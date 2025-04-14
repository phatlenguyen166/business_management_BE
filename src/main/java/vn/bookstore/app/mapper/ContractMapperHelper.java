package vn.bookstore.app.mapper;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import vn.bookstore.app.model.User;
import vn.bookstore.app.service.impl.ContractServiceImpl;

@Component
public class ContractMapperHelper {

    private final ContractServiceImpl contractService;

    public ContractMapperHelper(ContractServiceImpl contractService) {
        this.contractService = contractService;
    }

    @Named("formatRoleName")
    public String formatRoleName(User user) {
        return this.contractService.getActiveRoleName(user);
    }
}
