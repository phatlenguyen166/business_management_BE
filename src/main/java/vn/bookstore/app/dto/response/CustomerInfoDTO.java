package vn.bookstore.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfoDTO {

    private Long id;
    private String customerName;
    private String customerPhone;
    private String customerAddress;
}
