package pl.patryk.spotifyintegration.shared.errorhandler;

import pl.patryk.spotifyintegration.dto.spotify_error.ISpotifyError;

@FunctionalInterface
public interface IErrorDeserializer {

  ISpotifyError deserialize(byte[] responseBody);

}
