package shorturl.shortener.exception;

public class ShortUrlOutOfLengthException extends RuntimeException {

    public ShortUrlOutOfLengthException(final String message) {
        super(message);
    }
}
