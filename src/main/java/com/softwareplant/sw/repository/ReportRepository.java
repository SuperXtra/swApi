package com.softwareplant.sw.repository;

import com.softwareplant.sw.models.dao.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
