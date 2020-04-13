package com.softwareplant.sw.service;

import com.softwareplant.sw.models.swApi.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
public class ExternalApiService {

    private final RestTemplate restTemplate;
    private final WebClient webClient;

    @Autowired
    public ExternalApiService() {
        webClient = WebClient.create();
        restTemplate = new RestTemplate();
    }


    @Async
    public CompletableFuture<List<Planet>> planetListCompletableFuture(Planets planets) {
        String next = planets.getNext();
        List<Planet> planetsResults = planets.getResults();

        while (next != null){
            Planets results = queryStarWarsApiRest(next, Planets.class);
            next=results.getNext();
            planetsResults.addAll(results.getResults());
        }

        return CompletableFuture.completedFuture(planetsResults);
    }

    @Async
    public CompletableFuture<List<Person>> peopleListCompletableFuture(People people) {
        String next = people.getNext();
        List<Person> peopleResults = people.getResults();

        while (next != null){
            People results = queryStarWarsApiRest(next, People.class);
            next=results.getNext();
            peopleResults.addAll(results.getResults());
        }
        return CompletableFuture.completedFuture(peopleResults);
    }



    public  <T> T queryStarWarsApiRest(String apiUrl, Class<T> tClass) {
        return restTemplate.getForEntity(apiUrl, tClass).getBody();
    }

    public Mono<Film> getFilm(String url) {
        return queryStarWarsApiFlux(url, Film.class);
    }

    private <T> Mono<T> queryStarWarsApiFlux(String url, Class<T> classType) {
        return webClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(classType);
    }



}
