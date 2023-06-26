package com.ctrlaltelite.backend.dto;

import com.ctrlaltelite.backend.models.Attachment;
import com.ctrlaltelite.backend.models.RecordType;

import java.sql.Timestamp;

public class RecordDto {
    private Timestamp recordStart;
    private Timestamp recordEnd;
    private String recordType;
    private String note;
    private String attachment;

    public RecordDto(Timestamp recordStart, Timestamp recordEnd, String recordType, String note, String attachment) {
        this.recordStart = recordStart;
        this.recordEnd = recordEnd;
        this.recordType = recordType;
        this.note = note;
        this.attachment = attachment;
    }

    public RecordDto() {

    }

    public Timestamp getRecordStart() {
        return recordStart;
    }

    public void setRecordStart(Timestamp recordStart) {
        this.recordStart = recordStart;
    }

    public Timestamp getRecordEnd() {
        return recordEnd;
    }

    public void setRecordEnd(Timestamp recordEnd) {
        this.recordEnd = recordEnd;
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

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "RecordDto{" +
                "recordStart=" + recordStart +
                ", recordEnd=" + recordEnd +
                ", recordType=" + recordType +
                ", note='" + note + '\'' +
                ", attachment=" + attachment +
                '}';
    }
}
