package vn.bookstore.app.dto.response;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import vn.bookstore.app.model.Role;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ResAllowanceDTO {

    private Long id;
    private String idString;
    private String allowance;
    private List<String> roleName;

}
