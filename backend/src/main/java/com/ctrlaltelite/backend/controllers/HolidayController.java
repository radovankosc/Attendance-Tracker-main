package com.ctrlaltelite.backend.controllers;

import com.ctrlaltelite.backend.models.dao.DaoHoliday;
import com.ctrlaltelite.backend.services.HolidayService;
import com.ctrlaltelite.backend.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class HolidayController {
    private final HolidayService holidayService;
    private final MessageSource messageSource;

    public HolidayController(HolidayService holidayService, @Qualifier("messageSource") MessageSource messageSource) {
        this.holidayService = holidayService;
        this.messageSource = messageSource;
    }
    @PostMapping("/submit-holiday")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
        ResponseEntity<?> submitHoliday(@RequestBody DaoHoliday daoHoliday) throws ValidationException {
            holidayService.submitHoliday(daoHoliday);
            return ResponseEntity.ok().body(messageSource.getMessage("holiday.submitSuccess", null, LocaleContextHolder.getLocale()));
    }

    @DeleteMapping("/delete-holiday/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<String> deleteHoliday(@PathVariable Long id) throws ValidationException{
        holidayService.deleteHoliday(id);
        return ResponseEntity.ok().body(messageSource.getMessage("holiday.deleteSuccess", null, LocaleContextHolder.getLocale()));
    }}