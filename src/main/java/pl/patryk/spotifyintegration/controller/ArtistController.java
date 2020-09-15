package pl.patryk.spotifyintegration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.patryk.spotifyintegration.dto.SpotifyModelType;
import pl.patryk.spotifyintegration.dto.artist.ArtistSearchResultWrapper;
import pl.patryk.spotifyintegration.dto.track.TrackCollectionWrapper;
import pl.patryk.spotifyintegration.exception.EntityNotFoundException;
import pl.patryk.spotifyintegration.model.TopTracks;
import pl.patryk.spotifyintegration.service.artist.IApiArtistService;
import pl.patryk.spotifyintegration.service.artist.IArtistService;
import pl.patryk.spotifyintegration.service.search.SearchArtistService;

@RestController
@RequestMapping("/artist")
public class ArtistController {

  private final SearchArtistService searchArtistService;
  private final IApiArtistService apiArtistService;
  private final IArtistService artistService;

  @Autowired
  public ArtistController(SearchArtistService searchArtistService,
      IApiArtistService apiArtistService,
      IArtistService artistService) {
    this.searchArtistService = searchArtistService;
    this.apiArtistService = apiArtistService;
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
  public ResponseEntity<TrackCollectionWrapper> getArtistTopTracksFromSpotify(
      @PathVariable("id") String id) {

    TrackCollectionWrapper result = apiArtistService.getArtistTopTracks(id);
    return ResponseEntity.ok(result);
  }

  @GetMapping("/{id}/top/saved")
  public ResponseEntity<TopTracks> getArtistSavedTopTracks(@PathVariable("id") String id) {

    TopTracks result = artistService.getStoredTopTracks(id).orElseThrow(
        () -> new EntityNotFoundException(TopTracks.class.getSimpleName(), id));
    return ResponseEntity.ok(result);
  }

  @PostMapping("/{id}/top/save")
  public ResponseEntity<TopTracks> saveArtistTopTracks(@PathVariable("id") String id) {

    TopTracks result = artistService.storeArtistTopTracksFromApi(id);
    return ResponseEntity.ok(result);
  }

  @PutMapping("/{id}/top/update")
  public ResponseEntity<TopTracks> updateArtistTopTracks(@PathVariable("id") String id) {

    TopTracks result = artistService.updateArtistTopTrack(id);
    return ResponseEntity.ok(result);
  }

  @DeleteMapping("/{id}/top/delete")
  public ResponseEntity<Void> deleteArtistTopTracks(@PathVariable("id") String id) {

    artistService.deleteArtistTopTracks(id);
    return ResponseEntity.ok(null);
  }
}
