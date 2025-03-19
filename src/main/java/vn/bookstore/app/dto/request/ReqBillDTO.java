package vn.bookstore.app.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqBillDTO {

    @NotNull(message = "User ID không được để trống")
    private Long userId;

    @NotNull(message = "Customer ID không được để trống")
    private Long customerId;

    @NotEmpty(message = "Địa chỉ không được để trống")
    @Size(max = 255, message = "Địa chỉ không được vượt quá 255 ký tự")
    private String address;

    @NotEmpty(message = "Danh sách chi tiết hóa đơn không được để trống")
    private List<ReqBillDetailDTO> billDetails;
}
