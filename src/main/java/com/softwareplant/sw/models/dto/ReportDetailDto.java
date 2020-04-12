package com.softwareplant.sw.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReportDetailDto {
    @JsonProperty("film_id")
    private Long filmId;

    @JsonProperty("film_name")
    private String filmName;

    @JsonProperty("character_id")
    private Long characterId;

    @JsonProperty("character_name")
    private String characterName;

    @JsonProperty("planet_id")
    private Long planetId;

    @JsonProperty("planet_name")
    private String planetName;
}
