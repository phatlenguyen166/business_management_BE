package vn.bookstore.app.service;

import org.springframework.stereotype.Service;
import vn.bookstore.app.dto.request.ReqContractDTO;
import vn.bookstore.app.dto.response.ResContractDTO;
import vn.bookstore.app.model.Contract;

import java.util.List;

public interface ContractService {
    public ResContractDTO handleCreateContract(ReqContractDTO contract);
    public List<ResContractDTO> handleGetAllContracts();
}
