package vn.bookstore.app.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ReqContractUserDTO{
    @NotNull(message = "Lương cơ bản không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Lương cơ bản phải lớn hơn 0")
    @Digits(integer = 15, fraction = 4, message = "Lương cơ bản tối đa 15 số nguyên và 4 số thập phân")
    private BigDecimal baseSalary;

    @FutureOrPresent(message = "Ngày bắt đầu phải từ hôm nay trở đi")
    @NotNull(message = "Ngày bắt đầu không được để trống")
    private LocalDate startDate;


    private LocalDate endDate;

    @Future(message = "Ngày hết hạn trong tuong lai ")
    private LocalDate expiryDate;

    @NotNull(message = "User ID không được để trống")
    @Positive(message = "User ID phải là số dương")
    private Long seniorityId;

}
