// DTO: com.urbaniza.authapi.dto.report.ReportResponseDTO.java
package com.urbaniza.authapi.dto.report;

import com.urbaniza.authapi.enums.ReportStatus;

import java.time.LocalDateTime;
import java.util.Objects;

public class ResponseReportDTO {

    private Long id;
    private String title;
    private String description;
    private Double latitude;
    private Double longitude;
    private LocalDateTime creationDateTime;
    private String photoUrl;
    private String photoPublicId;
    private ReportStatus status;
    private Integer userId;
    private boolean anonymous;

    public ResponseReportDTO() {
    }

    public ResponseReportDTO(Long id, String title, String description, Double latitude, Double longitude, LocalDateTime creationDateTime, String photoUrl, String photoPublicId, ReportStatus status, Integer userId, boolean anonymous) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.creationDateTime = creationDateTime;
        this.photoUrl = photoUrl;
        this.photoPublicId = photoPublicId;
        this.status = status;
        this.userId = userId;
        this.anonymous = anonymous;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }
    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotoPublicId() {
        return photoPublicId;
    }
    public void setPhotoPublicId(String photoPublicId) {
        this.photoPublicId = photoPublicId;
    }

    public ReportStatus getStatus() {
        return status;
    }
    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public boolean isAnonymous() {
        return anonymous;
    }
    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ResponseReportDTO that = (ResponseReportDTO) object;
        return anonymous == that.anonymous && Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude) && Objects.equals(creationDateTime, that.creationDateTime) && Objects.equals(photoUrl, that.photoUrl) && Objects.equals(photoPublicId, that.photoPublicId) && status == that.status && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, latitude, longitude, creationDateTime, photoUrl, photoPublicId, status, userId, anonymous);
    }

    @Override
    public String toString() {
        return "ReportResponseDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", creationDateTime=" + creationDateTime +
                ", photoUrl='" + photoUrl + '\'' +
                ", photoPublicId='" + photoPublicId + '\'' +
                ", status=" + status +
                ", userId=" + userId +
                ", anonymous=" + anonymous +
                '}';
    }
}