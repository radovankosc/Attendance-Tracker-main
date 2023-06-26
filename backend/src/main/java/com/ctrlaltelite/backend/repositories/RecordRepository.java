package com.ctrlaltelite.backend.repositories;

import com.ctrlaltelite.backend.models.TrackRecord;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface RecordRepository extends CrudRepository<TrackRecord, Long> {


    Optional<TrackRecord> findById(String recordId);

    Iterable<TrackRecord> findAllByUserId(Long userId);
}
