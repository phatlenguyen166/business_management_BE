package vn.bookstore.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.bookstore.app.dto.request.ReqContractDTO;
import vn.bookstore.app.dto.request.ReqUserWithContractDTO;
import vn.bookstore.app.dto.response.ResContractDTO;
import vn.bookstore.app.mapper.ContractConverter;
import vn.bookstore.app.model.Contract;
import vn.bookstore.app.model.Role;
import vn.bookstore.app.model.User;
import vn.bookstore.app.repository.ContractRepository;
import vn.bookstore.app.repository.RoleRepository;
import vn.bookstore.app.repository.UserRepository;
import vn.bookstore.app.service.ContractService;
import vn.bookstore.app.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContractServiceImpl implements ContractService {
    @Autowired
    ContractRepository contractRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ContractConverter contractConverter;
    @Autowired
    UserService userService;

    @Override
    public ResContractDTO handleCreateContract(ReqContractDTO newContract) {
        User user = userRepository.findUserByIdAndStatus(newContract.getUserId(), 1).orElseThrow(() -> new RuntimeException("User không tồn tại!"));
        Role role = roleRepository.findRoleById(newContract.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role không tồn tại!"));
        Contract contract = saveContract(user, newContract, role);
        return contractConverter.convertToResContractDTO(contract);

    }


    @Override
    public Contract handleCreateContractWithUser(ReqUserWithContractDTO reqUserWithContractDTO, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User không tồn tại!"));
        Role role = roleRepository.findRoleById(reqUserWithContractDTO.getReqContract().getRoleId()).orElseThrow(() -> new RuntimeException("Role không tồn tại!"));
        ReqContractDTO reqContractDTO = contractConverter.convertToReqContractDTO(reqUserWithContractDTO.getReqContract(), userId);
       return saveContract(user,reqContractDTO,role);
    }

    @Override
    public Contract saveContract(User user, ReqContractDTO newContract, Role role) {
        if (!contractRepository.existsContractByUser(user)) {
            Contract contract =  contractConverter.convertToContract(newContract);
            contract.setUser(user);
            contract.setRole(role);
            contract.setStatus(1);
            contractRepository.save(contract);
            return  contract;
        } else {
            Contract currentContract = getActiveContract(user.getContracts());
            currentContract.setStatus(0);
            contractRepository.save(currentContract);
            Contract contract =  contractConverter.convertToContract(newContract);
            contract.setUser(user);
            contract.setRole(role);
            contract.setStatus(1);
            contractRepository.save(contract);
            return contract;
        }
    }

    @Override
    public List<ResContractDTO> handleGetAllContracts() {
        List<Contract> contracts = contractRepository.getAllByStatus(1);
        List<ResContractDTO> resContractDTOS = new ArrayList<>();
        for (Contract contract : contracts) {
            ResContractDTO resContractDTO = contractConverter.convertToResContractDTO(contract);
            resContractDTOS.add(resContractDTO);
        }
        return resContractDTOS;
    }

    @Override
    public Contract getActiveContract(List<Contract> contracts) {
        if (contracts == null || contracts.isEmpty()) {
            return null;
        }
        for (Contract contract : contracts) {
            if (contract != null && contract.getStatus() == 1) {
                return contract;
            }
        }
        return null;
    }
}


