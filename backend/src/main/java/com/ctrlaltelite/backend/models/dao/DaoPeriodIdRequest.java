package com.ctrlaltelite.backend.models.dao;

public class DaoPeriodIdRequest {
    private Long id;

    public DaoPeriodIdRequest(Long id) {
        this.id = id;
    }

    public DaoPeriodIdRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}