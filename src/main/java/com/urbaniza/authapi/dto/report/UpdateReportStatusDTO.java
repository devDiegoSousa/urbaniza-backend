package com.urbaniza.authapi.dto.report;

import com.urbaniza.authapi.enums.ReportStatus;
import jakarta.validation.constraints.NotNull;


public class UpdateReportStatusDTO {
    @NotNull(message = "Status is required")
    private ReportStatus newStatus;

    public @NotNull(message = "Status is required") ReportStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(@NotNull(message = "Status is required") ReportStatus newStatus) {
        this.newStatus = newStatus;
    }
}