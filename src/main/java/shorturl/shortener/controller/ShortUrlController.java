package shorturl.shortener.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shorturl.shortener.dto.ShortUrlRequest;
import shorturl.shortener.dto.ShortUrlResponse;
import shorturl.shortener.service.ShortUrlService;

@RequestMapping("/api/v1/short-url")
@RestController
public class ShortUrlController {

    private final ShortUrlService shortURLService;

    public ShortUrlController(final ShortUrlService shortURLService) {
        this.shortURLService = shortURLService;
    }

    @PostMapping
    public ResponseEntity<ShortUrlResponse> create(@RequestBody @Valid final ShortUrlRequest shortUrlRequest) {
        final ShortUrlResponse shortUrlResponse = shortURLService.create(shortUrlRequest);
        return ResponseEntity.created(URI.create("/api/v1/short-url/" + shortUrlResponse.getId())).body(shortUrlResponse);
    }
}
