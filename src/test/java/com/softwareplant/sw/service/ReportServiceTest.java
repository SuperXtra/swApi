package com.softwareplant.sw.service;

import com.softwareplant.sw.exception.customErrors.SingleRecordNotFoundException;
import com.softwareplant.sw.exception.customErrors.MultipleRecordsNotFoundException;
import com.softwareplant.sw.models.dto.ReportDetailDto;
import com.softwareplant.sw.models.dto.ReportDto;
import com.softwareplant.sw.models.dto.SearchQueryDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ReportServiceTest {

    @Autowired
    private ReportService reportService;
    @MockBean
    private ReportBuilder reportBuilder;

    private static final String query_criteria_character_phrase ="test_phrase";
    private static final String query_criteria_planet_name = "test_planet";
    private static final Long reportId = 1L;
    private static SearchQueryDto queryDto;


    @Before
    public void setUp() {
        queryDto = SearchQueryDto.builder()
                .queryCriteriaCharacterPhrase(query_criteria_character_phrase)
                .queryCriteriaPlanetName(query_criteria_planet_name)
                .build();

        Mockito.when(reportBuilder.prepareReport(queryDto, reportId)).thenReturn(prepareReportsDto(queryDto));
    }

    @Test
    @Transactional
    public void testIfReportIsCorrectlyPersisted() {
        reportService.createUpdateReport(1L, queryDto);
        ReportDto reportsDto = reportService.fetchRecordById(1L);

        assertThat(reportsDto.getReportId()).isEqualTo(1L);
        assertThat(reportsDto.getResults()).isNotEmpty();
        assertThat(reportsDto.getQueryCriteriaCharacterPhrase()).isEqualTo(query_criteria_character_phrase);
        assertThat(reportsDto.getQueryCriteriaPlanetName()).isEqualTo(query_criteria_planet_name);
    }

    @Test(expected = SingleRecordNotFoundException.class)
    public void testIfThrowsRecordNotFoundException() {
        reportService.removeById(2L);
    }

    @Test(expected = MultipleRecordsNotFoundException.class)
    public void testIfThrowsRecordsNotFoundException() {
        reportService.getAllreports();
    }

    @Test
    @Transactional
    public void testIfFetchesAllReports(){
        reportService.createUpdateReport(1L, queryDto);
        assertThat(reportService.getAllreports().size()).isEqualTo(1);

    }

    private ReportDto prepareReportsDto(SearchQueryDto query){
        ReportDetailDto dto = ReportDetailDto.builder()
                .characterId(1L)
                .characterName("Luke")
                .filmId(2L)
                .filmName("TestFilm")
                .planetId(3L)
                .planetName("TestPlanet")
                .build();


        return ReportDto.builder()
                .queryCriteriaCharacterPhrase(query.getQueryCriteriaCharacterPhrase())
                .queryCriteriaPlanetName(query.getQueryCriteriaPlanetName())
                .reportId(ReportServiceTest.reportId)
                .results(List.of(dto))
                .build();
    }

}
