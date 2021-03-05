package shorturl.shortener.service;

import org.springframework.stereotype.Service;

import shorturl.shortener.domain.ShortUrl;
import shorturl.shortener.dto.ShortUrlRequest;
import shorturl.shortener.dto.ShortUrlResponse;
import shorturl.shortener.repository.ShortUrlRepository;

@Service
public class ShortUrlService {

    private final ShortUrlRepository shortURLRepository;
    private final UrlEncoder urlEncoder;

    public ShortUrlService(final ShortUrlRepository shortURLRepository, final UrlEncoder urlEncoder) {
        this.shortURLRepository = shortURLRepository;
        this.urlEncoder = urlEncoder;
    }

    public ShortUrlResponse create(final ShortUrlRequest shortUrlRequest) {
        final ShortUrl shortUrl = shortURLRepository.save(new ShortUrl(shortUrlRequest.getUrl(), 1L));
        final String shortenUrl = urlEncoder.encoding(shortUrl.getId());
        shortUrl.updateShortUrl(shortenUrl);
        return ShortUrlResponse.of(shortenUrl);
    }
}
