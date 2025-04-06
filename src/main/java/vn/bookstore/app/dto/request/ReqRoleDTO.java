package vn.bookstore.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqRoleDTO {
    @NotBlank(message = "Tên role không được để trống")
    @Size(max = 50, message = "Tên role không được vượt quá 50 ký tự")
    private String name;
    @NotBlank(message = "Mô tả không được để trống")
    @Size(max = 255, message = "Mô tả không được vượt quá 255 ký tự")
    private String description;
    @NotNull(message = "Phụ cấp không được để trống")
    private Long allowanceId;

}
