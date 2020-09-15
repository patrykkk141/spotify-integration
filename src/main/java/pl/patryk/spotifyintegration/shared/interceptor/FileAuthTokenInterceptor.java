package pl.patryk.spotifyintegration.shared.interceptor;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import pl.patryk.spotifyintegration.service.token.ITokenService;

@Slf4j
@Component
@Order(Ordered.LOWEST_PRECEDENCE - 10)
@Profile("fileToken")
public class FileAuthTokenInterceptor implements ISpotifyAuthInterceptor {

  private final ITokenService tokenService;

  @Autowired
  public FileAuthTokenInterceptor(
      @Lazy ITokenService tokenService) {
    this.tokenService = tokenService;
  }

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body,
      ClientHttpRequestExecution execution) throws IOException {
    log.info(getClass().getName());

    if (!CollectionUtils.isEmpty(request.getHeaders().get(HttpHeaders.AUTHORIZATION))) {
      log.info("Authorization header already been set, skipping.");
    } else {
      request.getHeaders().setBearerAuth(tokenService.getAccessToken());
    }

    return execution.execute(request, body);
  }

}
