package vn.bookstore.app.dto.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqSeniorityLevelDTO {

    @NotBlank(message = "Tên cấp bậc không được để trống")
    @Size(max = 100, message = "Tên cấp bậc không được quá 100 ký tự")
    private String levelName;

    @Column(columnDefinition = "MEDIUMTEXT")
    @NotBlank(message = "Mô tả không được để trống")
    @Size(max = 500, message = "Mô tả không được quá 500 ký tự")
    private String description;
    private int status;
    @Positive(message = "Hệ số lương phải lớn hơn 0")
    @Max(value = 5, message = "Hệ số lương không được vượt quá 5")
    private float salaryCoefficient;
    private Long roleId;
}
