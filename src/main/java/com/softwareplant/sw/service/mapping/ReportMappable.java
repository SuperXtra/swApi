package com.softwareplant.sw.service.mapping;

import com.softwareplant.sw.models.dao.Report;
import com.softwareplant.sw.models.dto.ReportDto;

public interface ReportMappable {
    Report dtoToEntity(ReportDto source);
    ReportDto entityToDto(Report destination);
}
