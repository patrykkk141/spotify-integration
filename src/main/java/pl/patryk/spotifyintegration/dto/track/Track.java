package pl.patryk.spotifyintegration.dto.track;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.patryk.spotifyintegration.dto.SpotifyModelType;
import pl.patryk.spotifyintegration.dto.artist.SimplifiedArtist;

@Data
@NoArgsConstructor
public class Track {

  private String id;
  private String name;
  private Integer popularity;
  @JsonProperty("track_number")
  private String trackNumber;
  private SpotifyModelType type;
  private Set<SimplifiedArtist> artists;
  @JsonProperty("disc_number")
  private String discNumber;
  @JsonProperty("duration_ms")
  private Integer durationMs;
  private Boolean explicit;

}
