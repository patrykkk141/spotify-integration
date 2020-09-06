package pl.patryk.spotifyintegration.dto.artist;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.patryk.spotifyintegration.dto.SpotifyModelType;

@Data
@NoArgsConstructor
public class SimplifiedArtist {

  private String id;
  private String name;
  private SpotifyModelType type;

}
