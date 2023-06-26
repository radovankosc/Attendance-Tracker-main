package com.ctrlaltelite.backend.models;

public enum PeriodStatus {
    REQUESTED ("requested"),
    SUBMITTED ("submitted"),
    CLOSED ("closed");

    public final String label;

    PeriodStatus(String label) {
        this.label=label;
    }
}

