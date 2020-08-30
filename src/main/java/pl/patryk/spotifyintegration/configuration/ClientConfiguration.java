package pl.patryk.spotifyintegration.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import pl.patryk.spotifyintegration.configuration.interceptor.SpotifyAuthInterceptor;

@Configuration
public class ClientConfiguration {

  private Properties properties;
  private SpotifyAuthInterceptor spotifyAuthInterceptor;

  @Autowired
  public ClientConfiguration(Properties properties,
      SpotifyAuthInterceptor spotifyAuthInterceptor) {
    this.properties = properties;
    this.spotifyAuthInterceptor = spotifyAuthInterceptor;
  }

  @Bean(name = "spotifyClient")
  public RestTemplate getClientBean(RestTemplateBuilder builder) {
    return builder
        .rootUri(properties.getUrl())
        .setReadTimeout(Duration.ofMinutes(1))
        .setConnectTimeout(Duration.ofMinutes(1))
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
        .interceptors(spotifyAuthInterceptor)
        .build();
  }

  @Bean(name = "spotifyAuthClient")
  public RestTemplate getAuthClientBean(RestTemplateBuilder builder) {
    return builder
        .rootUri(properties.getAuthorizationUrl())
        .setReadTimeout(Duration.ofMinutes(1))
        .setConnectTimeout(Duration.ofMinutes(1))
        .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        .basicAuthentication(properties.getClientId(), properties.getClientSecret())
        .build();
  }

  @Bean
  public ObjectMapper getObjectMapperBean() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    return objectMapper;
  }

}
