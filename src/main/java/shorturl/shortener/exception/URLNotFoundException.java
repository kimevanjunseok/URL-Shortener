package shorturl.shortener.exception;

public class URLNotFoundException extends RuntimeException {

    public URLNotFoundException(final String message) {
        super(message);
    }
}
