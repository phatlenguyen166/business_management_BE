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
import vn.bookstore.app.dto.response.ResResponse;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestControllerAdvice
public class GlobalException {
    
    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<ResResponse<Object>> handleIdException(NotFoundException exception) {
        ResResponse<Object> res = new ResResponse<>();
        res.setStatusCode(HttpStatus.NOT_FOUND.value());
        res.setError(exception.getMessage());
        res.setMessage("NotFoundException");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    @ExceptionHandler(value = {InvalidRequestException.class})
    public ResponseEntity<ResResponse<Object>> handleIdException(InvalidRequestException exception) {
        ResResponse<Object> res = new ResResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError(exception.getMessage());
        res.setMessage("Dữ liệu không hợp lệ");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(value = {ExistingIdException.class})
    public ResponseEntity<ResResponse<Object>> handleIdException(ExistingIdException exception) {
        ResResponse<Object> res = new ResResponse<>();
        res.setStatusCode(CONFLICT.value());
        res.setError(exception.getMessage());
        res.setMessage("ExistingIdException");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
    }
    
    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<ResResponse<Object>> handleResourceNotFoundException(ResourceNotFoundException exception) {
        ResResponse<Object> res = new ResResponse<>();
        res.setStatusCode(HttpStatus.NOT_FOUND.value());
        res.setMessage(exception.getMessage());
        res.setError("ResourceNotFoundException");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }
    
    
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ResResponse<Object>> handleAllExceptions(Exception exception) {
        ResResponse<Object> res = new ResResponse<>();
        res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
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
    public ResponseEntity<ResResponse<Object>> handleDuplicateKeyException(InvalidDataException e,
                                                                           WebRequest request) {
        ResResponse<Object> res = new ResResponse<>();
//        res.set(request.getDescription(false).replace("uri=", ""));
        res.setStatusCode(CONFLICT.value());
        res.setError(CONFLICT.getReasonPhrase());
        res.setMessage(e.getMessage());
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
    }
    
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResResponse<Object>> handleBadCredentialsException(BadCredentialsException exception) {
        ResResponse<Object> res = new ResResponse<>();
        res.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        res.setError("Username or password incorrect ");
        res.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
<<<<<<< HEAD
<<<<<<< HEAD
        
=======
>>>>>>> d73af9a (Update Model User, Contract, SeniorityLevel, Role)
        RestResponse<Object> res = new RestResponse<>();
=======
        ResResponse<Object> res = new ResResponse<>();
>>>>>>> 7f00620 (change file class name)
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError("Validation Error");
        res.setMessage("Dữ liệu đầu vào không hợp lệ");
        res.setData(errors);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RestResponse<Object>> handleIllegalArgumentException(IllegalArgumentException exception) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError("Invalid Argument");
        res.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }
    
    
}
