package vn.bookstore.app.mapper;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import vn.bookstore.app.dto.request.ReqUserWithContractDTO;
import vn.bookstore.app.dto.response.ResContractDTO;
import vn.bookstore.app.dto.response.ResUserDTO;
import vn.bookstore.app.model.Contract;
import vn.bookstore.app.model.User;
import vn.bookstore.app.repository.SeniorityLevelRepository;
import vn.bookstore.app.service.impl.ContractServiceImpl;
import vn.bookstore.app.service.impl.SeniorityLevelServiceImpl;

@Component
public class UserConverter {
    private ModelMapper modelMapper;
    private ContractServiceImpl contractService;
    private SeniorityLevelServiceImpl seniorityLevelService;

    public UserConverter(  ModelMapper modelMapper,
                           @Lazy
                           ContractServiceImpl contractService,
                           SeniorityLevelServiceImpl seniorityLevelService) {
        this.modelMapper = modelMapper;
        this.contractService = contractService;
        this.seniorityLevelService = seniorityLevelService;
    }

    public User convertToUser(ReqUserWithContractDTO requestDTO) {
        User result = modelMapper.map(requestDTO, User.class);
        result.setId(null);
        return result;
    }

    public ResUserDTO convertToResUserDTO(User user, ResContractDTO resContractDTO) {
        ResUserDTO result = modelMapper.map(user, ResUserDTO.class);
        result.setIdString("NV-" + user.getId());
        Contract contract = contractService.getActiveContract(user.getContracts());
//        if (contract != null) {
//            result.setRoleId(contract.getRole().getId());
//        } else {
//            result.setRoleId(roleRepository.findRoleByName("NO_ROLE").getId());
//        }
        result.setResContractDTO(resContractDTO);
        return result;
    }


}
