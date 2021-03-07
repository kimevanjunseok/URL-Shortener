package shorturl.shortener.controller.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import shorturl.shortener.exception.URLNotFoundException;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(URLNotFoundException.class)
    public ResponseEntity<String> handleURLNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 URL을 찾을 수 없습니다.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions() {
        return ResponseEntity.badRequest().body("올바른 형식의 요청이 아닙니다.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부적인 문제가 발생했습니다.");
    }
}
