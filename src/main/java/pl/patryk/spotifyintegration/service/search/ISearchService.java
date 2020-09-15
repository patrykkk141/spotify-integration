package pl.patryk.spotifyintegration.service.search;

import javax.validation.constraints.NotNull;
import pl.patryk.spotifyintegration.dto.SpotifyModelType;

public interface ISearchService<R> {

  R getResult(@NotNull String q, @NotNull SpotifyModelType type);

}
