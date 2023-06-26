package com.ctrlaltelite.backend.controllers;

import com.ctrlaltelite.backend.dto.TrackPeriodDto;
import com.ctrlaltelite.backend.models.AppUser;
import com.ctrlaltelite.backend.models.TrackPeriod;
import com.ctrlaltelite.backend.models.TrackRecord;
import com.ctrlaltelite.backend.models.dao.DaoEditTrackRecord;
import com.ctrlaltelite.backend.models.dao.DaoPeriodIdRequest;
import com.ctrlaltelite.backend.repositories.PeriodRepository;
import com.ctrlaltelite.backend.repositories.UserRepository;
import com.ctrlaltelite.backend.services.PeriodService;
import com.ctrlaltelite.backend.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class PeriodController {
    private final PeriodRepository periodRepository;
    private final PeriodService periodService;
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public PeriodController(PeriodRepository periodRepository, PeriodService periodService, UserRepository userRepository, @Qualifier("messageSource") MessageSource messageSource) {
        this.periodRepository = periodRepository;
        this.periodService = periodService;
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    @GetMapping("/list-periods")
    ResponseEntity<Map<String, List<TrackPeriodDto>>> listTrackPeriods() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<AppUser> user = userRepository.findByUsername(username);
        Map<String, List<TrackPeriodDto>> trackingPeriodsMap = new HashMap<>();

        List<TrackPeriodDto> myTrackingPeriods = periodService.getTrackPeriodsForUser(user);
        trackingPeriodsMap.put(messageSource.getMessage("period.name.listMy", null, LocaleContextHolder.getLocale()), myTrackingPeriods);

        List<TrackPeriodDto> allNotClosedPeriods = periodService.getAllNonclosedTrackPeriods();

        List<TrackPeriodDto> trackingPeriodsIAmApproverOf = allNotClosedPeriods.stream()
                .filter(period -> period.getApproverId() != null && period.getApproverId().equals(user.get().getId()))
                .collect(Collectors.toList());

        if (!trackingPeriodsIAmApproverOf.isEmpty()) {
            trackingPeriodsMap.put(messageSource.getMessage("period.name.listMyToApprove", null, LocaleContextHolder.getLocale()), trackingPeriodsIAmApproverOf);
        }

        return ResponseEntity.ok(trackingPeriodsMap);
    }

    @GetMapping("/list-periods/{id}")
    public ResponseEntity<?> listPeriodRecords(@PathVariable Long id) throws ValidationException {
        try {
            TrackPeriod checkPeriod = periodRepository.findById(id).get();
            List<TrackRecord> myRecords = periodService.listAllRecordsInPeriod(checkPeriod);
            return ResponseEntity.ok().body(myRecords);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/submit-period")
    ResponseEntity<?> submitTrackPeriod(@RequestBody DaoPeriodIdRequest daoPeriodIdRequest) throws ValidationException {
            TrackPeriod checkPeriod = periodRepository.findById(daoPeriodIdRequest.getId()).get();
            periodService.submitTrackPeriod(checkPeriod);
            return ResponseEntity.ok().body(messageSource.getMessage("period.submitSuccess", null, LocaleContextHolder.getLocale()));
    }

    @PatchMapping("/close-period")
    ResponseEntity<?> closeTrackPeriod(@RequestBody DaoPeriodIdRequest daoPeriodIdRequest) throws ValidationException {

            TrackPeriod checkPeriod = periodRepository.findById(daoPeriodIdRequest.getId()).get();
            periodService.closeTrackPeriod(checkPeriod);
            return ResponseEntity.ok().body(messageSource.getMessage("period.closeSuccess", null, LocaleContextHolder.getLocale()));
    }
}
