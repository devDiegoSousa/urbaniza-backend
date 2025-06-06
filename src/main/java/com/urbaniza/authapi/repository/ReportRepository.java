package com.urbaniza.authapi.repository;

import com.urbaniza.authapi.enums.ReportStatus;
import com.urbaniza.authapi.model.Department;
import com.urbaniza.authapi.model.Report;
import com.urbaniza.authapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    // Searches for all reports created by a specific user.
    @Query("SELECT r FROM Report r WHERE r.user = :user ORDER BY r.createdAt DESC")
    List<Report> findReportsByUser(User user);

    // Searches for all reports associated with a specific department.
    // This is done through the Report -> Segment -> Department relationship.
    @Query("SELECT r FROM Report r WHERE r.segment.department = :department ORDER BY r.createdAt DESC")
    List<Report> findByDepartment(@Param("department") Department department);

    // Search for a specific report by its ID and the department it is associated with.
    // Useful to ensure that a department user can only modify reports from their department.
    @Query("SELECT r FROM Report r WHERE r.id = :reportId AND r.segment.department = :department")
    Optional<Report> findByIdAndDepartment(@Param("reportId") Long reportId, @Param("department") Department department);

    List<Report> findByStatus(ReportStatus status);
}