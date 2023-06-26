package com.ctrlaltelite.backend.repositories;

import com.ctrlaltelite.backend.models.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
 Optional<Attachment> findByFileName(String fileName);

}
