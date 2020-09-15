package pl.patryk.spotifyintegration.service.artist;

import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.patryk.spotifyintegration.configuration.Properties;
import pl.patryk.spotifyintegration.dto.track.TrackCollectionWrapper;

@Service
public class ApiArtistService implements IApiArtistService {

  private static final String ARTIST_RESOURCE = "/artists";
  private static final String TOP_TRACKS_RESOURCE = "/top-tracks";
  private static final String COUNTRY_CODE = "?country={country}";

  private RestTemplate apiClient;
  private Properties properties;

  @Autowired
  public ApiArtistService(@Qualifier("spotifyClient") RestTemplate apiClient,
      Properties properties) {
    this.apiClient = apiClient;
    this.properties = properties;
  }

  @Override
  public TrackCollectionWrapper getArtistTopTracks(@NotNull String artistId) {
    Map<String, String> uriParams = new HashMap<>();
    uriParams.put("country", properties.getDefaultCountryCode());

    return apiClient
        .getForEntity(ARTIST_RESOURCE + "/" + artistId + "/" + TOP_TRACKS_RESOURCE + COUNTRY_CODE,
            TrackCollectionWrapper.class, uriParams).getBody();
  }
}
