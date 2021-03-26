package shorturl.shortener.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shorturl.shortener.domain.ShortUrl;
import shorturl.shortener.dto.OriginUrlResponse;
import shorturl.shortener.dto.ShortUrlRequest;
import shorturl.shortener.dto.ShortUrlResponse;
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
        final long id = shortURLRepository.size() + 1L;
        final String shortenUrl = urlEncoder.encoding(id);
        final ShortUrl shortUrl = shortURLRepository.save(new ShortUrl(id, shortUrlRequest.getUrl(), shortenUrl));
        return ShortUrlResponse.of(shortUrl);
    }

    @Transactional(readOnly = true)
    public OriginUrlResponse findOriginUrlByShortUrl(final String inputShortUrl) {
        final ShortUrl shortUrl = shortURLRepository.findByShortUrl(inputShortUrl);
        return OriginUrlResponse.of(shortUrl);
    }
}
