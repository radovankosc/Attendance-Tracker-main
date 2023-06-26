package com.ctrlaltelite.backend.models.dao;

public class DaoChangeApproverRequest {
    private Long userId;
    private Long approverId;

    public DaoChangeApproverRequest(Long userId, Long approverId) {
        this.userId = userId;
        this.approverId = approverId;
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
}
