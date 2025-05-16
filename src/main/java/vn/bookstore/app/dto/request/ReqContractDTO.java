package vn.bookstore.app.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import vn.bookstore.app.dto.common.OnCreate;
import vn.bookstore.app.dto.common.OnUpdate;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ReqContractDTO {

    @NotNull(message = "Lương cơ bản không được để trống",groups = OnCreate.class)
    @DecimalMin(value = "0.0", inclusive = false, message = "Lương cơ bản phải lớn hơn 0",groups = OnCreate.class )
    @Digits(integer = 15, fraction = 4, message = "Lương cơ bản tối đa 15 số nguyên và 4 số thập phân")
    private BigDecimal baseSalary;

    @NotNull(message = "Ngày bắt đầu không được để trống", groups = OnCreate.class)
    @FutureOrPresent(message = "Ngày bắt đầu phải từ hôm nay trở đi",groups = OnCreate.class)
    private LocalDate startDate;

    @NotNull(message = "Ngày kết thúc không được để trống", groups = OnCreate.class)
    private LocalDate endDate;

    @Future(message = "Ngày hết hạn trong tuong lai ", groups = {OnCreate.class, OnUpdate.class})
    private LocalDate expiryDate;

    @NotNull(message = "User ID không được để trống", groups = OnCreate.class)
    @Positive(message = "User ID phải là số dương", groups = OnCreate.class)
    private Long userId;
    @NotNull(message = "Cấp bậc không được để trống", groups = OnCreate.class)
    @Positive(message = "Cấp bậc phải là số dương", groups = OnCreate.class)
    private Long seniorityId;
}
