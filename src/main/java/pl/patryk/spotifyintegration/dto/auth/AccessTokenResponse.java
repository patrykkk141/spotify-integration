package pl.patryk.spotifyintegration.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AccessTokenResponse {

    @NotEmpty
    @JsonProperty("access_token")
    private String accessToken;
    @NotEmpty
    @JsonProperty("token_type")
    private String tokenType;
    private String scope;
    @NotNull
    @JsonProperty("expires_in")
    private Integer expiresIn;
    @NotEmpty
    @JsonProperty("refresh_token")
    private String refreshToken;
}
