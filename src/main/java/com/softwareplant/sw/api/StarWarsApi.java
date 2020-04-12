package com.softwareplant.sw.api;

import com.softwareplant.sw.exception.ErrorDetails;
import com.softwareplant.sw.models.dto.ReportDto;
import com.softwareplant.sw.models.dto.SearchQueryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "starWarsReport")
public interface StarWarsApi {

    @ApiOperation(value = "Creates new report with provided id, if given id exists updates existing")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = ""),
            @ApiResponse(code = 400, message = "Invalid data", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "NotFound", response = ErrorDetails.class)
    })
    @PutMapping(path = "/{reportId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<SearchQueryDto> response(@PathVariable("reportId") Long reportId,
                                                   @RequestBody SearchQueryDto query);

    @ApiOperation(value = "Deletes report with given id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successful"),
            @ApiResponse(code = 404, message = "Not found", response = ErrorDetails.class)
    })
    @DeleteMapping(path = "/{report_id}")
    ResponseEntity<Void> deleteReport(@PathVariable("report_id") Long reportId);

    @ApiOperation(value = "Deletes all reports")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
    })
    @DeleteMapping(value = "/")
    ResponseEntity<Void> deleteAllReports();


    @ApiOperation(value = "Returns record of a given id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = ReportDto.class),
            @ApiResponse(code = 404, message = "Not found", response = ErrorDetails.class)
    })
    @GetMapping(path = "/{report_id}")
    ResponseEntity<ReportDto> getReport(@PathVariable("report_id") Long reportId) ;


    @ApiOperation(value = "Returns all records")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", responseContainer = "list", response = ReportDto.class),
            @ApiResponse(code = 404, message = "Not found", response = ErrorDetails.class)
    })
    @GetMapping(value = "/")
    ResponseEntity<List<ReportDto>> getReports();
}
