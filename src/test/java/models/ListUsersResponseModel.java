package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ListUsersResponseModel(
        int page,
        @JsonProperty("per_page") int perPage,
        int total,
        @JsonProperty("total_pages") int totalPages,
        List<UserData> data,
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
