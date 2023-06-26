package com.ctrlaltelite.backend.services;

import com.ctrlaltelite.backend.dto.RecordDto;
import com.ctrlaltelite.backend.exceptions.ValidationException;
import com.ctrlaltelite.backend.models.AppUser;
import com.ctrlaltelite.backend.models.PeriodStatus;
import com.ctrlaltelite.backend.models.RecordType;
import com.ctrlaltelite.backend.models.TrackRecord;
import com.ctrlaltelite.backend.models.dao.DaoEditTrackRecord;
import com.ctrlaltelite.backend.repositories.PeriodRepository;
import com.ctrlaltelite.backend.repositories.RecordRepository;
import com.ctrlaltelite.backend.repositories.UserRepository;
import com.ctrlaltelite.backend.utilities.DoIntervalsOverlap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

@Service

public class RecordService {
    private final RecordRepository recordRepository;
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    private PeriodRepository periodRepository;

    public RecordService(RecordRepository recordRepository, UserRepository userRepository, @Qualifier("messageSource") MessageSource messageSource) {
        this.recordRepository = recordRepository;
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    public void deleteRecord(Long id) throws ValidationException {
        var record = recordRepository.findById(id).get();
        if (record == null) {
        }
        var relatedPeriod = periodRepository.findByEndTimestampAndStartTimestamp(record.getEndTimestamp(), record.getStartTimestamp());
        if (PeriodStatus.SUBMITTED.equals(relatedPeriod.getPeriodStatus()) || PeriodStatus.CLOSED.equals((relatedPeriod.getPeriodStatus()))) {
            throw new ValidationException(messageSource.getMessage("record.delete.err", null, LocaleContextHolder.getLocale()));
        }
        recordRepository.deleteById(id);
    }

    public void createRecord(RecordDto record) throws ValidationException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<AppUser> user = userRepository.findByUsername(username);

        if (record.getRecordType() == null){
            throw new ValidationException(messageSource.getMessage("record.create.err.provideRequestType", null, LocaleContextHolder.getLocale()));
        }

        if(!isInSameDay(record.getRecordStart(),record.getRecordEnd()))
            throw new ValidationException(messageSource.getMessage("record.create.err.provideSameDay", null, LocaleContextHolder.getLocale()));

        Iterable<TrackRecord> allUserRecords = recordRepository.findAllByUserId(user.get().getId());
        for (TrackRecord myRecord : allUserRecords) {
            boolean doIntervalsOverlap = DoIntervalsOverlap.doIntervalsOverlap(myRecord.getStartTimestamp(), myRecord.getEndTimestamp(), record.getRecordStart(), record.getRecordEnd());
            if(doIntervalsOverlap) {
                throw new ValidationException(messageSource.getMessage("record.create.err.overlapNot", null, LocaleContextHolder.getLocale()));
            }
        }

        TrackRecord newRecord = new TrackRecord(null, user.get(), RecordType.fromLabel(record.getRecordType()), record.getRecordStart(), record.getRecordEnd(), record.getNote(), record.getAttachment());
        recordRepository.save(newRecord);
    }

    public void editRecord(DaoEditTrackRecord daoEditTrackRecord, String id) throws ValidationException {
        //find record from request in DTB
        Optional<TrackRecord> optEditedRecord = recordRepository.findById(id);
        TrackRecord editedRecord = optEditedRecord.get();

        //check if startTimestamp and endTimestamp are within the same day
        Long startTimestamp = daoEditTrackRecord.getStartTimestamp();
        Long endTimestamp = daoEditTrackRecord.getEndTimestamp();
        if(startTimestamp != null && endTimestamp != null)
            if(!startInSameDay(startTimestamp,endTimestamp))
                 throw new ValidationException(messageSource.getMessage("record.edit.err.sameDay", null, LocaleContextHolder.getLocale()));

        //when edit just start or end, check if it is within the same day as previous record
        if(startTimestamp == null && endTimestamp != null)
            if(!startInSameDay((editedRecord.getStartTimestamp().getTime()/1000),endTimestamp))
                throw new ValidationException(messageSource.getMessage("record.edit.err.overlap", null, LocaleContextHolder.getLocale()));

        if(endTimestamp == null && startTimestamp != null)
            if(!startInSameDay((editedRecord.getEndTimestamp().getTime()/1000),startTimestamp))
                throw new ValidationException(messageSource.getMessage("record.edit.err.noAccess", null, LocaleContextHolder.getLocale()));

        //give me the currently logged user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<AppUser> user = userRepository.findByUsername(username);

        //check if the record does not overlap with other user's records
        Iterable<TrackRecord> allUserRecords = recordRepository.findAllByUserId(user.get().getId());
        System.out.println("*****" + allUserRecords);
        if(startTimestamp != null && endTimestamp != null)
            for (TrackRecord record : allUserRecords)

                if((record.getStartTimestamp().before(new Timestamp(endTimestamp*1000)) || record.getStartTimestamp().equals(new Timestamp(endTimestamp*1000)))
                        && (record.getEndTimestamp().after(new Timestamp(startTimestamp*1000)) || record.getEndTimestamp().equals(startTimestamp*1000)))
                    throw new ValidationException("This request overlaps previous record!");

        //logged user can edit only own records
        if(user.get().getId() != editedRecord.getUser().getId())
            throw new ValidationException("You can not edit this record!");
        ifNotNullSetChanges(editedRecord,startTimestamp,endTimestamp,daoEditTrackRecord.getRecordType(), daoEditTrackRecord.getNote());
        recordRepository.save(editedRecord);
    }

    public boolean isInSameDay(Timestamp start, Timestamp end) {
        LocalDate startDate = start.toLocalDateTime().toLocalDate();
        LocalDate endDate = end.toLocalDateTime().toLocalDate();

        System.out.println(startDate);
        System.out.println(endDate);

        return startDate.getYear() == endDate.getYear()
                && startDate.getMonth() == endDate.getMonth()
                && startDate.getDayOfMonth() == endDate.getDayOfMonth();
    }

    public boolean startInSameDay(Long start, Long end) {
        int nOfSecInDay = 86400;
        return ((start / nOfSecInDay) == (end / nOfSecInDay));
    }

    public TrackRecord ifNotNullSetChanges(TrackRecord editedRecord, Long start, Long end, String recordType, String note){

        if(start != null)
            editedRecord.setStartTimestamp(new Timestamp(start * 1000));

        if(end != null)
            editedRecord.setEndTimestamp(new Timestamp(end * 1000));

        if(recordType != null)
            editedRecord.setRecordType(RecordType.fromLabel(recordType));

        if(note != null)
            editedRecord.setNote(note);

        return editedRecord;
    }
}
