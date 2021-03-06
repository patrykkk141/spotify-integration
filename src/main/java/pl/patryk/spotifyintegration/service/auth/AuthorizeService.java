package pl.patryk.spotifyintegration.service.auth;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.patryk.spotifyintegration.configuration.Properties;
import pl.patryk.spotifyintegration.dto.auth.AccessTokenResponse;

@Service
public class AuthorizeService implements IAuthorizeService {

    private Properties properties;
    private RestTemplate authClient;

    @Autowired
    public AuthorizeService(Properties properties,
        @Qualifier("spotifyAuthClient") RestTemplate authClient) {
        this.properties = properties;
        this.authClient = authClient;
    }

    @Override
    public String getAuthorizeUrl() {
        return properties.getAuthorizationUrl() +
            "/authorize?client_id=" +
            properties.getClientId() +
            "&response_type=code" +
            "&redirect_uri=" +
            URLEncoder.encode(properties.getRedirectUrl(), StandardCharsets.UTF_8);
    }

    @Override
    public AccessTokenResponse getAccessToken(String code) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.put("code", Collections.singletonList(code));
        form.put("redirect_uri", Collections.singletonList(properties.getRedirectUrl()));
        form.put("grant_type", Collections.singletonList("authorization_code"));

        ResponseEntity<AccessTokenResponse> response = authClient
            .postForEntity("/api/token", form, AccessTokenResponse.class);
        return response.getBody();
    }

    @Override
    public AccessTokenResponse getRefreshToken(@NotNull String refreshToken) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.put("refresh_token", Collections.singletonList(refreshToken));
        form.put("grant_type", Collections.singletonList("refresh_token"));

        ResponseEntity<AccessTokenResponse> response = authClient
            .postForEntity("/api/token", form, AccessTokenResponse.class);
        return response.getBody();
    }
}
