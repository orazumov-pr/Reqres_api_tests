package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResourceResponseModel(
        ResourceData data,
        Support support
) {
    public record ResourceData(
            int id,
            String name,
            int year,
            String color,
            @JsonProperty("pantone_value") String pantoneValue
    ) {}

    public record Support(
            String url,
            String text
    ) {}
}