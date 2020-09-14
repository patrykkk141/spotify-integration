package pl.patryk.spotifyintegration.model;

import java.util.Set;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import pl.patryk.spotifyintegration.dto.track.Track;

@Data
@Document
public class TopTracks {

  @Id
  @Field(value = "artist_id")
  private String artistId;

  @Field(value = "top_tracks")
  Set<Track> topTracks;

}
