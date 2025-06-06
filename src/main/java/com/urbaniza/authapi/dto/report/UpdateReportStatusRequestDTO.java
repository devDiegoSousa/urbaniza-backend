package com.urbaniza.authapi.dto.report;

import jakarta.validation.constraints.NotNull;

public class UpdateReportStatusRequestDTO {

    @NotNull(message = "The new status ID cannot be null.")
    private Long newStatusId;

    // Constructors
    public UpdateReportStatusRequestDTO() {}
    public UpdateReportStatusRequestDTO(Long newStatusId) {
        this.newStatusId = newStatusId;
    }

    // Getter & Setter
    public Long getNewStatusId() {return newStatusId;}
    public void setNewStatusId(Long newStatusId) {this.newStatusId = newStatusId;}

    // toString
    @Override
    public String toString() {
        return "ReportStatusUpdateRequestDTO{" +
            "newStatusId=" + newStatusId +
            '}';
    }
}