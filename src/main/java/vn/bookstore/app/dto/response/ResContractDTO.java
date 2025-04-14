package vn.bookstore.app.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResContractDTO {

    private Long id;
    private String idString;
    private Long userId;
    private String fullName;
    private Long seniorityId;
    private BigDecimal baseSalary;
    private int status;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate expiryDate;
    private String roleName;
    private String levelName;
    private float salaryCoefficient;
}
