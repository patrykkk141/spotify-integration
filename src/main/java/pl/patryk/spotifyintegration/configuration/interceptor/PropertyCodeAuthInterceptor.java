package pl.patryk.spotifyintegration.configuration.interceptor;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import pl.patryk.spotifyintegration.dto.auth.AccessTokenResponse;
import pl.patryk.spotifyintegration.exception.PropertyNotFoundException;
import pl.patryk.spotifyintegration.service.auth.AuthorizeService;

@Slf4j
@Profile("propertyCodeAuth")
@Component
public class PropertyCodeAuthInterceptor implements SpotifyAuthInterceptor {

  private final AuthorizeService authorizeService;

  @Value("${spotify.api.authCode}")
  private String code;

  @Autowired
  public PropertyCodeAuthInterceptor(
      AuthorizeService authorizeService) {
    this.authorizeService = authorizeService;
  }

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body,
      ClientHttpRequestExecution execution) throws IOException {
    log.info("PropertyCodeAuthInterceptor");

    if (!CollectionUtils.isEmpty(request.getHeaders().get(HttpHeaders.AUTHORIZATION))) {
      log.warn("Authorization header already been set, skipping.");
    } else if (!StringUtils.isEmpty(code)) {
      String accessToken = getAccessToken();
      request.getHeaders().setBearerAuth(accessToken);
    } else {
      throw new PropertyNotFoundException("spotify.api.authCode");
    }

    return execution.execute(request, body);
  }

  private String getAccessToken() {
    AccessTokenResponse accessTokenResponse = authorizeService
        .getAccessToken(code);

    return accessTokenResponse.getAccessToken();
  }
}
