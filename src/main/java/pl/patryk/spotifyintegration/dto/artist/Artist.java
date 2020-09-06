package pl.patryk.spotifyintegration.dto.artist;

import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Artist extends SimplifiedArtist {

  private Set<String> genres;
  private Integer popularity;
  private Followers followers;

}
