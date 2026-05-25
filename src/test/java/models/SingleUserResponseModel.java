package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SingleUserResponseModel(
        UserData data,
        Support support
) {
    public record UserData(
            int id,
            String email,
            @JsonProperty("first_name") String firstName,
            @JsonProperty("last_name") String lastName,
            String avatar
    ) {}

    public record Support(
            String url,
            String text
    ) {}
}
