package com.ctrlaltelite.backend.dto;

import com.ctrlaltelite.backend.models.PeriodStatus;
import com.ctrlaltelite.backend.models.TrackPeriod;

import java.sql.Timestamp;

public class TrackPeriodDto {
    private Long id;
    private Long userId;
    private Long approverId;
    private PeriodStatus periodStatus;
    private Timestamp startTimestamp;
    private Timestamp endTimestamp;

    public TrackPeriodDto() {
    }

    public TrackPeriodDto(TrackPeriod trackPeriod) {
        this.id = trackPeriod.getId();
        this.periodStatus = trackPeriod.getPeriodStatus();
        this.approverId = trackPeriod.getApprover() != null ? trackPeriod.getApprover().getId() : null;
        this.userId = trackPeriod.getUser().getId();
        this.startTimestamp = trackPeriod.getStartTimestamp();
        this.endTimestamp = trackPeriod.getEndTimestamp();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getApproverId() {
        return approverId;
    }

    public void setApproverId(Long approverId) {
        this.approverId = approverId;
    }

    public PeriodStatus getPeriodStatus() {
        return periodStatus;
    }

    public void setPeriodStatus(PeriodStatus periodStatus) {
        this.periodStatus = periodStatus;
    }

    public Timestamp getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Timestamp startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public Timestamp getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(Timestamp endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

}
