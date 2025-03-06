package vn.bookstore.app.service.impl;

import org.springframework.stereotype.Service;
import vn.bookstore.app.dto.request.ReqContractDTO;
import vn.bookstore.app.dto.request.ReqUserWithContractDTO;
import vn.bookstore.app.dto.response.ResContractDTO;
import vn.bookstore.app.mapper.ContractMapper;
import vn.bookstore.app.model.Contract;
import vn.bookstore.app.model.SeniorityLevel;
import vn.bookstore.app.model.User;
import vn.bookstore.app.repository.ContractRepository;
import vn.bookstore.app.repository.RoleRepository;
import vn.bookstore.app.repository.SeniorityLevelRepository;
import vn.bookstore.app.repository.UserRepository;
import vn.bookstore.app.service.ContractService;
import vn.bookstore.app.util.error.InvalidDataException;
import vn.bookstore.app.util.error.InvalidRequestException;
import vn.bookstore.app.util.error.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContractServiceImpl implements ContractService {
    private ContractRepository contractRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ContractMapper contractMapper;
    private SeniorityLevelRepository seniorityLevelRepository;


    public ContractServiceImpl(ContractRepository contractRepository,
                               UserRepository userRepository,
                               RoleRepository roleRepository,
                               ContractMapper contractMapper,
                               SeniorityLevelRepository seniorityLevelRepository) {
        this.contractRepository = contractRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.contractMapper = contractMapper;
        this.seniorityLevelRepository = seniorityLevelRepository;
    }


    @Override
    public ResContractDTO handleCreateContract(ReqContractDTO newContract) {
        User user = userRepository.findUserByIdAndStatus(newContract.getUserId(), 1).orElseThrow(() -> new RuntimeException("User không tồn tại!"));
        SeniorityLevel seniorityLevel = seniorityLevelRepository.findByIdAndStatus(newContract.getSeniorityId(),1)
                .orElseThrow(() -> new RuntimeException("Cấp bậc không tồn tại!"));
        Contract contract = saveContract(user, newContract, seniorityLevel);
        return contractMapper.convertToResContractDTO(contract);

    }


    @Override
    public Contract handleCreateContractWithUser(ReqUserWithContractDTO reqUserWithContractDTO, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidRequestException("User không tồn tại!"));
        SeniorityLevel seniorityLevel = seniorityLevelRepository.findByIdAndStatus(reqUserWithContractDTO.getReqContract().getSeniorityId(),1).orElseThrow(() -> new InvalidRequestException("Cấp bậc không tồn tại !")) ;
        ReqContractDTO reqContractDTO = contractMapper.convertToReqContractDTO(reqUserWithContractDTO.getReqContract(), userId);
        return saveContract(user,reqContractDTO,seniorityLevel);
    }

    @Override
    public Contract saveContract(User user, ReqContractDTO newContract, SeniorityLevel seniorityLevel) {
        Contract currentContract = getActiveContract(user.getContracts());
        if (currentContract == null) {
            Contract contract =  contractMapper.convertToContract(newContract);
            contract.setUser(user);
            contract.setSeniorityLevel(seniorityLevel);
            contract.setStatus(1);
            contractRepository.save(contract);
            return  contract;
        } else {

            currentContract.setStatus(0);
            contractRepository.save(currentContract);
            Contract contract =  contractMapper.convertToContract(newContract);
            contract.setUser(user);
            contract.setSeniorityLevel(seniorityLevel);
            contract.setStatus(1);
            contractRepository.save(contract);
            return contract;
        }
    }

    @Override
    public ResContractDTO getContractById(Long id) {
        Optional<Contract> contract = this.contractRepository.findContractByIdAndStatus(id,1);
        if (contract.isPresent()) {
            return contractMapper.convertToResContractDTO(contract.get());
        }
        return null;
    }

    @Override
    public ResContractDTO handleUpdatedContract(ReqContractDTO updateContract, Long id) {
        Contract currentContract = this.contractRepository.findContractByIdAndStatus(id,1).get();
        Contract  updatedContract = this.contractMapper.convertToContract(updateContract);
        updatedContract.setUser(currentContract.getUser());
        updatedContract.setSeniorityLevel(currentContract.getSeniorityLevel());
        this.contractRepository.save(updatedContract);
        return contractMapper.convertToResContractDTO(updatedContract);
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
            ResContractDTO resContractDTO = contractMapper.convertToResContractDTO(contract);
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


