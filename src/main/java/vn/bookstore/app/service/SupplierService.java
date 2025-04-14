package vn.bookstore.app.service;

import java.util.List;

import vn.bookstore.app.dto.request.ReqSupplierDTO;
import vn.bookstore.app.dto.response.ResSupplierDTO;

public interface SupplierService {

    List<ResSupplierDTO> getListSuppliers();

    ResSupplierDTO getSupplierById(Long id);

    void deleteSupplier(Long id);

    ResSupplierDTO addSupplier(ReqSupplierDTO reqSupplierDTO);

    ResSupplierDTO updateSupplier(ReqSupplierDTO reqSupplierDTO, Long supplierId);

}
