package shorturl.shortener.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originUrl;

    @Column
    private String shortUrl;

    @Column(nullable = false)
    private long requestCount;

    public ShortUrl(final String originUrl, final long requestCount) {
        this.originUrl = originUrl;
        this.requestCount = requestCount;
    }

    protected ShortUrl() {}

    public void updateShortUrl(final String shortenUrl) {
        this.shortUrl = shortenUrl;
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

    public long getRequestCount() {
        return requestCount;
    }
}
