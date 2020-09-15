package pl.patryk.spotifyintegration.shared.interceptor;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import pl.patryk.spotifyintegration.exception.PropertyNotFoundException;

@Slf4j
@Component
@Profile("propertyToken")
@Order(Ordered.LOWEST_PRECEDENCE - 10)
public final class PropertyAuthTokenInterceptor implements ISpotifyAuthInterceptor {

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
      throw new PropertyNotFoundException("spotify.api.token");
    }

    return execution.execute(request, body);
  }
}

