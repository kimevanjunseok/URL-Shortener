package shorturl.shortener.dto;

public class ShortUrlRequest {

    private String url;

    protected ShortUrlRequest() {}

    public ShortUrlRequest(final String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
