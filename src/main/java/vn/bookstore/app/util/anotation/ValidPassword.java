package vn.bookstore.app.util.anotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import vn.bookstore.app.util.validator.PasswordValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Mật khẩu phải có ít nhất 8 ký tự hoặc để trống";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}