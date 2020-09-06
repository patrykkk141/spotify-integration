package pl.patryk.spotifyintegration.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import pl.patryk.spotifyintegration.dto.ErrorResponse;
import pl.patryk.spotifyintegration.dto.spotify_error.SpotifyErrorWrapper;

@ControllerAdvice
@Slf4j
public class ErrorHandler {

  private final ObjectMapper objectMapper;

  @Autowired
  public ErrorHandler(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @ExceptionHandler(value = {HttpClientErrorException.class})
  protected ResponseEntity<ErrorResponse> handleSpotifyClientError(HttpClientErrorException e) {
    log.error("Spotify client exception", e);
    SpotifyErrorWrapper spotifyError = null;
    try {
      spotifyError = objectMapper
          .readValue(e.getResponseBodyAsByteArray(), SpotifyErrorWrapper.class);
    } catch (IOException ex) {
      log.error("Cannot parse SpotifyException", ex);
    }

    ErrorResponse response = new ErrorResponse();
    if (spotifyError != null && spotifyError.getError() != null) {
      response.setCode(spotifyError.getError().getStatus());
      response.setDescription(spotifyError.getError().getMessage());
    } else {
      response.setCode(HttpStatus.BAD_REQUEST.value());
      response.setDescription("Spotify returned unknown error");
    }

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }
}
