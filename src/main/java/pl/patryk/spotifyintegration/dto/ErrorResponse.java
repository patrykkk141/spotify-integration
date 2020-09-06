package pl.patryk.spotifyintegration.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    public ErrorResponse() {
        this.timestamp = System.currentTimeMillis();
    }

    public ErrorResponse(int code, String description) {
        this.code = code;
        this.description = description;
        this.timestamp = System.currentTimeMillis();
    }

    private int code;
    private String description;
    private long timestamp;

}
