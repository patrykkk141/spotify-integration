package pl.patryk.spotifyintegration.configuration.interceptor;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import pl.patryk.spotifyintegration.exception.PropertyTokenNotFoundException;

@Slf4j
@Component
@Profile("propertyToken")
public final class PropertyAuthTokenInterceptor implements SpotifyAuthInterceptor {

  @Value("${spotify.api.token}")
  private String token;

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body,
      ClientHttpRequestExecution execution) throws IOException {
    log.info("PropertyToken");

    if (!CollectionUtils.isEmpty(request.getHeaders().get(HttpHeaders.AUTHORIZATION))) {
      log.warn("Authorization header already been set, skipping.");
    } else if (!StringUtils.isEmpty(token)) {
      log.info("Setting token from properties.");
      request.getHeaders().setBearerAuth(token);
    } else {
      throw new PropertyTokenNotFoundException(
          "Token not found in properties, please fill spotify.api.token property");
    }

    return execution.execute(request, body);
  }
}

