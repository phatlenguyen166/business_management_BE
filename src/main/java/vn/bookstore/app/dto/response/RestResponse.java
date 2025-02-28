package vn.bookstore.app.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestResponse<T> {
    private int statusCode;
    private String error;
    private Object message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    
}
