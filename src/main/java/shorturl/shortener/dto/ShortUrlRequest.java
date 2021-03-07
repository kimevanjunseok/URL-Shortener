package shorturl.shortener.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

public class ShortUrlRequest {

    @NotBlank(message = "URL은 공백일 수 없습니다.")
    @URL(message = "올바른 URL 형식이 아닙니다.")
    private String url;

    protected ShortUrlRequest() {}

    public ShortUrlRequest(final String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
