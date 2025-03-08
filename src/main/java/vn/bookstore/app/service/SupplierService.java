package vn.bookstore.app.service;

import org.springframework.web.multipart.MultipartFile;
import vn.bookstore.app.dto.request.ReqProductDTO;
import vn.bookstore.app.dto.request.ReqSupplierDTO;
import vn.bookstore.app.dto.response.ResProductDTO;
import vn.bookstore.app.dto.response.ResSupplierDTO;

import java.io.IOException;
import java.util.List;

public interface SupplierService {
    List<ResSupplierDTO> getListSuppliers();
    
    ResSupplierDTO getSupplierById(Long id);
    
    void deleteSupplier(Long id);
    
    ResSupplierDTO addSupplier(ReqSupplierDTO reqSupplierDTO);
    
    ResSupplierDTO updateSupplier(ReqSupplierDTO reqSupplierDTO, Long supplierId);
    
}
