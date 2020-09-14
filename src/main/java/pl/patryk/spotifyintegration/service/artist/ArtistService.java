package pl.patryk.spotifyintegration.service.artist;

import java.util.Optional;
import javax.validation.constraints.NotNull;
import pl.patryk.spotifyintegration.model.TopTracks;

public interface ArtistService {

  Optional<TopTracks> getStoredTopTracks(@NotNull String artistId);

  TopTracks storeArtistTopTracksFromApi(@NotNull String artistsId);

  TopTracks updateArtistTopTrack(@NotNull String artistsId);

  void deleteArtistTopTracks(@NotNull String artistsId);

}
