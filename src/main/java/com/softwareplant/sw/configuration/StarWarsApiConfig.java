package com.softwareplant.sw.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@EnableSpringDataWebSupport
@Configuration
@PropertySource("classpath:application.yaml")
@ConfigurationProperties(prefix = "integration")
@Getter
@Setter
public class StarWarsApiConfig {
    @NotNull
    private IntegrationApp starWarsApi;

    @Getter
    @Setter
    public static class IntegrationApp {
        @NotBlank
        private String url;
    }

    public String getSearchPeople(){
        return UriComponentsBuilder.fromUriString(getStarWarsApi().getUrl()).path("people").query("search=").toUriString();
    }

    public String getSearchPlanets(){
        return UriComponentsBuilder.fromUriString(getStarWarsApi().getUrl()).path("planets").query("search=").toUriString();
    }

    public String getSearchFilms(){
        return UriComponentsBuilder.fromUriString(getStarWarsApi().getUrl()).path("films").query("search=").toUriString();
    }
}