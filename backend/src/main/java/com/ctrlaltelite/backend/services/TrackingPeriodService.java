package com.ctrlaltelite.backend.services;

import com.ctrlaltelite.backend.dto.CreateTrackingPeriodDTO;
import com.ctrlaltelite.backend.models.AppUser;
import com.ctrlaltelite.backend.models.PeriodStatus;
import com.ctrlaltelite.backend.models.TrackPeriod;
import com.ctrlaltelite.backend.repositories.PeriodRepository;
import com.ctrlaltelite.backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class TrackingPeriodService {
    private final PeriodRepository periodRepository;

    private final UserRepository userRepository;

    public TrackingPeriodService(PeriodRepository periodRepository, UserRepository userRepository) {
        this.periodRepository = periodRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createTrackingPeriod (CreateTrackingPeriodDTO trackingPeriodDTO) {
        Iterable<AppUser> allByActive = userRepository.findAllByActive(true);
        for (AppUser appUser : allByActive) {
            Optional<TrackPeriod> lastTrackingPeriodByUser = periodRepository.findLastTrackingPeriodByUser(appUser.getId());
            if (lastTrackingPeriodByUser.isEmpty()) {
                continue;
            }
            TrackPeriod trackPeriod = lastTrackingPeriodByUser.get();
            Timestamp timestamp = new Timestamp(trackingPeriodDTO.getStartTimestamp());
            if (trackPeriod.getEndTimestamp().before(timestamp)) {
                TrackPeriod newTrackPeriod = new TrackPeriod(null, appUser, null, PeriodStatus.REQUESTED, timestamp, new Timestamp(trackingPeriodDTO.getEndTimestamp()));
                periodRepository.save(newTrackPeriod);
            }
        }
    }
}
