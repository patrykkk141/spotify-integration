package pl.patryk.spotifyintegration.service;

import org.springframework.http.ResponseEntity;
import pl.patryk.spotifyintegration.dto.auth.AccessTokenResponse;

public interface AuthorizeService {

    ResponseEntity<AccessTokenResponse> getAccessToken(String code);

    String getAuthorizeUrl();
}
