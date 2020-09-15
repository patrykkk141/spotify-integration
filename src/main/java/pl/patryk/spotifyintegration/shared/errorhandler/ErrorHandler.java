package pl.patryk.spotifyintegration.shared.errorhandler;

import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import pl.patryk.spotifyintegration.dto.ErrorResponse;
import pl.patryk.spotifyintegration.dto.spotify_error.ISpotifyError;
import pl.patryk.spotifyintegration.dto.spotify_error.SpotifyApiErrorWrapper;
import pl.patryk.spotifyintegration.dto.spotify_error.SpotifyAuthError;
import pl.patryk.spotifyintegration.exception.EntityNotFoundException;
import pl.patryk.spotifyintegration.exception.TokenNotFoundException;

@ControllerAdvice
@Slf4j
public class ErrorHandler {

  private final Set<IErrorDeserializer> errorDeserializers;

  @Autowired
  public ErrorHandler(
      Set<IErrorDeserializer> errorDeserializers) {
    this.errorDeserializers = errorDeserializers;
  }

  @ExceptionHandler(value = {HttpClientErrorException.class})
  protected ResponseEntity<ErrorResponse> handleSpotifyClientError(HttpClientErrorException e) {
    log.info("Handling spotify client exception");
    log.error("Spotify client exception", e);

    ErrorResponse response;
    ISpotifyError error = deserializeErrorResponse(e.getResponseBodyAsByteArray());
    if (error instanceof SpotifyApiErrorWrapper) {
      SpotifyApiErrorWrapper spotifyApiErrorWrapper = (SpotifyApiErrorWrapper) error;
      response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
          spotifyApiErrorWrapper.getError().getMessage());
    } else if (error instanceof SpotifyAuthError) {
      SpotifyAuthError authError = (SpotifyAuthError) error;
      response = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),
          authError.getErrorDescription());
    } else {
      response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error");
    }

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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

  private ISpotifyError deserializeErrorResponse(byte[] responseBody) {
    if (CollectionUtils.isEmpty(errorDeserializers)) {
      throw new IllegalStateException("Any IErrorDeserializer was found.");
    }

    ISpotifyError error;
    for (IErrorDeserializer deserializer : errorDeserializers) {
      error = deserializer.deserialize(responseBody);
      if (error != null) {
        return error;
      }
    }

    log.error("Unrecognized Spotify error - {}", responseBody);
    throw new IllegalStateException("Unrecognized Spotify error");
  }
}
