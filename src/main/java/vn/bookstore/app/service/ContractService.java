package vn.bookstore.app.service;

import java.util.List;

import vn.bookstore.app.dto.request.ReqContractDTO;
import vn.bookstore.app.dto.request.ReqUserWithContractDTO;
import vn.bookstore.app.dto.response.ResContractDTO;
import vn.bookstore.app.model.Contract;
import vn.bookstore.app.model.SeniorityLevel;
import vn.bookstore.app.model.User;

public interface ContractService {

    public ResContractDTO handleCreateContract(ReqContractDTO contract);

    public List<ResContractDTO> handleGetAllContracts();

    public Contract getActiveContract(List<Contract> contracts);

    public Contract handleCreateContractWithUser(ReqUserWithContractDTO reqUserWithContractDTO, Long userId);

    public Contract saveContract(User user, ReqContractDTO newContract, SeniorityLevel seniorityLevel);

    public ResContractDTO getContractById(Long id);

    public ResContractDTO handleUpdatedContract(ReqContractDTO contract, Long id);

    public void handleDeleteContract(Long id);

    public List<ResContractDTO> handleGetAllContractsByUser(Long userId);
    
    public Contract findByUsername(String username,int status);
}
