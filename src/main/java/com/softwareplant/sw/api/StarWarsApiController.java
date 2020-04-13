package com.softwareplant.sw.api;

import com.softwareplant.sw.models.dto.*;
import com.softwareplant.sw.service.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/report")
public class StarWarsApiController implements StarWarsApi {

    private final ReportService reportService;

    @Autowired
    public StarWarsApiController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PutMapping(path = "/{reportId}")
    public ResponseEntity<Void> response(@PathVariable("reportId") Long reportId,
                                   @RequestBody SearchQueryDto query) {
        reportService.createUpdateReport(reportId, query);
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping(path = "/{report_id}")
    public ResponseEntity<Void> deleteReport(@PathVariable("report_id") Long reportId) {
        reportService.removeById(reportId);
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteAllReports() {
        reportService.removeAll();
        return ResponseEntity.status(204).build();
    }

    @GetMapping(path = "/{report_id}")
    public ResponseEntity<ReportDto> getReport(@PathVariable("report_id") Long reportId) {
        return ResponseEntity.ok(reportService.fetchRecordById(reportId));
    }

    @GetMapping()
    public ResponseEntity<List<ReportDto>> getReports() {
        return ResponseEntity.ok(reportService.getAllreports());
    }
}
