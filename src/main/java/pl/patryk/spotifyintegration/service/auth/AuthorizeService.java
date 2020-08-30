package pl.patryk.spotifyintegration.service.auth;

import pl.patryk.spotifyintegration.dto.auth.AccessTokenResponse;

public interface AuthorizeService {

    AccessTokenResponse getAccessToken(String code);

    String getAuthorizeUrl();
}
