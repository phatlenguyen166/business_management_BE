package vn.bookstore.app.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestResponse<T> {
    private int statusCode;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;
    
    private String message;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    
    public static <T> RestResponse<T> success(String message, T data) {
        return new RestResponse<>(HttpStatus.OK.value(), null, message, data);
    }
}
