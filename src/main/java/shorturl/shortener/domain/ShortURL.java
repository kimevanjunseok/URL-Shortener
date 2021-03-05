package shorturl.shortener.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ShortURL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originUrl;

    @Column
    private String shortUrl;

    @Column
    private int requestCount;

    protected ShortURL() {}

    public Long getId() {
        return id;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public int getRequestCount() {
        return requestCount;
    }
}
