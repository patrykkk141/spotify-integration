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
    try {
      saveToFile(tokenResponse);
    } catch (IOException e) {
      log.error("Exception occurred during saving token object to file - {}", tokenResponse, e);
      throw new IllegalStateException("Cannot save token to file", e);
    }
  }

  @Override
  public String getAccessToken() {
    AccessTokenResponse tokenObject;
    try {
      tokenObject = readFromFile(TOKEN_FILE_NAME);
      return tokenObject.getAccessToken();
    } catch (IOException e) {
      log.error("Cannot parse file to AccessTokenResponse type");
      throw new TokenNotFoundException("AccessToken not found");
    }
  }

  @Override
  public String getRefreshToken() {
    return null;
  }

  private AccessTokenResponse readFromFile(String file) throws IOException {
    return objectMapper.readValue(new File(file), AccessTokenResponse.class);
  }

  private void saveToFile(AccessTokenResponse tokenObject) throws IOException {
    objectMapper.writeValue(new File(TOKEN_FILE_NAME), tokenObject);
  }

}
