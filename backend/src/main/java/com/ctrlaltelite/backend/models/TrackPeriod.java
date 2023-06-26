package com.ctrlaltelite.backend.models;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Track_period")
public class TrackPeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "approver_id")
    private AppUser approver;

    @Enumerated(EnumType.STRING)
    private PeriodStatus periodStatus;

    @Column(name = "START_TIMESTAMP")
    private Timestamp startTimestamp;

    @Column(name = "END_TIMESTAMP")
    private Timestamp endTimestamp;

    public TrackPeriod
            (Long id, AppUser user, AppUser approver, PeriodStatus periodStatus, Timestamp startTimestamp, Timestamp endTimestamp) {
        this.id = id;
        this.user = user;
        this.periodStatus = periodStatus;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.approver = approver;
    }

    public TrackPeriod() {
    }

    public AppUser getApprover() {
        return approver;
    }

    public void setApprover(AppUser approver) {
        this.approver = approver;
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



    @Override
    public String toString() {
        return "TrackPeriod{" +
                "id=" + id +
                ", user=" + user +
                ", periodStatus=" + periodStatus +
                ", startTimestamp=" + startTimestamp +
                ", endTimestamp=" + endTimestamp +
                ", approver=" + approver +
                '}';
    }
}

