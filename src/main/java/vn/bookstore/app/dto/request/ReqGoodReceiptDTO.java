package vn.bookstore.app.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqGoodReceiptDTO {

    @NotNull(message = "Người dùng không được để trống")
    private Long userId;

    @NotEmpty(message = "Danh sách sản phẩm không được để trống")
    private List<ReqGoodReceiptDetailDTO> goodReceiptDetails;
}
