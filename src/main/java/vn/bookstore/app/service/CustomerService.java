package vn.bookstore.app.service;

import jakarta.transaction.Transactional;
import vn.bookstore.app.dto.request.ReqCustomerDTO;
import vn.bookstore.app.dto.response.ResCustomerDTO;
import vn.bookstore.app.model.Customer;

import java.util.List;

public interface CustomerService {
    
    ResCustomerDTO updateCustomer(ReqCustomerDTO reqCustomerDTO, Long customerId);
    
    ResCustomerDTO addCustomer(ReqCustomerDTO reqCustomer);
    
    List<ResCustomerDTO> getListCustomers();
    
    ResCustomerDTO getCustomerById(Long id);
    
    void deleteCustomer(Long id);

    Customer findById(Long id);
}
