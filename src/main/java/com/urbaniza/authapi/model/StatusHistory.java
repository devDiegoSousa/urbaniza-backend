package com.urbaniza.authapi.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "status_history")
public class StatusHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "modified_on", nullable = false, updatable = false)
  private LocalDateTime modifiedOn;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "report_id", nullable = false, foreignKey = @ForeignKey(name = "fk_history_report"))
  private Report report;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "current_status", nullable = false, foreignKey = @ForeignKey(name = "fk_history_current_status"))
  private StatusType currentStatus;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "previous_status", nullable = false, foreignKey = @ForeignKey(name = "fk_history_previous_status"))
  private StatusType previousStatus;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "modified_by", nullable = false, foreignKey = @ForeignKey(name = "fk_history_user"))
  private User modifiedBy;

  protected StatusHistory() {}
  public StatusHistory(Report report, StatusType currentStatus, StatusType previousStatus, User modifiedBy) {
    Objects.requireNonNull(report, "A denúncia (report) não pode ser nula.");
    Objects.requireNonNull(currentStatus, "O status atual não pode ser nulo.");
    Objects.requireNonNull(previousStatus, "O status anterior não pode ser nulo.");
    Objects.requireNonNull(modifiedBy, "O usuário modificador não pode ser nulo.");

    this.report = report;
    this.currentStatus = currentStatus;
    this.previousStatus = previousStatus;
    this.modifiedBy = modifiedBy;
    // A data/hora definida pelo @PrePersist.
  }

  @PrePersist
  protected void onModify() {
    this.modifiedOn = LocalDateTime.now();
  }

  // --- Getters & Setters---
  public Long getId() {return id;}

  public LocalDateTime getModifiedOn() {return modifiedOn;}

  public Report getReport() {return report;}
  public void setReport(Report report) {
    Objects.requireNonNull(report, "O report não pode ser nulo.");
    this.report = report;
  }

  public StatusType getCurrentStatus() {return currentStatus;}

  public StatusType getPreviousStatus() {return previousStatus;}

  public User getModifiedBy() {return modifiedBy;}

// --- equals(), hashCode() e toString() ---

  @Override
  public String toString() {
    return "StatusHistory{" +
        "id=" + id +
        ", modifiedOn=" + modifiedOn +
        ", reportId=" + (report != null ? report.getId() : "null") +
        ", currentStatusId=" + (currentStatus != null ? currentStatus.getId() : "null") +
        ", previousStatusId=" + (previousStatus != null ? previousStatus.getId() : "null") +
        ", modifiedById=" + (modifiedBy != null ? modifiedBy.getId() : "null") +
        '}';
  }
}