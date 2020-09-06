package pl.patryk.spotifyintegration.dto.album;

import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.patryk.spotifyintegration.dto.SpotifyModelType;
import pl.patryk.spotifyintegration.dto.artist.SimplifiedArtist;

@Data
@NoArgsConstructor
public class SimplifiedAlbum {

  private String id;
  private String name;
  private SpotifyModelType type;
  private Set<SimplifiedArtist> artists;

}
