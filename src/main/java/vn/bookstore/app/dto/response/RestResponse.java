package vn.bookstore.app.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RestResponse<T> {
    private int statusCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;
    private Object message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    
}
