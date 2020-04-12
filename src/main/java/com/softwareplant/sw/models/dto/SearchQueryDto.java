package com.softwareplant.sw.models.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.stereotype.Service;

@Data
@Builder
public class SearchQueryDto {

    @JsonProperty("query_criteria_character_phrase")
    private String queryCriteriaCharacterPhrase;

    @JsonProperty("query_criteria_planet_name")
    private String queryCriteriaPlanetName;
}
