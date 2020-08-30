package pl.patryk.spotifyintegration.dto.artist;

import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.patryk.spotifyintegration.dto.SpotifyModelType;

@Data
@NoArgsConstructor
public class Artist {

  private String id;
  private Set<String> genres;
  private String name;
  private Integer popularity;
  private SpotifyModelType type;
  private Followers followers;

}
