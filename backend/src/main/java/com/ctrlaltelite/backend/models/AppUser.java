package com.ctrlaltelite.backend.models;

import com.ctrlaltelite.backend.utilities.GetStartOfADay;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    @Column(columnDefinition = "VARCHAR(250) COLLATE latin1_general_cs")
    private String username;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    private Set<String> roles;
    private String password;
    private String activationCode;
    private String pwResetCode;
    private Boolean active;
    private Long approverId;
    private Timestamp joiningTimestamp;
    private String workSchedule;



    public AppUser(Long id, String email, String username, Set<String> roles, String password, String activationCode, Boolean active, String pwResetCode, Long approverId, String workSchedule, Timestamp joiningTimestamp) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.roles = roles;
        this.password = password;
        this.activationCode = activationCode;
        this.active = active;
        this.pwResetCode = pwResetCode;
        this.approverId = approverId;
        this.joiningTimestamp = joiningTimestamp;
        this.workSchedule = workSchedule;
    }

    public AppUser(Long id, String email, String username, String password, String activationCode, Boolean active, String pwResetCode, Long approverId, String workSchedule) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.activationCode = activationCode;
        this.active = active;
        this.pwResetCode = pwResetCode;
        this.roles = new HashSet<>();
        this.roles.add("ROLE_USER");
        this.approverId = approverId;

        this.joiningTimestamp = new Timestamp(System.currentTimeMillis());
        this.joiningTimestamp = GetStartOfADay.getStartOfDay(this.joiningTimestamp);
        this.workSchedule = workSchedule;
    }


    public AppUser() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
    public String getPassword() {
        return password;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPwResetCode() {
        return pwResetCode;
    }

    public void setPwResetCode(String pwResetCode) {
        this.pwResetCode = pwResetCode;
    }

    public Long getApproverId() {
        return approverId;
    }

    public void setApproverId(Long approverId) {
        this.approverId = approverId;
    }

    public Timestamp getJoiningTimestamp() {
        return joiningTimestamp;
    }

    public void setJoiningTimestamp(Timestamp joiningTimestamp) {
        this.joiningTimestamp = joiningTimestamp;
    }

    public String getWorkSchedule() {
        return workSchedule;
    }

    public void setWorkSchedule(String workSchedule) {
        this.workSchedule = workSchedule;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", roles=" + roles +
                ", password='" + password + '\'' +
                ", activationCode='" + activationCode + '\'' +
                ", pwResetCode='" + pwResetCode + '\'' +
                ", active=" + active +
                ", approverId=" + approverId +
                '}';
    }
}
