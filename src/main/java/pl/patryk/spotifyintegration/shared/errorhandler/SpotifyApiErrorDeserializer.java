package pl.patryk.spotifyintegration.shared.errorhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.patryk.spotifyintegration.dto.spotify_error.ISpotifyError;
import pl.patryk.spotifyintegration.dto.spotify_error.SpotifyApiErrorWrapper;

@Slf4j
@Component
public class SpotifyApiErrorDeserializer implements IErrorDeserializer {

  private final ObjectMapper objectMapper;

  @Autowired
  public SpotifyApiErrorDeserializer(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public ISpotifyError deserialize(byte[] responseBody) {
    log.info(getClass().getName());

    SpotifyApiErrorWrapper spotifyApiError = null;
    try {
      spotifyApiError = objectMapper
          .readValue(responseBody, SpotifyApiErrorWrapper.class);
    } catch (IOException e) {
      log.info("Parsing error to {} failed. Returning null", getClass().getName());
    }
    return spotifyApiError;
  }
}
