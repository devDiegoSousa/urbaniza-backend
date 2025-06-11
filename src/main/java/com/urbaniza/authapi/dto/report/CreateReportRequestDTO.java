package com.urbaniza.authapi.dto.report;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class CreateReportRequestDTO {

    @NotBlank(message = "Title cannot be blank.")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    @NotBlank(message = "Description cannot be blank.")
    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Latitude is required")
    @DecimalMin(value = "-90.0", message = "invalid latitude .")
    @DecimalMax(value = "90.0", message = "invalid latitude .")
    private BigDecimal latitude;

    @NotNull(message = "Longitude is required")
    @DecimalMin(value = "-180.0", message = "invalid longitude .")
    @DecimalMax(value = "90.0", message = "invalid latitude .")
    private BigDecimal longitude;

    @NotNull(message = "segment is required.")
    private Long segmentId;

    @NotNull(message = "Information about anonymity is required (true or false).")
    private boolean anonymous = false;

    // Constructors
    public CreateReportRequestDTO() {}
    public CreateReportRequestDTO(String title, String description, BigDecimal latitude, BigDecimal longitude, Long segmentId, Boolean anonymous) {
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.segmentId = segmentId;
        this.anonymous = anonymous;
    }

    // Getters & Setters
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

    public BigDecimal getLatitude() {
        return latitude;
    }
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Long getSegmentId() {return segmentId;}
    public void setSegmentId(Long segmentId) {this.segmentId = segmentId;}


    public Boolean isAnonymous() {return anonymous;}
    public void setAnonymous(Boolean anonymous) {this.anonymous = anonymous;}

    // toString
    @Override
    public String toString() {
        return "CreateReportRequestDTO{" +
            "title='" + title + '\'' +
            ", description='" + (description != null ? description.substring(0, Math.min(description.length(), 30)) + "..." : "null") + '\'' +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            ", segmentId=" + segmentId +
            ", anonymous=" + anonymous +
            '}';
    }
}