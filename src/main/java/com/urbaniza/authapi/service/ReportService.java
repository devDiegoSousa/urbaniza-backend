package com.urbaniza.authapi.service;

import com.urbaniza.authapi.dto.report.CreateReportRequestDTO;
import com.urbaniza.authapi.dto.report.ResponseReportDTO;
import com.urbaniza.authapi.dto.report.UpdateReportStatusRequestDTO;
import com.urbaniza.authapi.dto.report.ReporterInfoDTO;
import com.urbaniza.authapi.exception.InvalidInputException;
import com.urbaniza.authapi.exception.ResourceNotFoundException;
import com.urbaniza.authapi.exception.UnauthorizedOperationException;
import com.urbaniza.authapi.model.Department;
import com.urbaniza.authapi.model.Report;
import com.urbaniza.authapi.model.Segment;
import com.urbaniza.authapi.model.StatusHistory;
import com.urbaniza.authapi.model.StatusType;
import com.urbaniza.authapi.model.User;
import com.urbaniza.authapi.enums.UserRole;
import com.urbaniza.authapi.repository.DepartmentRepository;
import com.urbaniza.authapi.repository.ReportRepository;
import com.urbaniza.authapi.repository.SegmentRepository;
import com.urbaniza.authapi.repository.StatusHistoryRepository;
import com.urbaniza.authapi.repository.StatusTypeRepository;
import com.urbaniza.authapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final SegmentRepository segmentRepository;
    private final StatusTypeRepository statusTypeRepository;
    private final DepartmentRepository departmentRepository;
    private final StatusHistoryRepository statusHistoryRepository;
    private final CloudinaryService cloudinaryService;

    private static final String INITIAL_STATUS_NAME = "Novo";

    @Autowired
    public ReportService(ReportRepository reportRepository,
                         UserRepository userRepository,
                         SegmentRepository segmentRepository,
                         StatusTypeRepository statusTypeRepository,
                         DepartmentRepository departmentRepository,
                         StatusHistoryRepository statusHistoryRepository,
                         CloudinaryService cloudinaryService) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.segmentRepository = segmentRepository;
        this.statusTypeRepository = statusTypeRepository;
        this.departmentRepository = departmentRepository;
        this.statusHistoryRepository = statusHistoryRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Transactional
    public ResponseReportDTO createReport(CreateReportRequestDTO createRequestDTO,MultipartFile photoFile, User authenticatedUser) throws IOException {
        User citizen = userRepository.findByEmail(authenticatedUser.getEmail())
            .orElseThrow(() -> new ResourceNotFoundException("User citizen not founded with email: " + authenticatedUser.getEmail()));

        // Security check
        if (citizen.getRole() != UserRole.CITIZEN) {
            throw new UnauthorizedOperationException("Only CITIZEN users can create reports.");
        }

        // Check segment
        Segment segment = segmentRepository.findById(createRequestDTO.getSegmentId())
            .orElseThrow(() -> new ResourceNotFoundException("Segment not found with ID: " + createRequestDTO.getSegmentId()));

        StatusType initialStatus = statusTypeRepository.findByNameIgnoreCase(INITIAL_STATUS_NAME)
            .orElseThrow(() -> new IllegalStateException("Initial status'" + INITIAL_STATUS_NAME + "' not configured in the system."));

        Report report = new Report();
        report.setTitle(createRequestDTO.getTitle());
        report.setDescription(createRequestDTO.getDescription());
        report.setLatitude(createRequestDTO.getLatitude());
        report.setLongitude(createRequestDTO.getLongitude());
        report.setAnonymous(createRequestDTO.isAnonymous() != null ? createRequestDTO.isAnonymous() : false);
        report.setUser(citizen);
        report.setSegment(segment);
        report.setStatus(initialStatus);

        if (photoFile != null && !photoFile.isEmpty()) {
            if (cloudinaryService == null) {
                throw new ResourceNotFoundException("Cloudinary service is not configured but a photo was provided.");
            }
            Map uploadResult = cloudinaryService.uploadImage(photoFile, "reports");
            report.setPhotoUrl(uploadResult.get("url").toString());
            report.setPhotoPublicId(uploadResult.get("public_id").toString());
        }

        Report savedReport = reportRepository.save(report);
        createStatusHistoryEntry(savedReport, initialStatus, initialStatus, citizen);
        return convertToResponseReportDTO(savedReport);
    }

    @Transactional(readOnly = true)
    public List<ResponseReportDTO> getReportsForCitizen(String authenticatedUserEmail) {
        User citizen = userRepository.findByEmail(authenticatedUserEmail)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário cidadão não encontrado com o email: " + authenticatedUserEmail));


       // confirms if the user role is citizen
        if (citizen.getRole() != UserRole.CITIZEN) {
            throw new UnauthorizedOperationException("Operation not allowed for this user type.");
        }

        // List of all reports for a user
        List<Report> reports = reportRepository.findReportsByUser(citizen);
        return reports.stream()
            .map(this::convertToResponseReportDTO)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ResponseReportDTO> getReportsForDepartment(String authenticatedUserEmail) {
        User departmentUser = userRepository.findByEmail(authenticatedUserEmail)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário de departamento não encontrado com o email: " + authenticatedUserEmail));

        if (departmentUser.getRole() != UserRole.DEPARTMENT) {
            throw new UnauthorizedOperationException("Apenas usuários DEPARTMENT podem visualizar denúncias por departamento.");
        }
        // confirms if the user email matches a department's email
        Department department = departmentRepository.findByEmail(departmentUser.getEmail())
            .orElseThrow(() -> new ResourceNotFoundException("Department not found for user: " + authenticatedUserEmail + ". Check if the user's email matches a department's email."));

        // List of all department reports
        List<Report> reports = reportRepository.findByDepartment(department);
        return reports.stream()
            .map(this::convertToResponseReportDTO)
            .collect(Collectors.toList());
    }

    @Transactional
    public ResponseReportDTO updateReportStatus(Long reportId, UpdateReportStatusRequestDTO statusUpdateDTO, String authenticatedUserEmail) {
        User departmentUser = userRepository.findByEmail(authenticatedUserEmail)
            .orElseThrow(() -> new ResourceNotFoundException("Department user not found with email: " + authenticatedUserEmail));

        if (departmentUser.getRole() != UserRole.DEPARTMENT) {
            throw new UnauthorizedOperationException("Only the DEPARTMENT user can update the status of reports.");
        }

        Department department = departmentRepository.findById(departmentUser.getDepartmentId())
            .orElseThrow(() -> new ResourceNotFoundException("Department not found for user: " + departmentUser));

        Report report = reportRepository.findByIdAndDepartment(reportId, department)
            .orElseThrow(() -> new ResourceNotFoundException("Report with Id " + reportId + " not found or does not belong to this department."));

        StatusType oldStatus = report.getStatus();
        StatusType newStatus = statusTypeRepository.findById(statusUpdateDTO.getNewStatusId())
            .orElseThrow(() -> new ResourceNotFoundException("New status type not found with ID: " + statusUpdateDTO.getNewStatusId()));

        if (oldStatus.getId().equals(newStatus.getId())) {
            throw new InvalidInputException("The report already has the status '" + newStatus.getName() + "'. No changes made.");
        }

        report.setStatus(newStatus);
        Report updatedReport = reportRepository.save(report);
        createStatusHistoryEntry(updatedReport, newStatus, oldStatus, departmentUser);
        return convertToResponseReportDTO(updatedReport);
    }

    private void createStatusHistoryEntry(Report report, StatusType currentStatus, StatusType previousStatus, User modifiedBy) {
        StatusHistory historyEntry = new StatusHistory();
        historyEntry.setReport(report);
        historyEntry.setCurrentStatus(currentStatus);
        historyEntry.setPreviousStatus(previousStatus);
        historyEntry.setModifiedBy(modifiedBy);
        statusHistoryRepository.save(historyEntry);
    }

    private ResponseReportDTO convertToResponseReportDTO(Report report) {
        ResponseReportDTO dto = new ResponseReportDTO();
        dto.setId(report.getId());
        dto.setTitle(report.getTitle());
        dto.setDescription(report.getDescription());
        dto.setLatitude(report.getLatitude());
        dto.setLongitude(report.getLongitude());
        dto.setCreatedAt(report.getCreatedAt());
        dto.setPhotoUrl(report.getPhotoUrl());
        dto.setAnonymous(report.getAnonymous());

        if (!report.getAnonymous() && report.getUser() != null) {
            User reporter = report.getUser();
            ReporterInfoDTO reporterInfo = new ReporterInfoDTO(
                reporter.getId(),
                reporter.getFirstName(),
                reporter.getLastName(),
                reporter.getEmail()
            );
            dto.setReporterInfo(reporterInfo);
        }

        if (report.getStatus() != null) {
            dto.setStatusId(report.getStatus().getId());
            dto.setStatusName(report.getStatus().getName());
        }

        if (report.getSegment() != null) {
            Segment segment = report.getSegment();
            dto.setSegmentId(segment.getId());
            dto.setSegmentName(segment.getName());
            if (segment.getDepartment() != null) {
                Department department = segment.getDepartment();
                dto.setDepartmentId(department.getId());
                dto.setDepartmentName(department.getName());
                if (department.getCity() != null) {
                    dto.setCityId(department.getCity().getId());
                    dto.setCityName(department.getCity().getName());
                }
            }
        }
        return dto;
    }
}