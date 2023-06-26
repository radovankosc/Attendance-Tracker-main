package com.ctrlaltelite.backend.controllers;

import com.ctrlaltelite.backend.dto.RecordDto;
import com.ctrlaltelite.backend.models.dao.DaoEditTrackRecord;
import com.ctrlaltelite.backend.repositories.RecordRepository;
import com.ctrlaltelite.backend.repositories.UserRepository;
import com.ctrlaltelite.backend.services.RecordService;
import com.ctrlaltelite.backend.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class RecordController {

    private final RecordRepository recordRepository;
    private final UserRepository userRepository;
    private final RecordService recordService;
    private final MessageSource messageSource;

    public RecordController(RecordRepository recordRepository, UserRepository userRepository, RecordService recordService, @Qualifier("messageSource") MessageSource messageSource) {
        this.recordRepository = recordRepository;
        this.userRepository = userRepository;
        this.recordService = recordService;
        this.messageSource = messageSource;
    }

    @DeleteMapping("/tracking_record/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> deleteRecord(@PathVariable Long id) {
            recordService.deleteRecord(id);
            return ResponseEntity.ok().body(messageSource.getMessage("record.deleteSuccess", null, LocaleContextHolder.getLocale()));
    }

    @PostMapping("/create-record")
    public ResponseEntity<?> createRecord (@RequestBody RecordDto record){
            recordService.createRecord(record);
            return ResponseEntity.ok().body(messageSource.getMessage("record.createSuccess", null, LocaleContextHolder.getLocale()));
    }

    @PostMapping("/edit-record/{id}")
    public ResponseEntity<Object> editRecord(@RequestBody DaoEditTrackRecord daoEditTrackRecord, @PathVariable String id) {
            recordService.editRecord(daoEditTrackRecord, id);
            return ResponseEntity.ok().body(messageSource.getMessage("record.editSuccess", null, LocaleContextHolder.getLocale()));
    }
}