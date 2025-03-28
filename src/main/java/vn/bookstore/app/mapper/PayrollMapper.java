package vn.bookstore.app.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import vn.bookstore.app.dto.response.ResContractDTO;
import vn.bookstore.app.dto.response.ResPayrollDTO;
import vn.bookstore.app.model.Contract;
import vn.bookstore.app.model.Payroll;
import vn.bookstore.app.model.User;
import vn.bookstore.app.service.impl.ContractServiceImpl;

@Mapper(componentModel = "spring", uses = {ContractMapperHelper.class, PayrollMapperHelper.class})
public interface  PayrollMapper {
    PayrollMapper INSTANCE = Mappers.getMapper(PayrollMapper.class);

    @Mapping(source = "id", target = "idString",qualifiedByName = "formatId")
    @Mapping(source = "payroll.attendance.id", target = "attendanceId")
    @Mapping(source = "payroll.attendance.monthOfYear", target = "monthOfYear")
    @Mapping(source = "payroll.attendance.user.id", target = "userIdStr",qualifiedByName = "formatIdUser")
    @Mapping(source = "payroll.attendance.user.fullName", target = "fullName")
    @Mapping(source = "payroll.attendance.user", target = "roleName", qualifiedByName = "formatRoleName")
    @Mapping(source = "payroll.attendance.totalWorkingDays", target = "totalWorkingDays")
    @Mapping(source = "payroll.attendance.totalSickLeaves", target = "totalSickLeaves")
    @Mapping(source = "payroll.attendance.totalMaternityLeaves", target = "totalMaternityLeaves")
    @Mapping(source = "payroll.attendance.totalPaidLeaves", target = "totalPaidLeaves")
    @Mapping(source = "payroll.attendance.totalUnpaidLeaves", target = "totalUnpaidLeaves")
    @Mapping(source = "payroll.attendance.totalHolidayLeaves", target = "totalHolidayLeaves")
    @Mapping(source = "payroll", target = "totalBaseSalary", qualifiedByName = "formatTotalBaseSalary")
    @Mapping(source = "grossSalary", target = "grossSalary", qualifiedByName = "formatBigDecimal")
    @Mapping(source = "allowance", target = "allowance", qualifiedByName = "formatBigDecimal")
    @Mapping(source = "baseSalary", target = "baseSalary", qualifiedByName = "formatBigDecimal")
    @Mapping(source = "payroll", target = "deductions", qualifiedByName = "formatDeductions")
    @Mapping(source = "payroll", target = "totalBenefit", qualifiedByName = "formatTotalBenefit")
    @Mapping(source = "payroll", target = "mainSalary", qualifiedByName = "formatMainSalary")
    @Mapping(source = "employeeBHXH", target = "employeeBHXH", qualifiedByName = "formatBigDecimal")
    @Mapping(source = "employeeBHYT", target = "employeeBHYT", qualifiedByName = "formatBigDecimal")
    @Mapping(source = "employeeBHTN", target = "employeeBHTN", qualifiedByName = "formatBigDecimal")
    @Mapping(source = "tax", target = "tax", qualifiedByName = "formatBigDecimal")
    @Mapping(source = "penalties", target = "penalties", qualifiedByName = "formatBigDecimal")
    @Mapping(source = "totalIncome", target = "totalIncome", qualifiedByName = "formatBigDecimal")
    @Mapping(source = "maternityBenefit", target = "maternityBenefit", qualifiedByName = "formatBigDecimal")
    @Mapping(source = "sickBenefit", target = "sickBenefit", qualifiedByName = "formatBigDecimal")
    @Mapping(source = "payroll.attendance.standardWorkingDays", target = "standardWorkingDays")
    ResPayrollDTO convertToResPayrollDTO(Payroll payroll);


    @Named("formatId")
    static String formatId(Long id) {
        return "PR-" + id;
    }
    @Named("formatIdUser")
    static String formatIdUser(Long id) {
        return "NV-" + id;
    }
}
