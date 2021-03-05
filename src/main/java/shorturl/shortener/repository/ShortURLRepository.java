package shorturl.shortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import shorturl.shortener.domain.ShortURL;

public interface ShortURLRepository extends JpaRepository<ShortURL, Long> {
}
