package pl.patryk.spotifyintegration.shared.errorhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.patryk.spotifyintegration.dto.spotify_error.ISpotifyError;
import pl.patryk.spotifyintegration.dto.spotify_error.SpotifyAuthError;

@Slf4j
@Component
public class SpotifyAuthErrorDeserializer implements IErrorDeserializer {

  private final ObjectMapper objectMapper;

  @Autowired
  public SpotifyAuthErrorDeserializer(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public ISpotifyError deserialize(byte[] responseBody) {
    log.info(getClass().getName());

    SpotifyAuthError spotifyAuthError = null;
    try {
      spotifyAuthError = objectMapper.readValue(responseBody, SpotifyAuthError.class);
    } catch (IOException e) {
      log.info("Parsing error to {} failed. Returning null", getClass().getName());
    }

    return spotifyAuthError;
  }

}
