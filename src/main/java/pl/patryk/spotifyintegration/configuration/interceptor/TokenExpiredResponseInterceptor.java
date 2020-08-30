package pl.patryk.spotifyintegration.configuration.interceptor;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import pl.patryk.spotifyintegration.dto.auth.AccessTokenResponse;
import pl.patryk.spotifyintegration.service.auth.AuthorizeService;
import pl.patryk.spotifyintegration.service.token.TokenService;

@Slf4j
@Component
@Order(Ordered.LOWEST_PRECEDENCE - 9)
public class TokenExpiredResponseInterceptor implements ClientHttpRequestInterceptor {

  private static int maxAttempts = 3;

  private final AuthorizeService authorizeService;
  private final TokenService tokenService;

  @Autowired
  public TokenExpiredResponseInterceptor(
      AuthorizeService authorizeService, TokenService tokenService) {
    this.authorizeService = authorizeService;
    this.tokenService = tokenService;
  }

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body,
      ClientHttpRequestExecution execution) throws IOException {
    log.info(getClass().getCanonicalName());
    ClientHttpResponse response = execution.execute(request, body);

    if (response.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
      log.warn("Spotify client returned code {}, getting refresh token",
          HttpStatus.UNAUTHORIZED.value());

      for (int i = 0; i < maxAttempts; i++) {
        log.debug("Trying to refresh token and repeat request. Attempt - {}", i);
        AccessTokenResponse tokenResponse = authorizeService
            .getAccessToken(tokenService.getRefreshToken());

        request.getHeaders().setBearerAuth(tokenResponse.getAccessToken());
        response = execution.execute(request, body);

        if (!response.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
          log.info("Access token successfully refreshed.");
          tokenService.saveTokenResponse(tokenResponse);
          break;
        }
      }
    }

    return response;
  }

}
