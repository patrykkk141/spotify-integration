package pl.patryk.spotifyintegration.shared;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import pl.patryk.spotifyintegration.dto.ErrorResponse;
import pl.patryk.spotifyintegration.exception.EntityNotFoundException;
import pl.patryk.spotifyintegration.exception.TokenNotFoundException;

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
    log.info("Handling spotify client exception");
    log.error("Spotify client exception", e);

    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {TokenNotFoundException.class})
  protected ResponseEntity<ErrorResponse> handleTokenNotFoundException(TokenNotFoundException e) {
    log.info("Handling " + TokenNotFoundException.class.getSimpleName());

    ErrorResponse response = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),
        "Token not found. Please repeat login process");
    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(value = {EntityNotFoundException.class})
  protected ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
    log.info("Handling " + EntityNotFoundException.class.getSimpleName());

    ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
        "Entity not found." + e.getMessage());

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }
}
