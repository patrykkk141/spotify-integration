package pl.patryk.spotifyintegration.service.artist;

import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import pl.patryk.spotifyintegration.dto.track.TrackCollectionWrapper;

@Service
public interface ArtistService {

  TrackCollectionWrapper getArtistTopTracks(@NotNull String artistId);

}
