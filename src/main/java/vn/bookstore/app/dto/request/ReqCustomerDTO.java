package vn.bookstore.app.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqCustomerDTO {
    @NotBlank(message = "Tên khách hàng không được để trống")
    private String name;
    
    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(
            regexp = "^0[1-9][0-9]{8}$",
            message = "Số điện thoại không hợp lệ, vui lòng nhập đúng định dạng"
    )
    private String phoneNumber;
    
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ, vui lòng nhập đúng định dạng")
    private String email;
    
    @NotBlank(message = "Địa chỉ không được để trống")
    private String address;
    
}
