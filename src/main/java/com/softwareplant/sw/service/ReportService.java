package com.softwareplant.sw.service;

import java.util.*;
import java.util.stream.Collectors;

import com.softwareplant.sw.service.mapping.ReportMappableImpl;
import org.springframework.stereotype.Service;
import com.softwareplant.sw.exception.customErrors.*;
import com.softwareplant.sw.models.dao.*;
import com.softwareplant.sw.models.dto.*;
import com.softwareplant.sw.repository.ReportRepository;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final ReportBuilder service;
    private final ReportMappableImpl mapperImplementation;

    public ReportService(ReportRepository reportRepository, ReportBuilder service, ReportMappableImpl mapperImplementation) {
        this.reportRepository = reportRepository;
        this.service = service;
        this.mapperImplementation = mapperImplementation;
    }

    public void createUpdateReport(Long id, SearchQueryDto query) {
        ReportDto reportsDto = service.prepareReport(query, id);
        Report reports = mapperImplementation.dtoToEntity(reportsDto);

        reportRepository.save(reports);
    }

    public ReportDto fetchRecordById(Long id) {
        Report reports = reportRepository.findById(id).orElseThrow(() -> new SingleRecordNotFoundException("Could not find report with id: " + id));
        return mapperImplementation.entityToDto(reports);
    }

    public List<ReportDto> getAllreports() {
        List<ReportDto> reportsDtos = reportRepository.findAll().stream().map(mapperImplementation::entityToDto).collect(Collectors.toList());
        if (reportsDtos.size()==0) throw new MultipleRecordsNotFoundException("There are no records in the database.");
        return reportsDtos;
    }

    public void removeAll(){
        reportRepository.deleteAll();
    }

    public void removeById(Long id) {
        reportRepository.findById(id).orElseThrow(() -> new SingleRecordNotFoundException("Could not delete report with id: " + id + ", it does not exists."));
        reportRepository.deleteById(id);
    }
}
