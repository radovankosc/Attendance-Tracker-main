package com.ctrlaltelite.backend.models;

import com.ctrlaltelite.backend.repositories.HolidayRepository;
import com.ctrlaltelite.backend.utilities.DoIntervalsOverlap;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;

@Entity
@Table(name = "Track_record")
public class TrackRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private AppUser user;

    @Enumerated(EnumType.STRING)
    private RecordType recordType;

    @Column(name = "START_TIMESTAMP")
    private Timestamp startTimestamp;

    @Column(name = "END_TIMESTAMP")
    private Timestamp endTimestamp;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "ATTACHMENT")
    private String attachmentName;

    public TrackRecord(Long id, AppUser user, RecordType recordType, Timestamp startTimestamp, Timestamp endTimestamp, String note, String attachmentName) {
        this.id = id;
        this.user = user;
        this.recordType = recordType;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.note = note;
        this.attachmentName = attachmentName;
    }

    public TrackRecord() {

    }

    public TrackRecord(TrackRecord record) {
        this.id = record.id;
        this.user = record.user;
        this.recordType = record.getRecordType();
        this.startTimestamp = record.startTimestamp;
        this.endTimestamp = record.endTimestamp;
        this.note = record.note;
        this.attachmentName = record.attachmentName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public RecordType getRecordType() {
        return recordType;
    }

    public void setRecordType(RecordType recordType) {
        this.recordType = recordType;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public Long durationInHrs(){
        long duration = this.getEndTimestamp().getTime() - this.getStartTimestamp().getTime();
        return duration/3600000;
    }


    @Override
    public String toString() {
        return "TrackRecord{" +
                "id=" + id +
                ", user=" + user +
                ", recordType=" + recordType +
                ", startTimestamp=" + startTimestamp +
                ", endTimestamp=" + endTimestamp +
                ", note='" + note + '\'' +
                ", attachment=" + attachmentName +
                '}';
    }
}




