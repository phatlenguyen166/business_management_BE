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
import java.util.Optional;

@Service
public class ContractServiceImpl implements ContractService {
    private ContractRepository contractRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ContractConverter contractConverter;


    public ContractServiceImpl(ContractRepository contractRepository,
                               UserRepository userRepository,
                               RoleRepository roleRepository,
                               ContractConverter contractConverter) {
        this.contractRepository = contractRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.contractConverter = contractConverter;
    }


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
        Contract currentContract = getActiveContract(user.getContracts());
        if (currentContract == null) {
            Contract contract =  contractConverter.convertToContract(new Contract(),newContract);
            contract.setUser(user);
            contract.setRole(role);
            contract.setStatus(1);
            contractRepository.save(contract);
            return  contract;
        } else {

            currentContract.setStatus(0);
            contractRepository.save(currentContract);
            Contract contract =  contractConverter.convertToContract(new Contract(),newContract);
            contract.setUser(user);
            contract.setRole(role);
            contract.setStatus(1);
            contractRepository.save(contract);
            return contract;
        }
    }

    @Override
    public ResContractDTO getContractById(Long id) {
        Optional<Contract> contract = this.contractRepository.findContractByIdAndStatus(id,1);
        if (contract.isPresent()) {
            return contractConverter.convertToResContractDTO(contract.get());
        }
        return null;
    }

    @Override
    public ResContractDTO handleUpdatedContract(ReqContractDTO updateContract, Long id) {
        Contract currentContract = this.contractRepository.findContractByIdAndStatus(id,1).get();
        Contract  updatedContract = this.contractConverter.convertToContract(currentContract, updateContract);
        updatedContract.setUser(currentContract.getUser());
        updatedContract.setRole(currentContract.getRole());
        this.contractRepository.save(updatedContract);
        return contractConverter.convertToResContractDTO(updatedContract);
    }

    @Override
    public void handleDeleteContract(Long id) {
        Contract contract = this.contractRepository.findContractByIdAndStatus(id,1).get();
        User user = contract.getUser();
        contract.setStatus(0);
        user.setStatus(0);
        this.contractRepository.save(contract);
        this.userRepository.save(user);
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


