package com.ctrlaltelite.backend.repositories;

import com.ctrlaltelite.backend.models.Holiday;
import org.springframework.data.repository.CrudRepository;

public interface HolidayRepository extends CrudRepository<Holiday, Long> {

}
