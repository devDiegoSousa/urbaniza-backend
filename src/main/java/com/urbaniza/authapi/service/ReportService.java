package com.urbaniza.authapi.service;

import com.urbaniza.authapi.model.User;
import com.urbaniza.authapi.model.Report;

import com.urbaniza.authapi.repository.UserRepository;
import com.urbaniza.authapi.repository.ReportRepository;

import com.urbaniza.authapi.enums.ReportStatus;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ReportService {

    @Autowired
    private ReportRepository ReportRepository;

    @Autowired
    private UserRepository UserRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Transactional
    public Report createReport(@Valid Report report, Integer userId, MultipartFile photoFile) throws IOException {
        Optional<User> userOptional = UserRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User ID not found.");
        }
        report.setUser(userOptional.get());
        report.setStatus(ReportStatus.NEW); // Define o status inicial explicitamente

        if (photoFile != null && !photoFile.isEmpty()) {
            Map uploadResult = cloudinaryService.uploadImage(photoFile);
            report.setPhotoUrl(uploadResult.get("url").toString());
            report.setPhotoPublicId(uploadResult.get("public_id").toString());
        }

        return reportRepository.save(report);
    }

    public Optional<Report> findReportById(Long id) {
        return reportRepository.findById(id);
    }

    public List<Report> findReportsByUserId(Integer userId) {
        return reportRepository.findByUserId(userId);
    }

    public List<Report> findReportsByStatus(ReportStatus status) {
        return reportRepository.findByStatus(status);
    }

    public List<Report> findAllReports() {
        return reportRepository.findAll();
    }

    @Transactional
    public Report updateReportStatus(Long id, ReportStatus newStatus) {
        Optional<Report> reportOptional = reportRepository.findById(id);
        if (reportOptional.isPresent()) {
            Report report = reportOptional.get();
            report.setStatus(newStatus);
            return reportRepository.save(report);
        }
        return null;
    }

    @Transactional
    public void deleteReport(Long id) throws IOException {
        Optional<Report> reportOptional = reportRepository.findById(id);
        reportOptional.ifPresent(report -> {
            if (report.getPhotoPublicId() != null) {
                try {
                    cloudinaryService.deleteImage(report.getPhotoPublicId());
                } catch (IOException e) {
                    System.err.println("Error deleting image from Cloudinary: " + e.getMessage());
                }
            }
            reportRepository.deleteById(id);
        });
    }
}
