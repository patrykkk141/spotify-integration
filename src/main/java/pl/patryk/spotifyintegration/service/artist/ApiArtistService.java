package pl.patryk.spotifyintegration.service.artist;

import javax.validation.constraints.NotNull;
import pl.patryk.spotifyintegration.dto.track.TrackCollectionWrapper;

public interface ApiArtistService {

  TrackCollectionWrapper getArtistTopTracks(@NotNull String artistId);

}
