package vn.bookstore.app.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
