package pl.patryk.spotifyintegration.service.artist;

import java.util.Optional;
import javax.validation.constraints.NotNull;
import pl.patryk.spotifyintegration.model.TopTracks;

public interface DbArtistService {

  TopTracks save(@NotNull TopTracks topTracks);

  Optional<TopTracks> getFromDatabase(@NotNull String artistId);

  TopTracks updateTopTracks(@NotNull String artistId, @NotNull TopTracks topTracks);

  void deleteTopTrack(@NotNull String artistId);

}
