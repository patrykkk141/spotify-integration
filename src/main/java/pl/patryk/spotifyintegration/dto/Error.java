package pl.patryk.spotifyintegration.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Error {

    public Error() {
        this.timestamp = System.currentTimeMillis();
    }

    public Error(String code, String description) {
        this.code = code;
        this.description = description;
        this.timestamp = System.currentTimeMillis();
    }

    private String code;
    private String description;
    private long timestamp;

}
