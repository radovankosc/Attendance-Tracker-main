package com.ctrlaltelite.backend.dto;

public class CreateTrackingPeriodDTO {
    private Long startTimestamp;

    private Long endTimestamp;

    public CreateTrackingPeriodDTO() {
    }

    public CreateTrackingPeriodDTO(Long startTimestamp, Long endTimestamp) {
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
    }

    public Long getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public Long getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(Long endTimestamp) {
        this.endTimestamp = endTimestamp;
    }
}
