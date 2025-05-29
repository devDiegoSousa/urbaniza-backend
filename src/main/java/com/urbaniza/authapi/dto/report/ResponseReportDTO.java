package com.urbaniza.authapi.dto.report; // Ajuste o pacote

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL) // Do not include null fields in response JSON
public class ResponseReportDTO {

    private Long id;
    private String title;
    private String description;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private LocalDateTime createdAt;
    private String photoUrl;
    private Boolean anonymous;
    private ReporterInfoDTO reporterInfo; // Null if is anonymous
    private String statusName;
    private Long statusId;
    private String segmentName;
    private Long segmentId;
    private String departmentName;
    private Long departmentId;
    private String cityName;
    private Long cityId;

    // Constructors
    public ResponseReportDTO(){}

    // Getters & Setters
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public BigDecimal getLatitude() {return latitude;}
    public void setLatitude(BigDecimal latitude) {this.latitude = latitude;}

    public BigDecimal getLongitude() {return longitude;}
    public void setLongitude(BigDecimal longitude) {this.longitude = longitude;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    public String getPhotoUrl() {return photoUrl;}
    public void setPhotoUrl(String photoUrl) {this.photoUrl = photoUrl;}

    public Boolean getAnonymous() {return anonymous;}
    public void setAnonymous(Boolean anonymous) {this.anonymous = anonymous;}

    public ReporterInfoDTO getReporterInfo() {return reporterInfo;}
    public void setReporterInfo(ReporterInfoDTO reporterInfo) {this.reporterInfo = reporterInfo;}

    public String getStatusName() {return statusName;}
    public void setStatusName(String statusName) {this.statusName = statusName;}

    public Long getStatusId() {return statusId;}
    public void setStatusId(Long statusId) {this.statusId = statusId;}

    public String getSegmentName() {return segmentName;}
    public void setSegmentName(String segmentName) {this.segmentName = segmentName;}

    public Long getSegmentId() {return segmentId;}
    public void setSegmentId(Long segmentId) {this.segmentId = segmentId;}

    public String getDepartmentName() {return departmentName;}
    public void setDepartmentName(String departmentName) {this.departmentName = departmentName;}

    public Long getDepartmentId() {return departmentId;}
    public void setDepartmentId(Long departmentId) {this.departmentId = departmentId;}

    public String getCityName() {return cityName;}
    public void setCityName(String cityName) {this.cityName = cityName;}

    public Long getCityId() {return cityId;}
    public void setCityId(Long cityId) {this.cityId = cityId;}

    // toString
    @Override
    public String toString() {
        return "ReportResponseDTO{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", anonymous=" + anonymous +
            ", statusName='" + statusName + '\'' +
            ", segmentName='" + segmentName + '\'' +
            ", departmentName='" + departmentName + '\'' +
            ", cityName='" + cityName + '\'' +
            '}';
    }
}