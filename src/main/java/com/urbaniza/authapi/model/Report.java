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
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
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
    private boolean anonymous;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_report_user"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_id", nullable = false, foreignKey = @ForeignKey(name = "fk_report_status"))
    private StatusType status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "segment_id", nullable = false, foreignKey = @ForeignKey(name = "fk_report_segment"))
    private Segment segment;

    @OneToMany(
        mappedBy = "report",
        cascade = CascadeType.ALL, // Se a denúncia some, seu histórico some.
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private List<StatusHistory> statusHistories = new ArrayList<>();


    protected Report() {}

    public Report(String title, String description, BigDecimal latitude, BigDecimal longitude,
                  boolean anonymous, User user, StatusType status, Segment segment) {

        Objects.requireNonNull(title, "O título não pode ser nulo.");
        Objects.requireNonNull(description, "A descrição não pode ser nula.");
        Objects.requireNonNull(latitude, "A latitude não pode ser nula.");
        Objects.requireNonNull(longitude, "A longitude não pode ser nula.");
        Objects.requireNonNull(user, "O usuário não pode ser nulo.");
        Objects.requireNonNull(status, "O status não pode ser nulo.");
        Objects.requireNonNull(segment, "O segmento não pode ser nulo.");

        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.anonymous = anonymous;
        this.user = user;
        this.status = status;
        this.segment = segment;
        // Data de criação definida pelo @PrePersist
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // --- Getters & Setters---

    public Long getId() {return id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {
        Objects.requireNonNull(title, "O título não pode ser nulo.");
        this.title = title;
    }

    public String getDescription() {return description;}
    public void setDescription(String description) {
        Objects.requireNonNull(description, "A descrição não pode ser nula.");
        this.description = description;
    }

    public BigDecimal getLatitude() {return latitude;}
    public void setLatitude(BigDecimal latitude) {
        Objects.requireNonNull(latitude, "A latitude não pode ser nula.");
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {return longitude;}
    public void setLongitude(BigDecimal longitude) {
        Objects.requireNonNull(longitude, "A longitude não pode ser nula.");
        this.longitude = longitude;
    }

    public LocalDateTime getCreatedAt() {return createdAt;}

    public String getPhotoUrl() {return photoUrl;}
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotoPublicId() {return photoPublicId;}
    public void setPhotoPublicId(String photoPublicId) {
        this.photoPublicId = photoPublicId;
    }

    public boolean isAnonymous() {return anonymous;}
    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public User getUser() {return user;}

    public StatusType getStatus() {return status;}
    public void setStatus(StatusType status) {
        Objects.requireNonNull(status, "O status não pode ser nulo.");
        this.status = status;
    }

    public Segment getSegment() {return segment;}

    public void setSegment(Segment segment) {
        Objects.requireNonNull(segment, "O status não pode ser nulo.");
        this.segment = segment;
    }

    public List<StatusHistory> getStatusHistories() {return statusHistories;}

    // --- Helper methods---

    public void addStatusHistory(StatusHistory historyEntry) {
        if (this.statusHistories == null) {
            this.statusHistories = new ArrayList<>();
        }
        this.statusHistories.add(historyEntry);
        historyEntry.setReport(this); // Assume que StatusHistory tem setReport
    }

    public void removeStatusHistory(StatusHistory historyEntry) {
        if (this.statusHistories != null) {
            this.statusHistories.remove(historyEntry);
            historyEntry.setReport(null); // Assume que StatusHistory tem setReport
        }
    }

    // --- equals(), hashCode() e toString() ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return id != null && id.equals(report.id);
    }

    @Override
    public String toString() {
        return "Report{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", createdAt=" + createdAt +
            ", statusId=" + (status != null ? status.getId() : "null") +
            ", userId=" + (user != null ? user.getId() : "null") +
            '}';
    }
}