package com.ctrlaltelite.backend.repositories;

import com.ctrlaltelite.backend.models.AppUser;
import com.ctrlaltelite.backend.models.TrackPeriod;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.sql.Timestamp;

import java.util.List;
import java.util.Optional;

public interface PeriodRepository extends CrudRepository<TrackPeriod, Long> {
    TrackPeriod findByEndTimestampAndStartTimestamp(Timestamp endTimestamp, Timestamp startTimestamp);

    @Query(value = "SELECT * FROM track_period where user_id = ?1 order by end_timestamp desc limit 1" , nativeQuery = true)
    Optional<TrackPeriod> findLastTrackingPeriodByUser(Long userId);

    List<TrackPeriod> findByUser(Optional<AppUser> user);


}
