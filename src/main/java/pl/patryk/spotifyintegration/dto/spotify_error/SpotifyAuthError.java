package pl.patryk.spotifyintegration.dto.spotify_error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SpotifyAuthError implements ISpotifyError {

  private String error;
  @JsonProperty("error_description")
  private String errorDescription;

}
