package vn.bookstore.app.dto.response;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class ResSeniorityDTO {

    private Long id;
    private String idString;
    private String levelName;
    private String description;
    private float salaryCoefficient;
    private int status;
    private String roleId;
}
