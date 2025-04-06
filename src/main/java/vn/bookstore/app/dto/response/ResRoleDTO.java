package vn.bookstore.app.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResRoleDTO {
    private Long id;
    private String idString;
    private String name;
    private String description;
    private int status;
    private List<ResSeniorityDTO>  resSeniority;
    private String allowanceId;
}
