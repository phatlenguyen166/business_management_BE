package vn.bookstore.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import vn.bookstore.app.dto.response.PayrollStatisticsDTO;
import vn.bookstore.app.dto.response.PayrollStatisticsViewDTO;
import vn.bookstore.app.dto.response.ResPayrollDTO;
import vn.bookstore.app.model.Payroll;

@Mapper(componentModel = "spring", uses = {ContractMapperHelper.class, PayrollMapperHelper.class})
public interface SalaryStatisticMapper {
    SalaryStatisticMapper INSTANCE = Mappers.getMapper(SalaryStatisticMapper.class);


    @Mapping(source = "avgGrossSalary", target = "avgGrossSalary", qualifiedByName = "formatBigDecimal")
    @Mapping(source = "avgNetSalary", target = "avgNetSalary", qualifiedByName = "formatBigDecimal")
    @Mapping(source = "avgAllowance", target = "avgAllowance", qualifiedByName = "formatBigDecimal")
    @Mapping(source = "avgPenalties", target = "avgPenalties", qualifiedByName = "formatBigDecimal")
    @Mapping(source = "avgTax", target = "avgTax", qualifiedByName = "formatBigDecimal")
    @Mapping(source = "costPerEmployee", target = "costPerEmployee", qualifiedByName = "formatBigDecimal")
    @Mapping(source = "totalGrossSalary", target = "totalGrossSalary", qualifiedByName = "formatBigDecimal")
    @Mapping(source = "totalNetSalary", target = "totalNetSalary", qualifiedByName = "formatBigDecimal")
    @Mapping(source = "totalAllowance", target = "totalAllowance", qualifiedByName = "formatBigDecimal")
    @Mapping(source = "totalPenalties", target = "totalPenalties", qualifiedByName = "formatBigDecimal")
    @Mapping(source = "totalTax", target = "totalTax", qualifiedByName = "formatBigDecimal")
    @Mapping(source = "totalEmployeeBHXH", target = "totalEmployeeBHXH", qualifiedByName = "formatBigDecimal")
    @Mapping(source = "totalEmployeeBHYT", target = "totalEmployeeBHYT", qualifiedByName = "formatBigDecimal")
    @Mapping(source = "totalEmployeeBHTN", target = "totalEmployeeBHTN", qualifiedByName = "formatBigDecimal")
    @Mapping(source = "totalSickBenefit", target = "totalSickBenefit", qualifiedByName = "formatBigDecimal")
    @Mapping(source = "totalMaternityBenefit", target = "totalMaternityBenefit", qualifiedByName = "formatBigDecimal")
    @Mapping(source = "totalCompanyCost", target = "totalCompanyCost", qualifiedByName = "formatBigDecimal")
    PayrollStatisticsViewDTO convertToPayrollStatisticsViewDTO(PayrollStatisticsDTO payrollStatisticsDTO);


}
