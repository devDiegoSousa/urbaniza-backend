// DTO: com.urbaniza.authapi.dto.report.CreateReportDTO.java
package com.urbaniza.authapi.dto.report;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class CreateReportDTO {

    @NotBlank
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Latitude is required")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    private Double longitude;

    @NotNull(message = "User ID is required")
    private Long userId;

    private boolean anonymous;

    public CreateReportDTO() {
    }

    public CreateReportDTO(String title, String description, Double latitude, Double longitude, Long userId, boolean anonymous) {
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userId = userId;
        this.anonymous = anonymous;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateReportDTO that = (CreateReportDTO) o;
        return anonymous == that.anonymous && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, latitude, longitude, userId, anonymous);
    }

    @Override
    public String toString() {
        if (!anonymous){
            return "CreateReportDTO{" +
                    "title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    ", latitude=" + latitude +
                    ", longitude=" + longitude +
                    ", userId=" + userId +
                    ", anonymous=" + anonymous +
                    '}';

            // TODO Garantir que os dados do usuário anónimo não sejam vinculados
        } else{
            return "CreateReportDTO{" +
                    "title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    ", latitude=" + latitude +
                    ", longitude=" + longitude +
                    ", userId=" + userId +
                    ", anonymous=" + anonymous +
                    '}';
        }

    }
}