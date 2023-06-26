package com.ctrlaltelite.backend.models.dao;

import java.util.Set;

public class DaoAppUser {
    private Long id;
    private String username;
    private String email;
    private Set<String> roles;

    private Long approverId;

    private String workSchedule;

    public DaoAppUser(Long id, String username, String email, Set<String> roles, String workSchedule) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.workSchedule = workSchedule;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Long getApproverId() {
        return approverId;
    }

    public void setApproverId(Long approverId) {
        this.approverId = approverId;
    }

    public String getWorkSchedule() {
        return workSchedule;
    }

    public void setWorkSchedule(String workSchedule) {
        this.workSchedule = workSchedule;
    }
}
