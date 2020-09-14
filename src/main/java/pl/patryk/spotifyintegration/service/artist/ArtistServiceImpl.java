package pl.patryk.spotifyintegration.service.artist;

import java.util.Optional;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.patryk.spotifyintegration.converter.TracksConverter;
import pl.patryk.spotifyintegration.dto.track.TrackCollectionWrapper;
import pl.patryk.spotifyintegration.model.TopTracks;

@Slf4j
@Service
public class ArtistServiceImpl implements ArtistService {

  private final ApiArtistService apiArtistService;
  private final DbArtistService dbArtistService;

  @Autowired
  public ArtistServiceImpl(
      ApiArtistService apiArtistService,
      DbArtistService dbArtistService) {
    this.apiArtistService = apiArtistService;
    this.dbArtistService = dbArtistService;
  }

  @Override
  public Optional<TopTracks> getStoredTopTracks(@NotNull String artistId) {
    return dbArtistService.getFromDatabase(artistId);
  }

  @Override
  public TopTracks storeArtistTopTracksFromApi(@NotNull String artistsId) {
    log.info("Storing artist top tracks in database. Artist id - {}", artistsId);
    TrackCollectionWrapper trackCollection = apiArtistService.getArtistTopTracks(artistsId);

    TopTracks topTracks = TracksConverter.convertToTopTracks(trackCollection);
    topTracks.setArtistId(artistsId);

    return dbArtistService.save(topTracks);
  }


  @Override
  public TopTracks updateArtistTopTrack(@NotNull String artistsId) {
    log.info("Updating artist top tracks for artist id - {}", artistsId);
    TrackCollectionWrapper trackCollection = apiArtistService.getArtistTopTracks(artistsId);

    TopTracks topTracks = TracksConverter.convertToTopTracks(trackCollection);
    topTracks.setArtistId(artistsId);

    return dbArtistService.updateTopTracks(artistsId, topTracks);
  }

  @Override
  public void deleteArtistTopTracks(@NotNull String artistsId) {
    log.info("Deleting top tracks for artist id - {}", artistsId);
    dbArtistService.deleteTopTrack(artistsId);
  }
}
