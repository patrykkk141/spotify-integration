package pl.patryk.spotifyintegration.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotEmpty;

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

}

