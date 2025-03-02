package vn.bookstore.app.mapper;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import vn.bookstore.app.dto.request.ReqUserDTO;
import vn.bookstore.app.dto.response.ResUserDTO;
import vn.bookstore.app.model.Contract;
import vn.bookstore.app.model.User;
import vn.bookstore.app.repository.RoleRepository;
import vn.bookstore.app.service.impl.ContractServiceImpl;
import vn.bookstore.app.service.impl.UserServiceImpl;

@Component
public class UserConverter {
    @Autowired
    private ModelMapper modelMapper;
    @Lazy
    @Autowired
    private ContractServiceImpl contractService;
    @Autowired
    private RoleRepository roleRepository;



    public User convertToUser(ReqUserDTO requestDTO) {
        User result = modelMapper.map(requestDTO, User.class);
        return result;
    }

    public ResUserDTO convertToResUserDTO(User user) {
        ResUserDTO result = modelMapper.map(user, ResUserDTO.class);
        result.setIdString("NV-" + user.getId());
        Contract contract = contractService.getActiveContract(user.getContracts());
        if (contract != null) {
            result.setRoleId(contract.getRole().getId());
        } else {
            result.setRoleId(roleRepository.findRoleByName("NO_ROLE").getId());
        }
        return result;
    }


}
