package pl.patryk.spotifyintegration.configuration;

import javax.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spotify.api")
public class Properties {

    @NotEmpty
    private String url;
    @NotEmpty
    private String authorizationUrl;
    @NotEmpty
    private String clientId;
    @NotEmpty
    private String clientSecret;
    @NotEmpty
    private String redirectUrl;
    @NotEmpty
    private String defaultCountryCode;

}

