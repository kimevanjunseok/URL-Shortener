package shorturl.shortener.utils;

import org.springframework.stereotype.Component;

@Component
public class UrlEncoder {

    private final int RADIX = 62;
    private final String BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public String encoding(long param) {
        final StringBuilder builder = new StringBuilder();
        while (param > 0) {
            builder.append(BASE62.charAt((int) (param % RADIX)));
            param /= RADIX;
        }
        return builder.toString();
    }
}
