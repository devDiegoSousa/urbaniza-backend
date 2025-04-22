// Service: com.urbaniza.authapi.service.ReportService.java
package com.urbaniza.authapi.service;

import com.urbaniza.authapi.dto.report.CreateReportDTO;
import com.urbaniza.authapi.dto.report.ResponseReportDTO;
import com.urbaniza.authapi.enums.ReportStatus;
import com.urbaniza.authapi.model.Report;
import com.urbaniza.authapi.model.User;
import com.urbaniza.authapi.repository.ReportRepository;
import com.urbaniza.authapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired(required = false) // CloudinaryService é opcional se não houver foto
    private CloudinaryService cloudinaryService;

    @Transactional
    public ResponseReportDTO createReport(CreateReportDTO createReportDTO, MultipartFile photoFile) throws IOException {
        Optional<User> userOptional = userRepository.findById(createReportDTO.getUserId());
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + createReportDTO.getUserId() + " not found.");
        }
        User user = userOptional.get();
        Report report = new Report();
        report.setTitle(createReportDTO.getTitle());
        report.setDescription(createReportDTO.getDescription());
        report.setLatitude(createReportDTO.getLatitude());
        report.setLongitude(createReportDTO.getLongitude());
        report.setUser(user);
        report.setAnonymous(createReportDTO.isAnonymous());
        report.setStatus(ReportStatus.NEW);

        if (photoFile != null && !photoFile.isEmpty() && cloudinaryService != null) {
            Map uploadResult = cloudinaryService.uploadImage(photoFile);
            report.setPhotoUrl(uploadResult.get("url").toString());
            report.setPhotoPublicId(uploadResult.get("public_id").toString());
        }

        Report savedReport = reportRepository.save(report);
        return convertToResponseDTO(savedReport);
    }

    public Optional<ResponseReportDTO> findReportById(Long id) {
        return reportRepository.findById(id)
                .map(this::convertToResponseDTO);
    }

    public List<ResponseReportDTO> findAllReports() {
        return reportRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    private ResponseReportDTO convertToResponseDTO(Report report) {
        ResponseReportDTO responseDTO = new ResponseReportDTO();
        responseDTO.setId(report.getId());
        responseDTO.setTitle(report.getTitle());
        responseDTO.setDescription(report.getDescription());
        responseDTO.setLatitude(report.getLatitude());
        responseDTO.setLongitude(report.getLongitude());
        responseDTO.setCreationDateTime(report.getCreationDateTime());
        responseDTO.setPhotoUrl(report.getPhotoUrl());
        responseDTO.setPhotoPublicId(report.getPhotoPublicId());
        responseDTO.setStatus(report.getStatus());
        responseDTO.setUserId(report.getUser().getId());
        responseDTO.setAnonymous(report.isAnonymous());
        return responseDTO;
    }

    // Outros métodos do serviço (atualização, exclusão, etc.) seriam implementados aqui
}