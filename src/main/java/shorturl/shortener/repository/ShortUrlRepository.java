package shorturl.shortener.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import shorturl.shortener.domain.ShortUrl;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    Optional<ShortUrl> findByShortUrl(final String shortUrl);
}
