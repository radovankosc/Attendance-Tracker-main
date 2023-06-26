package com.ctrlaltelite.backend.controllers;


import com.ctrlaltelite.backend.services.AttachmentService;
import com.ctrlaltelite.backend.utilities.getFileExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class AttachmentController {
    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImageToFileSystem(@RequestParam("attachment")MultipartFile file) throws IOException {
        String uploadImage = attachmentService.uploadImageToFileSystem(file);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
        byte[] imageData = attachmentService.downloadImageFromFileSystem(fileName);
        String fileType;
        fileType = getFileExtension.getExtensionByStringHandling(fileName).get();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/" + fileType))
                .body(imageData);
    }
}
