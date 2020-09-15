package pl.patryk.spotifyintegration.dto.spotify_error;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SpotifyApiErrorWrapper implements ISpotifyError {

  private SpotifyError error;

}
