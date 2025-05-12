// Model: com.urbaniza.authapi.model.Report.java
package com.urbaniza.authapi.model;

import com.urbaniza.authapi.enums.ReportStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Latitude is required")
    @Column(nullable = false)
    private Double latitude;

    @NotNull(message = "Longitude is required")
    @Column(nullable = false)
    private Double longitude;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime creationDateTime;

    @Column
    private String photoUrl;

    @Column(length = 255)
    private String photoPublicId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User ID is required")
    private User user;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean anonymous;

    @PrePersist
    protected void onCreate() {
        if (this.status == null) {
            this.status = ReportStatus.NEW;
        }
    }

    public Report() {
    }

    public Report(Long id, String title, String description, Double latitude, Double longitude, LocalDateTime creationDateTime, String photoUrl, String photoPublicId, ReportStatus status, User user, boolean anonymous) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.creationDateTime = creationDateTime;
        this.photoUrl = photoUrl;
        this.photoPublicId = photoPublicId;
        this.status = status;
        this.user = user;
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

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
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
        Report report = (Report) o;
        return anonymous == report.anonymous && Objects.equals(id, report.id) && Objects.equals(title, report.title) && Objects.equals(description, report.description) && Objects.equals(latitude, report.latitude) && Objects.equals(longitude, report.longitude) && Objects.equals(creationDateTime, report.creationDateTime) && Objects.equals(photoUrl, report.photoUrl) && Objects.equals(photoPublicId, report.photoPublicId) && status == report.status && Objects.equals(user, report.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, latitude, longitude, creationDateTime, photoUrl, photoPublicId, status, user, anonymous);
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", creationDateTime=" + creationDateTime +
                ", photoUrl='" + photoUrl + '\'' +
                ", photoPublicId='" + photoPublicId + '\'' +
                ", status=" + status +
                ", user=" + (anonymous ? "Anonymous" : user.getId()) +
                ", anonymous=" + anonymous +
                '}';
    }
}