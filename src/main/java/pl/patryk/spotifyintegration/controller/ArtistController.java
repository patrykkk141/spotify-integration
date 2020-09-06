package pl.patryk.spotifyintegration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.patryk.spotifyintegration.dto.SpotifyModelType;
import pl.patryk.spotifyintegration.dto.artist.ArtistSearchResultWrapper;
import pl.patryk.spotifyintegration.dto.track.TrackCollectionWrapper;
import pl.patryk.spotifyintegration.service.artist.ArtistService;
import pl.patryk.spotifyintegration.service.search.SearchArtistService;

@RestController
@RequestMapping("/artist")
public class ArtistController {

  private final SearchArtistService searchArtistService;
  private final ArtistService artistService;

  @Autowired
  public ArtistController(SearchArtistService searchArtistService,
      ArtistService artistService) {
    this.searchArtistService = searchArtistService;
    this.artistService = artistService;
  }

  @GetMapping("/search")
  public ResponseEntity<ArtistSearchResultWrapper> searchForArtist(
      @RequestParam("q") String query) {

    ArtistSearchResultWrapper result = searchArtistService
        .getResult(query, SpotifyModelType.ARTIST);
    return ResponseEntity.ok(result);
  }

  @GetMapping("/{id}/top")
  public ResponseEntity<TrackCollectionWrapper> getArtistTopTrack(@PathVariable("id") String id) {

    TrackCollectionWrapper result = artistService.getArtistTopTracks(id);
    return ResponseEntity.ok(result);
  }

  @PostMapping("/{id}/top/save")
  public ResponseEntity<TrackCollectionWrapper> saveArtistTopTracks(@PathVariable("id") String id) {

    return null;
  }
}
