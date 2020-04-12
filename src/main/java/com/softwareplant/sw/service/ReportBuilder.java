package com.softwareplant.sw.service;

import com.softwareplant.sw.configuration.StarWarsApiConfig;
import com.softwareplant.sw.exception.customErrors.ExternalApiException;
import com.softwareplant.sw.exception.customErrors.QueryReturnedNoDataException;
import com.softwareplant.sw.models.dto.ReportDetailDto;
import com.softwareplant.sw.models.dto.ReportDto;
import com.softwareplant.sw.models.dto.SearchQueryDto;
import com.softwareplant.sw.models.swApi.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class ReportBuilder {

    private final ExternalApiService externalApi;
    private final StarWarsApiConfig config;

    public ReportBuilder(ExternalApiService externalApi, StarWarsApiConfig config) {
        this.externalApi = externalApi;
        this.config = config;
    }

    public ReportDto prepareReport(SearchQueryDto searchQueryDto, Long reportId) {
        List<Person> personResults;
        List<Planet> planetsResults;

        final People people = externalApi.queryStarWarsApiRest(config.getSearchPeople() + searchQueryDto.getQueryCriteriaCharacterPhrase(), People.class);
        final Planets planets = externalApi.queryStarWarsApiRest(config.getSearchPlanets() + searchQueryDto.getQueryCriteriaPlanetName(), Planets.class);

        if (people.getCount() < 1 || planets.getCount() < 1) throw new QueryReturnedNoDataException("Could not find data with provided query");



        CompletableFuture<List<Planet>> planetsFuture = externalApi.planetListCompletableFuture(planets);
        CompletableFuture<List<Person>> peopleFuture = externalApi.peopleListCompletableFuture(people);

        try {
            planetsResults = planetsFuture.get();
            personResults = peopleFuture.get();
        } catch (InterruptedException | ExecutionException e) { throw new ExternalApiException("Could not fetch data from external API - asynchronously."); }

        List<String> planetUrls = planetsResults.parallelStream().map(Planet::getUrl)
                .collect(Collectors.toList());

        List<Person> filteredPerson = personResults.parallelStream().filter(person -> planetUrls.contains(person.getHomeworld()))
                .collect(Collectors.toList());

        return createReports(filteredPerson, searchQueryDto, reportId, planetsResults);
    }


    private ReportDto createReports(List<Person> filteredPerson, SearchQueryDto searchQueryDto, Long reportId,
                                    List<Planet> planetsResults) {

        List<Film> films = fetchFilms(filteredPerson);
        return ReportDto.builder()
                .queryCriteriaCharacterPhrase(searchQueryDto.getQueryCriteriaCharacterPhrase())
                .queryCriteriaPlanetName(searchQueryDto.getQueryCriteriaPlanetName())
                .reportId(reportId)
                .results(
                        combineData(films, filteredPerson, planetsResults)
                )
                .build();
    }

    private List<ReportDetailDto> combineData(List<Film> films, List<Person> people, List<Planet> planetsResults) {
        return people.parallelStream()
                .map(person -> singlePersonFilmList(person, planetsResults, films))
                .collect(Collectors.toList()).stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<ReportDetailDto> singlePersonFilmList(Person person, List<Planet> planetsResults, List<Film> films) {
       return person.getFilms().parallelStream().map(film ->
                ReportDetailDto.builder()
                        .characterName(person.getName())
                        .planetName(planetsResults
                                .parallelStream()
                                .filter(planet ->
                                        planet.getUrl()
                                                .equals(person.getHomeworld()))
                                .map(Planet::getName).findAny().orElseThrow(RuntimeException::new))
                        .characterId(Long.valueOf(person.getUrl().replaceAll("[^0-9]", "")))
                        .filmId(Long.valueOf(film.replaceAll("[^0-9]", "")))
                        .planetId(Long.valueOf(person.getHomeworld().replaceAll("[^0-9]", "")))
                        .filmName(getFilmTitle(films, film))
                        .build()
        ).collect(Collectors.toList());
    }



    private List<Film> fetchFilms(List<Person> filteredPerson) {
        List<String> urls = filteredPerson.stream().flatMap(person -> person.getFilms().stream())
                .collect(Collectors.toList());

        return Flux.fromIterable(urls)
                .parallel(urls.size())
                .runOn(Schedulers.elastic())
                .flatMap(externalApi::getFilm)
                .sequential()
                .collectList()
                .block();
    }

    private String getFilmTitle(List<Film> films, String filmUrl) {
        return films.stream()
                .filter(film -> filmUrl.equals(film.getUrl()))
                .map(Film::getTitle)
                .findAny()
                .orElseThrow(RuntimeException::new);
    }
}