package vn.bookstore.app.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqGoodReceiptDetailDTO {
    @NotNull(message = "ID sản phẩm không được để trống")
    private Long productId;

    @Min(value = 1, message = "Số lượng sản phẩm phải lớn hơn 0")
    private int quantity;
}
