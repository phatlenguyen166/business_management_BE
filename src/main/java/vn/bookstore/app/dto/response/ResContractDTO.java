package vn.bookstore.app.dto.response;

import lombok.Getter;
import lombok.Setter;
import vn.bookstore.app.model.SeniorityLevel;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ResContractDTO {
    private Long id;
    private Long userId;
    private String fullName;
    private BigDecimal baseSalary;
    private int standardWorkingDay;
    private int status;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate expiryDate;
    private String roleName;
    private String levelName;
    private float salaryCoefficient;
}
