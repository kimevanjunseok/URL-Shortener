package shorturl.shortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import shorturl.shortener.domain.ShortUrl;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
}
