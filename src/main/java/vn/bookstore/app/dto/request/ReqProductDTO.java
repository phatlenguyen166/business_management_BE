package vn.bookstore.app.dto.request;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqProductDTO {

    @NotNull(message = "Tên không được để trống")
    @Size(min = 2, max = 255, message = "Tên phải có từ 2 đến 255 ký tự")
    private String name;

    private String image;

    @Min(value = 0, message = "Số lượng không được nhỏ hơn 0")
    private int quantity;

    @Min(value = 1, message = "Trạng thái không hợp lệ")
    private int status;

    @Column(precision = 19, scale = 4, nullable = false)
    @NotNull(message = "Giá không được để trống")
    @Min(value = 0, message = "Giá phải lớn hơn hoặc bằng 0")
    private BigDecimal price;

    @NotNull(message = "Nhà cung cấp không được để trống")
    private Long supplierId;

    @NotNull(message = "Tác giả không được để trống")
    private Long authorId;

    @NotNull(message = "Danh mục không được để trống")
    private Long categoryId;
}
