// Controller: com.urbaniza.authapi.controller.ReportController.java
package com.urbaniza.authapi.controller;

import com.urbaniza.authapi.dto.report.CreateReportDTO;
import com.urbaniza.authapi.dto.report.ResponseReportDTO;
import com.urbaniza.authapi.service.ReportService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseReportDTO> createReport(
            @Valid @RequestPart("report") CreateReportDTO createReportDTO,
            @RequestPart(value = "photo", required = false) MultipartFile photo) {

        System.out.println("Recebida requisição POST /reports");
        System.out.println("CreateReportDTO: " + createReportDTO);
        try {
            ResponseReportDTO createdReport = reportService.createReport(createReportDTO, photo);
            return new ResponseEntity<>(createdReport, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not upload photo.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseReportDTO> getReportById(@PathVariable Long id) {
        return reportService.findReportById(id)
                .map(report -> new ResponseEntity<>(report, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<ResponseReportDTO>> getAllReports() {
        List<ResponseReportDTO> reports = reportService.findAllReports();
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    // Outros endpoints (put, delete, etc.) seriam implementados de forma semelhante usando DTOs
}