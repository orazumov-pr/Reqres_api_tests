package models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthorizationResponseModel(
        int id,
        String token
) {
}
