package com.ctrlaltelite.backend.models;

import com.ctrlaltelite.backend.repositories.AttachmentRepository;
import jakarta.persistence.*;

import java.util.Arrays;

@Entity
@Table(name = "resources")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    @Enumerated(EnumType.STRING)
    private ResourceType fileType;

    private String filePath;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private AppUser user;

    public Attachment(String fileName, ResourceType fileType, String filePath) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.filePath = filePath;
    }

    public Attachment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ResourceType getFileType() {
        return fileType;
    }

    public void setFileType(ResourceType fileType) {
        this.fileType = fileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", filePath='" + filePath + '\'' +
                ", user=" + user +
                '}';
    }
}