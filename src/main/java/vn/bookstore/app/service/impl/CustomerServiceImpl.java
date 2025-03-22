package vn.bookstore.app.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.bookstore.app.dto.request.ReqCustomerDTO;
import vn.bookstore.app.dto.response.ResCustomerDTO;
import vn.bookstore.app.mapper.CustomerMapper;
import vn.bookstore.app.model.Customer;
import vn.bookstore.app.repository.CustomerRepository;
import vn.bookstore.app.service.CustomerService;
import vn.bookstore.app.util.error.InvalidRequestException;
import vn.bookstore.app.util.error.ResourceNotFoundException;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    
    private final CustomerRepository customerRepository;
    
    private final CustomerMapper customerMapper;
    
    @Override
    public ResCustomerDTO updateCustomer(ReqCustomerDTO reqCustomerDTO, Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhà cung cấp ID: " + customerId));
        
        if (!customer.getPhoneNumber().equals(reqCustomerDTO.getPhoneNumber())
                && customerRepository.existsByPhoneNumber(reqCustomerDTO.getPhoneNumber())) {
            throw new InvalidRequestException("Số điện thoại đã tồn tại!");
        }
        
        if (!customer.getEmail().equals(reqCustomerDTO.getEmail())
                && customerRepository.existsByEmail(reqCustomerDTO.getEmail())) {
            throw new InvalidRequestException("Email đã tồn tại!");
        }
        customerMapper.updateCustomerFromDto(reqCustomerDTO, customer);
        Customer newCustomer = customerRepository.save(customer);
        return customerMapper.toResCustomerDTO(newCustomer);
    }
    
    @Override
    @Transactional
    public ResCustomerDTO addCustomer(ReqCustomerDTO reqCustomer) {
        Customer customer = customerMapper.toCustomer(reqCustomer);
//        System.out.println("name = " + customer.getName());
        if (customerRepository.existsByPhoneNumber(customer.getPhoneNumber())) {
            throw new InvalidRequestException("Số điện thoại đã tồn tại");
        }
        
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new InvalidRequestException("Email đã tồn tại");
        }
        customer.setStatus(1);

        customer = customerRepository.save(customer);
        return customerMapper.toResCustomerDTO(customer);
    }
    
    @Override
    public List<ResCustomerDTO> getListCustomers() {
        return customerRepository.findAll().stream().map(customerMapper::toResCustomerDTO).toList();
    }
    
    @Override
    public ResCustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id).map(customerMapper::toResCustomerDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm khách hàng với ID: " + id));
    }
    
    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Không " +
                "tìm " +
                "thấy khách hàng với ID: " + id));
        
        customer.setStatus(0);
        customerRepository.save(customer);
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Khách hàng không tồn tại"));
    }
}
