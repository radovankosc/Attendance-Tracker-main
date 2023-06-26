package com.ctrlaltelite.backend.services;

import com.ctrlaltelite.backend.dto.TrackPeriodDto;
import com.ctrlaltelite.backend.exceptions.ValidationException;
import com.ctrlaltelite.backend.models.AppUser;
import com.ctrlaltelite.backend.models.PeriodStatus;
import com.ctrlaltelite.backend.models.TrackPeriod;
import com.ctrlaltelite.backend.models.TrackRecord;
import com.ctrlaltelite.backend.repositories.HolidayRepository;
import com.ctrlaltelite.backend.repositories.PeriodRepository;
import com.ctrlaltelite.backend.repositories.RecordRepository;
import com.ctrlaltelite.backend.repositories.UserRepository;
import com.ctrlaltelite.backend.utilities.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.ctrlaltelite.backend.models.PeriodStatus.CLOSED;
import static com.ctrlaltelite.backend.models.PeriodStatus.SUBMITTED;

@Service
public class PeriodService {
private static PeriodRepository periodRepository;
private final RecordRepository recordRepository;
private final HolidayRepository holidayRepository;
private final UserRepository userRepository;
    private final MessageSource messageSource;

    public PeriodService(PeriodRepository periodRepository, RecordRepository recordRepository, HolidayRepository holidayRepository, UserRepository userRepository, @Qualifier("messageSource") MessageSource messageSource) {
        this.periodRepository = periodRepository;
        this.recordRepository = recordRepository;
        this.holidayRepository = holidayRepository;
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    public static List<TrackPeriodDto> getTrackPeriodsForUser(Optional<AppUser> user) {
        List<TrackPeriod> trackPeriods = periodRepository.findByUser(user);
        List<TrackPeriodDto> dtos = new ArrayList<>();
        for (TrackPeriod trackPeriod : trackPeriods) {
            TrackPeriodDto dto = new TrackPeriodDto();
            dto.setId(trackPeriod.getId());
            dto.setUserId(trackPeriod.getUser().getId());
            dto.setApproverId(trackPeriod.getApprover().getId());
            dto.setPeriodStatus(trackPeriod.getPeriodStatus());
            dto.setStartTimestamp(trackPeriod.getStartTimestamp());
            dto.setEndTimestamp(trackPeriod.getEndTimestamp());
            dtos.add(dto);
        }
        return dtos;
    }

    public List<TrackPeriodDto> getAllNonclosedTrackPeriods() {
        List<TrackPeriod> trackPeriods = (List<TrackPeriod>) periodRepository.findAll();
        List<TrackPeriodDto> trackPeriodDtos = trackPeriods.stream()
                .filter(trackPeriod -> {
                    PeriodStatus status = trackPeriod.getPeriodStatus();
                    return status != PeriodStatus.CLOSED;
                })
                .map(trackPeriod -> {
                    TrackPeriodDto dto = new TrackPeriodDto(trackPeriod);
                    return dto;
                })
                .collect(Collectors.toList());
        System.out.println(trackPeriodDtos);
        return trackPeriodDtos;
    }

    public List<TrackRecord> listAllRecordsInPeriodOfUser(TrackPeriod trackPeriod, AppUser user) throws ValidationException {
        Iterable<TrackRecord> allUserRecords = recordRepository.findAllByUserId(user.getId());
        Stream<TrackRecord> stream = StreamSupport.stream(allUserRecords.spliterator(), false);

        if(trackPeriod.getUser().getId() != user.getId()) {
            throw new ValidationException(messageSource.getMessage("period.err.noAccess", null, LocaleContextHolder.getLocale()));
        }

        List<TrackRecord> userRecords = stream
                .filter(myRecord -> myRecord.getUser().getId().equals(user.getId())
                        && myRecord.getStartTimestamp().getTime() >= trackPeriod.getStartTimestamp().getTime()
                        && myRecord.getEndTimestamp().getTime() <= trackPeriod.getEndTimestamp().getTime())
                .collect(Collectors.toList());

        return userRecords;
    }

    public List<TrackRecord> listAllRecordsInPeriod(TrackPeriod trackPeriod) throws ValidationException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<AppUser> user = userRepository.findByUsername(username);
        List<TrackRecord> recordsInPeriod = new ArrayList<>();

        if(trackPeriod.getApprover().getId() == user.get().getId()){
            recordsInPeriod = listAllRecordsInPeriodOfUser(trackPeriod, trackPeriod.getUser());
        } else {
            recordsInPeriod = listAllRecordsInPeriodOfUser(trackPeriod, user.get());
        }

        return recordsInPeriod;
    }

    public void submitTrackPeriod(TrackPeriod trackPeriod) throws ValidationException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<AppUser> user = userRepository.findByUsername(username);
        String userWorkSchedule = user.get().getWorkSchedule();
        Iterable<TrackRecord> allUserRecords = recordRepository.findAllByUserId(user.get().getId());

        String workSchedule = user.get().getWorkSchedule();

        if(userWorkSchedule.equals("8888800")) {
            for (Long startDay = Math.max(trackPeriod.getStartTimestamp().getTime(),user.get().getJoiningTimestamp().getTime()); startDay < trackPeriod.getEndTimestamp().getTime(); startDay += 86400000) {
                Long hoursInDay = 0L;

                while(IsHoliday.isHolidays(holidayRepository, new Timestamp(startDay), new Timestamp(startDay + 10))) {
                    startDay += 86400000;
                }
                boolean isWeekend = IsWeekend.isWeekend(new Timestamp(startDay));
                if (!isWeekend) {
                    for (TrackRecord myRecord : allUserRecords) {
                        boolean doIntervalsOverlap = DoIntervalsOverlap.doIntervalsOverlap(myRecord.getStartTimestamp(), myRecord.getEndTimestamp(), new Timestamp(startDay), new Timestamp(startDay + 86400000));
                        if (doIntervalsOverlap) {
                            hoursInDay += myRecord.durationInHrs();
                        }
                    }
                    System.out.println(hoursInDay);
                    if (hoursInDay < 8) {
                        throw new ValidationException(messageSource.getMessage("period.err.min8hours", null, LocaleContextHolder.getLocale()));
                    }
                }
            }
            trackPeriod.setPeriodStatus(SUBMITTED);
            periodRepository.save(trackPeriod);
        }
        else {
            for (Long startDay = Math.max(trackPeriod.getStartTimestamp().getTime(),user.get().getJoiningTimestamp().getTime()); startDay < trackPeriod.getEndTimestamp().getTime(); startDay += 86400000) {
                Long hoursInDay = 0L;
                Timestamp startDayTimestamp = new Timestamp(startDay);
                int scheduleIndex = WorkingHrsForShift.getShiftIndex(user.get().getJoiningTimestamp(), startDayTimestamp, workSchedule);
                for (TrackRecord myRecord : allUserRecords) {
                    boolean doIntervalsOverlap = DoIntervalsOverlap.doIntervalsOverlap(myRecord.getStartTimestamp(), myRecord.getEndTimestamp(), new Timestamp(startDay), new Timestamp(startDay + 86400000));
                    if (doIntervalsOverlap) {
                        hoursInDay += myRecord.durationInHrs();
                    }
                }

                System.out.println(scheduleIndex);
                System.out.println(workSchedule.charAt(1));
                System.out.println(WorkingHrsForShift.hexToDecimal(workSchedule.charAt(scheduleIndex)));
                System.out.println(hoursInDay);

                if(scheduleIndex == workSchedule.length() - 1) {
                        if (hoursInDay > 0) {
                            throw new ValidationException(messageSource.getMessage("period.err.dayOffNot", null, LocaleContextHolder.getLocale()));
                        }
                }

                int workingHoursDecimal = WorkingHrsForShift.hexToDecimal(workSchedule.charAt(scheduleIndex));
                if(hoursInDay < workingHoursDecimal) {
                    throw new ValidationException(messageSource.getMessage("period.err.notEnoughHrs1", null, LocaleContextHolder.getLocale()) + workingHoursDecimal + messageSource.getMessage("period.err.notEnoughHrs2", null, LocaleContextHolder.getLocale()));
                }
            }
            trackPeriod.setPeriodStatus(SUBMITTED);
            periodRepository.save(trackPeriod);
        }
    }

    public void closeTrackPeriod(TrackPeriod trackPeriod) throws ValidationException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<AppUser> currentUser = userRepository.findByUsername(username);
        Long periodApproverId = trackPeriod.getApprover().getId();
        Long currentUserID = currentUser.get().getId();

        if(periodApproverId != currentUserID) {
            throw new ValidationException(messageSource.getMessage("period.close.err.noAccess", null, LocaleContextHolder.getLocale()));
        };

        trackPeriod.setPeriodStatus(CLOSED);
        periodRepository.save(trackPeriod);
    }
}
