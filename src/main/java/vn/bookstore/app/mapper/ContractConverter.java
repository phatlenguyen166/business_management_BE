package vn.bookstore.app.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import vn.bookstore.app.dto.request.ReqContractDTO;
import vn.bookstore.app.dto.response.ResContractDTO;
import vn.bookstore.app.model.Contract;
import vn.bookstore.app.model.Role;
import vn.bookstore.app.model.User;


@Component
public class ContractConverter {
    private ModelMapper modelMapper;

    public ContractConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }



    public Contract convertToContract(ReqContractDTO reqContractDTO) {
        Contract contract = new Contract();
        contract.setBaseSalary(reqContractDTO.getBaseSalary());
        contract.setStandardWorkingDay(reqContractDTO.getStandardWorkingDay());
        contract.setStartDate(reqContractDTO.getStartDate());
        contract.setEndDate(reqContractDTO.getEndDate());
        contract.setExpiryDate(reqContractDTO.getExpiryDate());
        return contract;
    }

    public ResContractDTO convertToResContractDTO(Contract contract) {
        ResContractDTO resContractDTO = modelMapper.map(contract, ResContractDTO.class);
        resContractDTO.getUser().setIdString("NV-" + contract.getUser().getId());
        resContractDTO.getUser().setRoleId(contract.getRole().getId());
        return resContractDTO ;
    }



}
