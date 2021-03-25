package shorturl.shortener.repository;

import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import shorturl.shortener.domain.ShortUrl;
import shorturl.shortener.exception.URLNotFoundException;

@Repository
public class ShortUrlRepository {

    private static final String ORIGIN_URL = "OriginUrlKey";
    private static final String SHORT_URL = "ShortUrlKey";
    private static final String REQUEST_COUNT = "RequestCount";

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, Object> hashOperations;

    public ShortUrl findByOriginUrl(final String url) {
        return (ShortUrl) hashOperations.get(ORIGIN_URL, url);
    }

    public long size() {
        return hashOperations.size(SHORT_URL);
    }

    public ShortUrl save(final ShortUrl shortUrl) {
        hashOperations.put(ORIGIN_URL, shortUrl.getOriginUrl(), shortUrl);
        hashOperations.put(SHORT_URL, shortUrl.getShortUrl(), shortUrl);
        hashOperations.put(REQUEST_COUNT, shortUrl.getShortUrl(), 1L);
        return shortUrl;
    }

    public ShortUrl findByShortUrl(final String shortUrl) {
        Long count = (Long) hashOperations.get(REQUEST_COUNT, shortUrl);
        if (count == null) {
            throw new URLNotFoundException("해당 URL은 존재하지 않습니다. url: " + shortUrl);
        }
        hashOperations.put(REQUEST_COUNT, shortUrl, count + 1L);
        return (ShortUrl) hashOperations.get(SHORT_URL, shortUrl);
    }
}
