package shorturl.shortener.dto.exception;

public class ErrorResponse {

    private final int statusCode;
    private final String message;

    private ErrorResponse(final int statusCode, final String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public static ErrorResponse of(final int statusCode, final String message) {
        return new ErrorResponse(statusCode, message);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
