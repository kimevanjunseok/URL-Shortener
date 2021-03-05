package shorturl.shortener.controller;

import org.springframework.web.bind.annotation.RestController;

import shorturl.shortener.service.ShortURLService;

@RestController
public class ShortURLController {

    private final ShortURLService shortURLService;

    public ShortURLController(final ShortURLService shortURLService) {
        this.shortURLService = shortURLService;
    }
}
