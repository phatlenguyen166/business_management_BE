package vn.bookstore.app.service;

import jakarta.servlet.http.HttpServletRequest;
import vn.bookstore.app.dto.request.ReqSignInDTO;
import vn.bookstore.app.dto.response.ResTokenDTO;


public interface AuthenticationService {
    
    
    ResTokenDTO authenticate(ReqSignInDTO signInRequest);
    
    
    ResTokenDTO refresh(HttpServletRequest request);
    
    
    String logout(HttpServletRequest request);
    
}

