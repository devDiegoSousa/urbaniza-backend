package com.urbaniza.authapi.repository;

import com.urbaniza.authapi.enums.ReportStatus;
import com.urbaniza.authapi.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findByUserId(Integer userId);

    Optional<Report> findById(Long id);

    List<Report> findByStatus(ReportStatus status);
}