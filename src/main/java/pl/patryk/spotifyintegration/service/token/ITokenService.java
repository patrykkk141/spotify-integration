package pl.patryk.spotifyintegration.service.token;

import javax.validation.constraints.NotNull;
import pl.patryk.spotifyintegration.dto.auth.AccessTokenResponse;

public interface ITokenService {

  void saveTokenResponse(@NotNull AccessTokenResponse tokenResponse);

  String getAccessToken();

  String getRefreshToken();

}
