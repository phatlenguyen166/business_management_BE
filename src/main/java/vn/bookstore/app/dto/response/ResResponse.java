package vn.bookstore.app.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResResponse<T> {
    private int statusCode;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;
    
    private String message;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    
    public static <T> ResResponse<T> success(String message, T data) {
        return new ResResponse<>(HttpStatus.OK.value(), null, message, data);
    }
}
