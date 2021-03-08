package shorturl.shortener.service;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shorturl.shortener.domain.ShortUrl;
import shorturl.shortener.dto.OriginUrlResponse;
import shorturl.shortener.dto.ShortUrlRequest;
import shorturl.shortener.dto.ShortUrlResponse;
import shorturl.shortener.exception.URLNotFoundException;
import shorturl.shortener.repository.ShortUrlRepository;
import shorturl.shortener.utils.UrlEncoder;

@Transactional
@Service
public class ShortUrlService {

    private final ShortUrlRepository shortURLRepository;
    private final UrlEncoder urlEncoder;

    public ShortUrlService(final ShortUrlRepository shortURLRepository, final UrlEncoder urlEncoder) {
        this.shortURLRepository = shortURLRepository;
        this.urlEncoder = urlEncoder;
    }

    public ShortUrlResponse create(final ShortUrlRequest shortUrlRequest) {
        final Optional<ShortUrl> existUrl = shortURLRepository.findByOriginUrl(shortUrlRequest.getUrl());
        if (existUrl.isPresent()) {
            final ShortUrl shortUrl = existUrl.get();
            shortUrl.addRequestCount();
            return ShortUrlResponse.of(shortUrl);
        }

        final ShortUrl shortUrl = shortURLRepository.save(new ShortUrl(shortUrlRequest.getUrl(), null, 1L));
        final String shortenUrl = urlEncoder.encoding(shortUrl.getId());
        shortUrl.updateShortUrl(shortenUrl);
        return ShortUrlResponse.of(shortUrl);
    }

    @Cacheable(value = "shortUrl")
    @Transactional(readOnly = true)
    public OriginUrlResponse findByShortUrl(final String inputShortUrl) {
        final ShortUrl shortUrl =  shortURLRepository.findByShortUrl(inputShortUrl)
                .orElseThrow(() -> new URLNotFoundException("해당 URL은 존재하지 않습니다. url: " + inputShortUrl));
        shortUrl.addRequestCount();
        return OriginUrlResponse.of(shortUrl);
    }
}
