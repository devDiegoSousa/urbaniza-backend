package com.urbaniza.authapi.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition="TEXT")
    private String description;

    @Column(name = "latitude", nullable = false, precision = 9, scale = 6)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false, precision = 9, scale = 6)
    private BigDecimal longitude;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "photo_url", length = 255)
    private String photoUrl;

    @Column(name = "photo_public_id", length = 255)
    private String photoPublicId;

    @Column(name = "anonymous", nullable = false)
    private Boolean anonymous = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // O cidadão que criou o report

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", nullable = false)
    private StatusType status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "segment_id", nullable = false)
    private Segment segment;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<StatusHistory> statusHistories = new ArrayList<>();

    // Constructors
    public Report() {}

    public Report(String title, String description, BigDecimal latitude, BigDecimal longitude, Boolean anonymous, User user, StatusType status, Segment segment) {
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.anonymous = anonymous;
        this.user = user;
        this.status = status;
        this.segment = segment;
    }


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.anonymous == null) {
            this.anonymous = false;
        }
    }

    // Getters e Setters
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

    public String getPhotoPublicId() {return photoPublicId;}
    public void setPhotoPublicId(String photoPublicId) {this.photoPublicId = photoPublicId;}

    public Boolean getAnonymous() {return anonymous;}
    public void setAnonymous(Boolean anonymous) {this.anonymous = anonymous;}

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}

    public StatusType getStatus() {return status;}
    public void setStatus(StatusType status) {this.status = status;}

    public Segment getSegment() {return segment;}
    public void setSegment(Segment segment) {this.segment = segment;}

    public List<StatusHistory> getStatusHistories() {return statusHistories;}
    public void setStatusHistories(List<StatusHistory> statusHistories) {this.statusHistories = statusHistories;}

    // equals, hashCode, toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return Objects.equals(id, report.id);
    }

    @Override
    public int hashCode() {return Objects.hash(id);}

    @Override
    public String toString() {
        return "Report{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", anonymous=" + anonymous +
            ", userId=" + (user != null ? user.getId() : null) +
            ", statusId=" + (status != null ? status.getId() : null) +
            ", segmentId=" + (segment != null ? segment.getId() : null) +
            '}';
    }
}