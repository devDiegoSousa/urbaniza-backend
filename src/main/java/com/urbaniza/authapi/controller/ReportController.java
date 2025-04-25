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
import com.fasterxml.jackson.databind.ObjectMapper; // Importe o ObjectMapper
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseReportDTO> createReport(
            @RequestParam("report") String reportJson, // Alterado para @RequestParam
            @RequestPart(value = "photo", required = false) MultipartFile photo) {

        System.out.println("Recebida requisição POST /reports (TESTE)");
        System.out.println("reportJson: " + reportJson);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            CreateReportDTO createReportDTO = objectMapper.readValue(reportJson, CreateReportDTO.class);
            ResponseReportDTO createdReport = reportService.createReport(createReportDTO, photo);
            return new ResponseEntity<>(createdReport, HttpStatus.CREATED);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not parse report JSON: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // test form-data/multipart
    @PostMapping(value = "/test-multipart", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> testMultipart(@RequestPart("data") String data) {
        System.out.println("Dados recebidos: " + data);
        return ResponseEntity.ok("Dados recebidos com sucesso: " + data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseReportDTO> getReportById(@PathVariable Long id) {
        return reportService.findReportById(id)
                .map(report -> new ResponseEntity<>(report, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/s/{userId}")
    public ResponseEntity<List<ResponseReportDTO>> getReportByUserId(@PathVariable Long userId) {
        List<ResponseReportDTO> reports = reportService.findReportByUserId(userId);
        return reports.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ResponseReportDTO>> getAllReports() {
        List<ResponseReportDTO> reports = reportService.findAllReports();
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    // Outros endpoints (put, delete, etc.) seriam implementados de forma semelhante usando DTOs
}