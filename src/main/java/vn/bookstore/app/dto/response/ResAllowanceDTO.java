package vn.bookstore.app.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResAllowanceDTO {

    private Long id;
    private String idString;
    private String allowance;
    private List<String> roleName;

}
