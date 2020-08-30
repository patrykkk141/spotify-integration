package pl.patryk.spotifyintegration.service;

import pl.patryk.spotifyintegration.dto.auth.AccessTokenResponse;

public interface AuthorizeService {

    AccessTokenResponse getAccessToken(String code);

    String getAuthorizeUrl();
}
