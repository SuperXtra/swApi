package com.softwareplant.sw.service.mapping;

import com.softwareplant.sw.models.dao.ReportDetails;
import com.softwareplant.sw.models.dao.Report;
import com.softwareplant.sw.models.dto.ReportDetailDto;
import com.softwareplant.sw.models.dto.ReportDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportMappableImpl implements ReportMappable {

    public Report dtoToEntity(ReportDto source) {
        List<ReportDetails> reportList = new ArrayList<>();
        source.getResults().forEach(result ->
                reportList.add(ReportDetails.builder()
                .characterName(result.getCharacterName())
                .filmName(result.getFilmName())
                .planetName(result.getPlanetName())
                .characterId(result.getCharacterId())
                .filmId(result.getFilmId())
                .planetId(result.getPlanetId())
                .build()));

        return Report.builder()
                .queryCriteriaCharacterPhrase(source.getQueryCriteriaCharacterPhrase())
                .queryCriteriaPlanetName(source.getQueryCriteriaPlanetName())
                .reportId(source.getReportId())
                .results(reportList)
                .build();
    }

    public ReportDto entityToDto(Report destination) {
        List<ReportDetailDto> reportDtoList = new ArrayList<>();

        destination.getResults().forEach(result ->
                reportDtoList.add(ReportDetailDto.builder()
                        .characterName(result.getCharacterName())
                        .filmName(result.getFilmName())
                        .planetName(result.getPlanetName())
                        .characterId(result.getCharacterId())
                        .filmId(result.getFilmId())
                        .planetId(result.getPlanetId())
                        .build()));

        return ReportDto.builder()
                .queryCriteriaCharacterPhrase(destination.getQueryCriteriaCharacterPhrase())
                .queryCriteriaPlanetName(destination.getQueryCriteriaPlanetName())
                .reportId(destination.getReportId())
                .results(reportDtoList)
                .build();
    }
}
