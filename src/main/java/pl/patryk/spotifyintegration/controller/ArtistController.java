package pl.patryk.spotifyintegration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.patryk.spotifyintegration.dto.SpotifyModelType;
import pl.patryk.spotifyintegration.dto.artist.ArtistSearchResultWrapper;
import pl.patryk.spotifyintegration.service.search.SearchArtistService;

@RestController
@RequestMapping("/artist")
public class ArtistController {

  private SearchArtistService searchArtistService;

  @Autowired
  public ArtistController(SearchArtistService searchArtistService) {
    this.searchArtistService = searchArtistService;
  }

  @GetMapping("/search")
  public ResponseEntity<ArtistSearchResultWrapper> searchForArtist(
      @RequestParam("q") String query) {
    return searchArtistService.getResult(query, SpotifyModelType.ARTIST);
  }

}
