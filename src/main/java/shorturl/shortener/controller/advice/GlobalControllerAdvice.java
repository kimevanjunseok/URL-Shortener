package shorturl.shortener.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import shorturl.shortener.dto.exception.ErrorResponse;
import shorturl.shortener.exception.ShortUrlOutOfLengthException;
import shorturl.shortener.exception.URLNotFoundException;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(URLNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleURLNotFoundException() {
        final ErrorResponse errorResponse = ErrorResponse.of(404, "해당 URL을 찾을 수 없습니다.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions() {
        final ErrorResponse errorResponse = ErrorResponse.of(400, "올바른 형식의 요청이 아닙니다.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ShortUrlOutOfLengthException.class)
    public ResponseEntity<ErrorResponse> handleShortUrlOutOfLengthException() {
        final ErrorResponse errorResponse = ErrorResponse.of(510, "URL 길이에 대한 확장이 필요합니다.");
        return ResponseEntity.status(HttpStatus.NOT_EXTENDED).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException() {
        final ErrorResponse errorResponse = ErrorResponse.of(500, "서버 내부적인 문제가 발생했습니다.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
