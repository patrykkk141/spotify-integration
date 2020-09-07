package pl.patryk.spotifyintegration.service.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.patryk.spotifyintegration.dto.auth.AccessTokenResponse;
import pl.patryk.spotifyintegration.exception.TokenNotFoundException;

@Service
@Slf4j
public class FileTokenService implements TokenService {

  private static final String TOKEN_FILE_NAME = "tokens.json";

  private final ObjectMapper objectMapper;

  @Autowired
  public FileTokenService(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public void saveTokenResponse(@NotNull AccessTokenResponse tokenResponse) {
    log.info("Saving token response");
    try {
      saveToFile(tokenResponse);
    } catch (IOException e) {
      log.error("Exception occurred during saving token object to file - {}", tokenResponse, e);
      throw new IllegalStateException("Cannot save token to file", e);
    }
  }

  @Override
  public String getAccessToken() {
    log.info("Getting access token from file");

    AccessTokenResponse tokenObject = getTokenObjectFromFile(TOKEN_FILE_NAME);
    return getTokenByType(tokenObject, "ACCESS_TOKEN");
  }

  @Override
  public String getRefreshToken() {
    log.info("Getting refresh token from file");

    AccessTokenResponse tokenObject = getTokenObjectFromFile(TOKEN_FILE_NAME);
    return getTokenByType(tokenObject, "REFRESH_TOKEN");
  }

  private AccessTokenResponse getTokenObjectFromFile(String file) {
    AccessTokenResponse tokenObject;
    try {
      tokenObject = objectMapper.readValue(new File(file), AccessTokenResponse.class);
    } catch (IOException e) {
      log.error("Cannot parse file to AccessTokenResponse type");
      throw new TokenNotFoundException("No file containing tokens was found.");
    }
    return tokenObject;
  }

  private String getTokenByType(AccessTokenResponse response, String tokenType) {
    switch (tokenType) {
      case "ACCESS_TOKEN":
        return response.getAccessToken();
      case "REFRESH_TOKEN":
        return response.getRefreshToken();
      default:
        throw new IllegalStateException(String.format("Unrecognized token type - %s", tokenType));
    }
  }

  private void saveToFile(AccessTokenResponse tokenObject) throws IOException {
    objectMapper.writeValue(new File(TOKEN_FILE_NAME), tokenObject);
  }

}
