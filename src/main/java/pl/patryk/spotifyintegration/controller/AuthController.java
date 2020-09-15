package pl.patryk.spotifyintegration.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.patryk.spotifyintegration.dto.auth.AccessTokenResponse;
import pl.patryk.spotifyintegration.service.auth.IAuthorizeService;
import pl.patryk.spotifyintegration.service.token.ITokenService;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final IAuthorizeService authorizeService;
  private final ITokenService tokenService;

  @Autowired
  public AuthController(IAuthorizeService authorizeService,
      ITokenService tokenService) {
    this.authorizeService = authorizeService;
    this.tokenService = tokenService;
  }

  @GetMapping
  public ResponseEntity<String> getAuthorizeUrl() {
    log.info("Getting Authorize URL");

    return ResponseEntity.ok(authorizeService.getAuthorizeUrl());
  }

  @GetMapping("/authorizeCallback")
  public ResponseEntity<String> handleAuthorizeCallback(
      @RequestParam(value = "code", required = false) String code,
      @RequestParam(value = "error", required = false) String error) {
    log.info("Handling authorize callback");

    if (error == null) {
      log.debug("Requesting for access token");
      AccessTokenResponse tokenResponse = authorizeService.getAccessToken(code);
      tokenService.saveTokenResponse(tokenResponse);

      return ResponseEntity.ok("OK");
    } else {
      return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
  }
}
