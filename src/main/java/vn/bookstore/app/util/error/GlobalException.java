package vn.bookstore.app.util.error;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import vn.bookstore.app.dto.response.ResponseDTO;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestControllerAdvice
public class GlobalException {
    
    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<ResponseDTO<Object>> handleIdException(NotFoundException exception) {
        ResponseDTO<Object> res = new ResponseDTO<>();
        res.setStatusCode(HttpStatus.NOT_FOUND.value());
        res.setSuccess(false);
        res.setError(exception.getMessage());
        res.setMessage("NotFoundException");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }
    
    @ExceptionHandler(value = {InvalidRequestException.class})
    public ResponseEntity<ResponseDTO<Object>> handleIdException(InvalidRequestException exception) {
        ResponseDTO<Object> res = new ResponseDTO<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setSuccess(false);
        res.setError(exception.getMessage());
        res.setMessage("Dữ liệu không hợp lệ");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }
    
    @ExceptionHandler(value = {ExistingIdException.class})
    public ResponseEntity<ResponseDTO<Object>> handleIdException(ExistingIdException exception) {
        ResponseDTO<Object> res = new ResponseDTO<>();
        res.setStatusCode(CONFLICT.value());
        res.setSuccess(false);
        res.setError(exception.getMessage());
        res.setMessage("ExistingIdException");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
    }
    
    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<ResponseDTO<Object>> handleResourceNotFoundException(ResourceNotFoundException exception) {
        ResponseDTO<Object> res = new ResponseDTO<>();
        res.setStatusCode(HttpStatus.NOT_FOUND.value());
        res.setSuccess(false);
        res.setMessage(exception.getMessage());
        res.setError("ResourceNotFoundException");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }
    
    
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ResponseDTO<Object>> handleAllExceptions(Exception exception) {
        ResponseDTO<Object> res = new ResponseDTO<>();
        res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        res.setSuccess(false);
        res.setMessage(exception.getMessage());
        res.setError("Internal Server Error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
    }
    
    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(CONFLICT)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Conflict",
                    content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "409 Response",
                                    summary = "Handle exception when input data is conflicted",
                                    value = """
                                            {
                                              "timestamp": "2023-10-19T06:07:35.321+00:00",
                                              "status": 409,
                                              "path": "/api/v1/...",
                                              "error": "Conflict",
                                              "message": "{data} exists, Please try again!"
                                            }
                                            """
                            ))})
    })
    public ResponseEntity<ResponseDTO<Object>> handleDuplicateKeyException(InvalidDataException e,
                                                                           WebRequest request) {
        ResponseDTO<Object> res = new ResponseDTO<>();
//        res.set(request.getDescription(false).replace("uri=", ""));
        res.setStatusCode(CONFLICT.value());
        res.setSuccess(false);
        res.setError(CONFLICT.getReasonPhrase());
        res.setMessage(e.getMessage());
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
    }
    
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseDTO<Object>> handleBadCredentialsException(BadCredentialsException exception) {
        ResponseDTO<Object> res = new ResponseDTO<>();
        res.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        res.setSuccess(false);
        res.setError("Username or password incorrect ");
        res.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        ResponseDTO<Object> res = new ResponseDTO<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setSuccess(false);
        res.setError("Validation Error");
        res.setMessage("Dữ liệu đầu vào không hợp lệ");
        res.setData(errors); // Trả về lỗi dưới dạng Map
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }
    
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ResponseDTO<Object>> handleIllegalStateException(IllegalStateException exception) {
        ResponseDTO<Object> res = new ResponseDTO<>();
        res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        res.setSuccess(false);
        res.setError("Illegal State Exception");
        res.setMessage(exception.getMessage());
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
    }
}
