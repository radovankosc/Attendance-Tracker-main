package com.ctrlaltelite.backend.services;

import com.ctrlaltelite.backend.models.Attachment;
import com.ctrlaltelite.backend.models.ResourceType;
import com.ctrlaltelite.backend.repositories.AttachmentRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class AttachmentService {
    private final MessageSource messageSource;
    private AttachmentRepository attachmentRepository;

    public AttachmentService(AttachmentRepository attachmentRepository, @Qualifier("messageSource") MessageSource messageSource) {
        this.attachmentRepository = attachmentRepository;
        this.messageSource = messageSource;
    }


    public String uploadImageToFileSystem(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        ResourceType fileType = (ResourceType.fromLabel(file.getContentType()));
        String filePath = "C:\\Users\\radov\\OneDrive\\Desktop\\GFA-Project Phase\\CtrAltElite-Java-NextJS-Attendance-Tracker\\backend\\src\\main\\resources\\uploads\\" + fileName;

        Attachment attachment = attachmentRepository.save(new Attachment(fileName,fileType,filePath));

        file.transferTo(new File(filePath));

        if (attachment != null) {
            return messageSource.getMessage("attachment.uploadSuccess", null, LocaleContextHolder.getLocale()) + filePath;
        }
        return null;
    }

    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<Attachment> attachment = attachmentRepository.findByFileName(fileName);
        String filePath = attachment.get().getFilePath();
        byte[] attachments = Files.readAllBytes(new File(filePath).toPath());
        return attachments;
    }
}
