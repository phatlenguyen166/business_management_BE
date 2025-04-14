package vn.bookstore.app.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import vn.bookstore.app.dto.request.ReqSupplierDTO;
import vn.bookstore.app.dto.response.ResSupplierDTO;
import vn.bookstore.app.mapper.SupplierMapper;
import vn.bookstore.app.model.Supplier;
import vn.bookstore.app.repository.SupplierRepository;
import vn.bookstore.app.service.SupplierService;
import vn.bookstore.app.util.error.InvalidRequestException;
import vn.bookstore.app.util.error.ResourceNotFoundException;

@RequiredArgsConstructor
@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    @Override
    public List<ResSupplierDTO> getListSuppliers() {
        return supplierRepository.findAll().stream().map(supplierMapper::toResSupplierDTO).toList();
    }

    @Override
    public ResSupplierDTO getSupplierById(Long id) {
        return supplierRepository.findById(id).map(supplierMapper::toResSupplierDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm nhà cung cấp với ID: " + id));
    }

    @Override
    public void deleteSupplier(Long id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Không "
                + "tìm "
                + "thấy nhà cung cấp với ID: " + id));

        supplier.setStatus(0);
        supplierRepository.save(supplier);
    }

    @Override
    @Transactional
    public ResSupplierDTO addSupplier(ReqSupplierDTO reqSupplierDTO) {
        Supplier supplier = supplierMapper.toSupplier(reqSupplierDTO);
        if (supplierRepository.existsByName(supplier.getName())) {
            throw new InvalidRequestException("Tên nhà cung cấp đã tồn tại!");
        }
        if (supplierRepository.existsByPhoneNumber(supplier.getPhoneNumber())) {
            throw new InvalidRequestException("Số điện thoại đã tòn tại");
        }
        supplier.setStatus(1);
        supplier = supplierRepository.save(supplier);
        return supplierMapper.toResSupplierDTO(supplier);
    }

    @Override
    public ResSupplierDTO updateSupplier(ReqSupplierDTO reqSupplierDTO, Long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhà cung cấp ID: " + supplierId));

        if (!supplier.getName().equals(reqSupplierDTO.getName()) && supplierRepository.existsByName(reqSupplierDTO.getName())) {
            throw new InvalidRequestException("Tên nhà cung cấp đã tồn tại!");
        }

        if (!supplier.getPhoneNumber().equals(reqSupplierDTO.getPhoneNumber())
                && supplierRepository.existsByPhoneNumber(reqSupplierDTO.getPhoneNumber())) {
            throw new InvalidRequestException("Số điện thoại đã tồn tại!");
        }

        supplierMapper.updateSupplierFromDto(reqSupplierDTO, supplier);
        Supplier newSupplier = supplierRepository.save(supplier);
        return supplierMapper.toResSupplierDTO(newSupplier);
    }
}
