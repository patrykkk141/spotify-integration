package pl.patryk.spotifyintegration.dto.track;

import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TrackCollectionWrapper {

  private Set<Track> tracks;

}
