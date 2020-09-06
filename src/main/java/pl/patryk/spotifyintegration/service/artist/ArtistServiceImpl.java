package pl.patryk.spotifyintegration.service.artist;

import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.patryk.spotifyintegration.dto.track.TrackCollectionWrapper;

@Service
public class ArtistServiceImpl implements ArtistService {

  private static final String ARTIST_RESOURCE = "/artists";
  private static final String TOP_TRACKS_RESOURCE = "/top-tracks";

  private RestTemplate apiClient;

  @Autowired
  public ArtistServiceImpl(@Qualifier("spotifyClient") RestTemplate apiClient) {
    this.apiClient = apiClient;
  }

  @Override
  public TrackCollectionWrapper getArtistTopTracks(@NotNull String artistId) {
    return apiClient.getForEntity(ARTIST_RESOURCE + "/" + artistId + "/" + TOP_TRACKS_RESOURCE,
        TrackCollectionWrapper.class).getBody();
  }
}
