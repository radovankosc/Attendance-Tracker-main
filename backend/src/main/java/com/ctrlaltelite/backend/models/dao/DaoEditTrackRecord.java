package com.ctrlaltelite.backend.models.dao;

public class DaoEditTrackRecord {
    private Long startTimestamp;
    private Long endTimestamp;
    private String recordType;
    private String note;

    public DaoEditTrackRecord(Long startTimestamp, Long endTimestamp, String recordType, String note) {
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.recordType = recordType;
        this.note = note;
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

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
