package vn.bookstore.app.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqSupplierDTO {
    
    @NotBlank(message = "Tên nhà cung cấp không được để trống!")
    private String name;
    
    @NotBlank(message = "Số điện thoại nhà cung cấp không được để trống!")
    @Pattern(
            regexp = "^0[1-9][0-9]{8}$",
            message = "Số điện thoại không hợp lệ, vui lòng nhập đúng định dạng (10 số, bắt đầu bằng 0)"
    )
    private String phoneNumber;
    
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ, vui lòng nhập đúng định dạng")
    private String email;
    
    @Min(value = 0, message = "Tỷ lệ phần trăm không thể nhỏ hơn 0%")
    @Max(value = 100, message = "Tỷ lệ phần trăm không thể lớn hơn 100%")
    private double percentage;
    
    @NotBlank(message = "Địa chỉ cung cấp không được để trống!")
    private String address;
}
