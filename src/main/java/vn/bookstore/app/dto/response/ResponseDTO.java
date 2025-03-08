package vn.bookstore.app.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"statusCode", "success", "error", "message", "data"})
public class ResponseDTO<T> {
    private int statusCode;
    boolean success;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;
    
    private String message;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    
    public static <T> ResponseDTO<T> success(boolean success, String message, T data) {
        return new ResponseDTO<>(HttpStatus.OK.value(), success, null, message, data);
    }
}
