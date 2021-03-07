package shorturl.shortener.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import shorturl.shortener.exception.ShortUrlOutOfLengthException;

@Entity
@Table(indexes = {
        @Index(name = "short_url_origin_url_idx", columnList = "originUrl"),
        @Index(name="short_url_short_url_idx", columnList = "shortUrl")})
public class ShortUrl {

    private static final String BASE_URL = "http://localhost:8080/";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originUrl;

    @Column
    private String shortUrl;

    @Column(nullable = false)
    private long requestCount;

    public ShortUrl(final String originUrl, final String shortUrl, final long requestCount) {
        validateShortUrlLength(shortUrl);
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
        this.requestCount = requestCount;
    }

    private void validateShortUrlLength(final String shortenUrl) {
        if (shortenUrl != null && shortenUrl.length() > 8) {
            throw new ShortUrlOutOfLengthException("ShortUrl의 길이가 8을 초과하였습니다. length: " + shortenUrl.length());
        }
    }

    protected ShortUrl() {}

    public void updateShortUrl(final String shortenUrl) {
        validateShortUrlLength(shortenUrl);
        this.shortUrl = shortenUrl;
    }

    public void addCount() {
        this.requestCount += 1;
    }

    public Long getId() {
        return id;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String getFullShortUrl() {
        return BASE_URL + shortUrl;
    }

    public long getRequestCount() {
        return requestCount;
    }
}
