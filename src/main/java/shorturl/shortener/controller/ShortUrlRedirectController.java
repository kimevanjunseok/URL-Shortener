package shorturl.shortener.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import shorturl.shortener.dto.OriginUrlResponse;
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

    @GetMapping("/{shortUrl:[A-Za-z0-9]+}")
    public String shortUrl(@PathVariable final String shortUrl) {
        final OriginUrlResponse originUrlResponse = shortUrlService.findByShortUrl(shortUrl);
        return "redirect:" + originUrlResponse.getOriginUrl();
    }
}
