package pl.patryk.spotifyintegration.dto.artist;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.patryk.spotifyintegration.dto.BaseSearchResult;

@Data
@NoArgsConstructor
public class ArtistSearchResultWrapper {

  private BaseSearchResult<Artist> artists;

}
