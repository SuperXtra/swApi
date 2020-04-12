package com.softwareplant.sw.service.mapping;

import com.softwareplant.sw.models.dao.Report;
import com.softwareplant.sw.models.dto.ReportDetailDto;
import com.softwareplant.sw.models.dto.ReportDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReportMappableImplTest {

    @Autowired
    private ReportMappable mapper;


    @Test
    public void testDtoToEntityMapping(){

       //  given: The report for character name "Star", film name "Wars" and planet name "Sun"
        ReportDetailDto reportDto = ReportDetailDto.builder()
                .characterId(1L)
                .characterName("Star")
                .filmId(2L)
                .filmName("Wars")
                .planetId(4L)
                .planetName("Sun")
                .build();

        ReportDto dto = ReportDto.builder()
                .queryCriteriaCharacterPhrase("dtoCharacterPhrase")
                .queryCriteriaPlanetName("dtoCriteriaPlanetName")
                .reportId(34L)
                .results(List.of(reportDto))
                .build();

        // when: mapping from report DTO to the entity
        Report reports = mapper.dtoToEntity(dto);

        // then
        assertThat(reports.getReportId()).isEqualTo(dto.getReportId());
        assertThat(reports.getQueryCriteriaCharacterPhrase()).isEqualTo(dto.getQueryCriteriaCharacterPhrase());
        assertThat(reports.getQueryCriteriaPlanetName()).isEqualTo(dto.getQueryCriteriaPlanetName());
    }

    @Test
    public void testEntityToDtoMapping(){

        // given

        ReportDetailDto reportDto = ReportDetailDto.builder()
                .characterId(1L)
                .characterName("Star")
                .filmId(2L)
                .filmName("Wars")
                .planetId(4L)
                .planetName("Sun")
                .build();

        ReportDto dto = ReportDto.builder()
                .queryCriteriaCharacterPhrase("dtoCharacterPhrase")
                .queryCriteriaPlanetName("dtoCriteriaPlanetName")
                .reportId(34L)
                .results(List.of(reportDto))
                .build();

        // when
        Report reports = mapper.dtoToEntity(dto);

        // then

        assertThat(reports.getReportId()).isEqualTo(dto.getReportId());
        assertThat(reports.getQueryCriteriaCharacterPhrase()).isEqualTo(dto.getQueryCriteriaCharacterPhrase());
        assertThat(reports.getQueryCriteriaPlanetName()).isEqualTo(dto.getQueryCriteriaPlanetName());
    }

}
