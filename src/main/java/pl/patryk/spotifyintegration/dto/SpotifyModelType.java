package pl.patryk.spotifyintegration.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SpotifyModelType {
  ARTIST("artist"), TRACK("track"), ALBUM("album");

  private String name;

  SpotifyModelType(String name) {
    this.name = name;
  }

  @JsonValue
  public String getName() {
    return name;
  }
}
