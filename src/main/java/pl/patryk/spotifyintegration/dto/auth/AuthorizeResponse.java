package pl.patryk.spotifyintegration.dto.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthorizeResponse {

  private String code;
  private String state;
  private String error;

}
