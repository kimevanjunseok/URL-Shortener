package shorturl.shortener.repository;

import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import shorturl.shortener.domain.ShortUrl;
import shorturl.shortener.exception.URLNotFoundException;

@Repository
public class ShortUrlRepository {

    private static final String ORIGIN_URL = "OriginUrlKey";
    private static final String SHORT_URL = "ShortUrlKey";

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, Object> hashOperations;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOperations;

    public long size() {
        return hashOperations.size(SHORT_URL);
    }

    public ShortUrl save(final ShortUrl shortUrl) {
        final Boolean hasOriginUrl = hashOperations.putIfAbsent(ORIGIN_URL, shortUrl.getOriginUrl(), shortUrl);
        if (!hasOriginUrl) {
            return (ShortUrl) hashOperations.get(ORIGIN_URL, shortUrl.getOriginUrl());
        }
        hashOperations.put(SHORT_URL, shortUrl.getShortUrl(), shortUrl);
        valueOperations.set(shortUrl.getShortUrl(), "1");
        return shortUrl;
    }

    public ShortUrl findByShortUrl(final String shortUrl) {
        final ShortUrl foundUrl = (ShortUrl) hashOperations.get(SHORT_URL, shortUrl);
        if (Objects.isNull(foundUrl)) {
            throw new URLNotFoundException("해당 URL은 존재하지 않습니다.");
        }
        valueOperations.increment(shortUrl, 1);
        return foundUrl;
    }
}
