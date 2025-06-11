package com.urbaniza.authapi.controller;

import com.urbaniza.authapi.dto.report.CreateReportRequestDTO;
import com.urbaniza.authapi.dto.report.ResponseReportDTO;
import com.urbaniza.authapi.dto.report.UpdateReportStatusRequestDTO;
import com.urbaniza.authapi.model.User;
import com.urbaniza.authapi.service.ReportService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // ======================
    // Citizen user endpoints
    // ======================

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CITIZEN')")
    public ResponseEntity<ResponseReportDTO> createReport(
        @RequestPart(value = "report") CreateReportRequestDTO createRequestDTO,
        @RequestPart(value = "photo", required = false) MultipartFile photo,
        @AuthenticationPrincipal User authenticatedUser) {

        try {
            ResponseReportDTO createdReport = reportService.createReport(createRequestDTO, photo, authenticatedUser);
            return new ResponseEntity<>(createdReport, HttpStatus.CREATED);
        } catch (IOException e) {
            // Tratar melhor o erro
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload photo: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Tratar melhor o erro
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    /**
     * @param authenticatedUser O usuário autenticado.
     * @return ResponseEntity com uma lista de ResponseReportDTO e status HTTP 200 (OK).
     */
    @GetMapping("/my-reports")
    @PreAuthorize("hasRole('CITIZEN')")
    public ResponseEntity<List<ResponseReportDTO>> getMyReports(
        @AuthenticationPrincipal User authenticatedUser) {

        List<ResponseReportDTO> reports = reportService.getReportsForCitizen(authenticatedUser.getEmail());
        return ResponseEntity.ok(reports);
    }

    // Department user endpoints

    /**
     * @param authenticatedUser O usuário autenticado.
     * @return ResponseEntity com uma lista de ResponseReportDTO e status HTTP 200 (OK).
     */
    @GetMapping("/department")
    @PreAuthorize("hasRole('DEPARTMENT')")
    public ResponseEntity<List<ResponseReportDTO>> getDepartmentReports(
        @AuthenticationPrincipal User authenticatedUser) {

        List<ResponseReportDTO> reports = reportService.getReportsForDepartment(authenticatedUser.getEmail());
        return ResponseEntity.ok(reports);
    }

    /**
     * @param reportId O ID da denúncia a ser atualizada.
     * @param statusUpdateDTO DTO com o ID do novo status.
     * @param authenticatedUser O usuário autenticado.
     * @return ResponseEntity com o ResponseReportDTO da denúncia atualizada e status HTTP 200 (OK).
     */
    @PutMapping("/{reportId}/status")
    @PreAuthorize("hasRole('DEPARTMENT')")
    public ResponseEntity<ResponseReportDTO> updateReportStatus(
        @PathVariable Long reportId,
        @Valid @RequestBody UpdateReportStatusRequestDTO statusUpdateDTO,
        @AuthenticationPrincipal User authenticatedUser) {

        ResponseReportDTO updatedReport = reportService.updateReportStatus(reportId, statusUpdateDTO, authenticatedUser.getEmail());
        return ResponseEntity.ok(updatedReport);
    }
}