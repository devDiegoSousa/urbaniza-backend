package com.urbaniza.authapi.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "status_history")
public class StatusHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "modified_on", nullable = false, updatable = false)
  private LocalDateTime modifiedOn;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "report_id", nullable = false)
  private Report report;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "current_status", nullable = false)
  private StatusType currentStatus;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "previous_status", nullable = false)
  private StatusType previousStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "modified_by", nullable = false)
  private User modifiedBy;

  // Constructors
  public StatusHistory() {}

  public StatusHistory(Report report, StatusType currentStatus, StatusType previousStatus, User modifiedBy) {
    this.report = report;
    this.currentStatus = currentStatus;
    this.previousStatus = previousStatus;
    this.modifiedBy = modifiedBy;
  }

  @PrePersist
  protected void onPersist() {
    this.modifiedOn = LocalDateTime.now();
  }

  // Getters & Setters
  public Long getId() {return id;}
  public void setId(Long id) {this.id = id;}

  public LocalDateTime getModifiedOn() {return modifiedOn;}
  public void setModifiedOn(LocalDateTime modifiedOn) {this.modifiedOn = modifiedOn;}

  public Report getReport() {return report;}
  public void setReport(Report report) {this.report = report;}

  public StatusType getCurrentStatus() {return currentStatus;}
  public void setCurrentStatus(StatusType currentStatus) {this.currentStatus = currentStatus;}

  public StatusType getPreviousStatus() {return previousStatus;}
  public void setPreviousStatus(StatusType previousStatus) {this.previousStatus = previousStatus;}

  public User getModifiedBy() {return modifiedBy;}
  public void setModifiedBy(User modifiedBy) {this.modifiedBy = modifiedBy;}

  // equals, hashCode, toString
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    StatusHistory that = (StatusHistory) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {return Objects.hash(id);}

  @Override
  public String toString() {
    return "StatusHistory{" +
        "id=" + id +
        ", reportId=" + (report != null ? report.getId() : null) +
        ", currentStatusId=" + (currentStatus != null ? currentStatus.getId() : null) +
        ", previousStatusId=" + (previousStatus != null ? previousStatus.getId() : null) +
        ", modifiedById=" + (modifiedBy != null ? modifiedBy.getId() : null) +
        ", modifiedOn=" + modifiedOn +
        '}';
  }
}