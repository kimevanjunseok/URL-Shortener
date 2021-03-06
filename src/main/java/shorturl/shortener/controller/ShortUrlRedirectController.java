package shorturl.shortener.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import shorturl.shortener.dto.ShortUrlResponse;
import shorturl.shortener.service.ShortUrlService;

@Controller
public class ShortUrlRedirectController {

    private final ShortUrlService shortUrlService;

    public ShortUrlRedirectController(final ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @GetMapping("/")
    public String main() {
        return "main";
    }

    @GetMapping("/{shortUrl}")
    public String shortUrl(@PathVariable String shortUrl) {
        final ShortUrlResponse shortUrlResponse = shortUrlService.findByShortUrl(shortUrl);
        return "redirect:" + shortUrlResponse.getOriginUrl();
    }
}
