package vn.bookstore.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ReqSupplierDTO {
    
    
    @NotBlank(message = "Tên nhà cung cấp không được để trống!")
    private String name;
    
    @NotBlank(message = "Sdt nhà cung cấp không được để trống!")
    @Pattern(
            regexp = "^0[1-9][0-9]{8}$",
            message = "Số điện thoại không hợp lệ, vui lòng nhập đúng định dạng"
    )
    private String phoneNumber;
    
    @NotBlank(message = "Địa chỉ cung cấp không được để trống!")
    private String address;
    
}
