package pl.patryk.spotifyintegration.service.auth;

import javax.validation.constraints.NotNull;
import pl.patryk.spotifyintegration.dto.auth.AccessTokenResponse;

public interface AuthorizeService {

  AccessTokenResponse getAccessToken(@NotNull String code);

  AccessTokenResponse getRefreshToken(@NotNull String refreshToken);

  String getAuthorizeUrl();
}
