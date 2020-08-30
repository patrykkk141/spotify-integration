package pl.patryk.spotifyintegration.exception;

public class PropertyNotFoundException extends RuntimeException {

  public PropertyNotFoundException(String propertyName) {
    super("Property " + propertyName + " not found.");
  }
}
