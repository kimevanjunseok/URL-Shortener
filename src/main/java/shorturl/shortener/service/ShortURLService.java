package shorturl.shortener.service;

import org.springframework.stereotype.Service;

import shorturl.shortener.repository.ShortURLRepository;

@Service
public class ShortURLService {

    private final ShortURLRepository shortURLRepository;

    public ShortURLService(final ShortURLRepository shortURLRepository) {
        this.shortURLRepository = shortURLRepository;
    }
}
