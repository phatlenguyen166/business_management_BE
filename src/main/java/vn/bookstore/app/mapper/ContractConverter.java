package vn.bookstore.app.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import vn.bookstore.app.dto.request.ReqContractDTO;
import vn.bookstore.app.dto.request.ReqContractUserDTO;
import vn.bookstore.app.dto.response.ResContractDTO;
import vn.bookstore.app.model.Contract;


@Component
public class ContractConverter {
    private ModelMapper modelMapper;
    
    public ContractConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    
    
    public Contract convertToContract(Contract contract, ReqContractDTO reqContractDTO) {
        contract.setBaseSalary(reqContractDTO.getBaseSalary());
        contract.setStandardWorkingDay(reqContractDTO.getStandardWorkingDay());
        contract.setStartDate(reqContractDTO.getStartDate());
        contract.setEndDate(reqContractDTO.getEndDate());
        contract.setExpiryDate(reqContractDTO.getExpiryDate());
        return contract;
    }
    
    public ResContractDTO convertToResContractDTO(Contract contract) {
        ResContractDTO resContractDTO = modelMapper.map(contract, ResContractDTO.class);
        resContractDTO.setUserId(contract.getUser().getId());
        resContractDTO.setRoleName(contract.getRole().getName());
        return resContractDTO;
    }
    
    public ReqContractDTO convertToReqContractDTO(ReqContractUserDTO reqContract, Long id) {
        ReqContractDTO reqContractDTO = modelMapper.map(reqContract, ReqContractDTO.class);
        reqContractDTO.setUserId(id);
        return reqContractDTO;
    }
    
    
}
