package pl.patryk.spotifyintegration.service.artist;

import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.patryk.spotifyintegration.exception.EntityNotFoundException;
import pl.patryk.spotifyintegration.model.TopTracks;
import pl.patryk.spotifyintegration.repository.ITopTracksRepository;

@Service
public class DBArtistService implements IDBArtistService {

  private final ITopTracksRepository topTracksRepository;

  @Autowired
  public DBArtistService(
      ITopTracksRepository topTracksRepository) {
    this.topTracksRepository = topTracksRepository;
  }

  @Override
  public TopTracks save(@NotNull TopTracks topTracks) {
    return topTracksRepository.save(topTracks);
  }

  @Override
  public Optional<TopTracks> getFromDatabase(@NotNull String artistId) {
    return topTracksRepository.findById(artistId);
  }

  @Override
  public TopTracks updateTopTracks(@NotNull String artistId, @NotNull TopTracks topTracks) {
    if (!topTracksRepository.existsById(artistId)) {
      throw new EntityNotFoundException(TopTracks.class.getSimpleName(), artistId);
    }

    return topTracksRepository.save(topTracks);
  }

  @Override
  public void deleteTopTrack(@NotNull String artistId) {
    topTracksRepository.deleteById(artistId);
  }
}
