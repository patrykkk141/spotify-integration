package pl.patryk.spotifyintegration.converter;

import pl.patryk.spotifyintegration.dto.track.TrackCollectionWrapper;
import pl.patryk.spotifyintegration.model.TopTracks;

public class TracksConverter {

  public static TopTracks convertToTopTracks(TrackCollectionWrapper trackCollection) {
    TopTracks topTracks = new TopTracks();
    topTracks.setTopTracks(trackCollection.getTracks());

    return topTracks;
  }

  public static TrackCollectionWrapper convertToTracksCollection(TopTracks topTracks) {
    TrackCollectionWrapper trackCollection = new TrackCollectionWrapper();
    trackCollection.setTracks(topTracks.getTopTracks());

    return trackCollection;
  }
}
