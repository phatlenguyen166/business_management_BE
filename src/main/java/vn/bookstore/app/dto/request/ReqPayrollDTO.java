package vn.bookstore.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqPayrollDTO {
    @NotBlank(message = "Thời gian không được để trốngThời gian không được để trống")
    private String yearMonthStr;
}
