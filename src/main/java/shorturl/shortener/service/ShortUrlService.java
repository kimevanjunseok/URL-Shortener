package shorturl.shortener.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shorturl.shortener.domain.ShortUrl;
import shorturl.shortener.dto.ShortUrlRequest;
import shorturl.shortener.dto.ShortUrlResponse;
import shorturl.shortener.repository.ShortUrlRepository;

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
            return ShortUrlResponse.of(existUrl.get());
        }
        final ShortUrl shortUrl = shortURLRepository.save(new ShortUrl(shortUrlRequest.getUrl(), 1L));
        final String shortenUrl = urlEncoder.encoding(shortUrl.getId());
        shortUrl.updateShortUrl(shortenUrl);
        return ShortUrlResponse.of(shortUrl);
    }

    public ShortUrlResponse findByShortUrl(final String inputShortUrl) {
        final ShortUrl shortUrl =  shortURLRepository.findByShortUrl(inputShortUrl)
                .orElseThrow(IllegalArgumentException::new);
        return ShortUrlResponse.of(shortUrl);
    }
}
