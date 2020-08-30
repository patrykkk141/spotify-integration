package pl.patryk.spotifyintegration.service.search;

import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.patryk.spotifyintegration.dto.SpotifyModelType;
import pl.patryk.spotifyintegration.dto.artist.ArtistSearchResultWrapper;

@Service
public class SearchArtistService implements
    SearchService<ResponseEntity<ArtistSearchResultWrapper>> {

  public static final String SEARCH_RESOURCE = "/search?q={q}&type={type}";

  private RestTemplate apiClient;

  @Autowired
  public SearchArtistService(@Qualifier("spotifyClient") RestTemplate apiClient) {
    this.apiClient = apiClient;
  }

  @Override
  public ResponseEntity<ArtistSearchResultWrapper> getResult(@NotNull String q,
      @NotNull SpotifyModelType type) {
    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("q", q);
    queryParams.put("type", type.getName());

    return apiClient.getForEntity(SEARCH_RESOURCE, ArtistSearchResultWrapper.class, queryParams);
  }
}
