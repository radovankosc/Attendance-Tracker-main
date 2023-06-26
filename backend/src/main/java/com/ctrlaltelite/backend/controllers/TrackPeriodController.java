package com.ctrlaltelite.backend.controllers;

import com.ctrlaltelite.backend.dto.CreateTrackingPeriodDTO;
import com.ctrlaltelite.backend.services.TrackingPeriodService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrackPeriodController {
    private final TrackingPeriodService service;

    public TrackPeriodController(TrackingPeriodService service) {
        this.service = service;
    }

    @PostMapping("/request/tracked/period")
    public void createTrackingPeriod(@RequestBody CreateTrackingPeriodDTO trackingPeriodDTO){
        service.createTrackingPeriod(trackingPeriodDTO);
    }
}
