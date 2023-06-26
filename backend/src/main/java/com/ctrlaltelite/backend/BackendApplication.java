package com.ctrlaltelite.backend;


import com.ctrlaltelite.backend.models.*;
import com.ctrlaltelite.backend.repositories.HolidayRepository;
import com.ctrlaltelite.backend.repositories.PeriodRepository;
import com.ctrlaltelite.backend.repositories.RecordRepository;
import com.ctrlaltelite.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;
import java.util.HashSet;

@SpringBootApplication

public class BackendApplication /*implements CommandLineRunner */{
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final RecordRepository recordRepository;
    private final PeriodRepository periodRepository;
    private final HolidayRepository holidayRepository;

    @Autowired
    public BackendApplication(UserRepository userRepository, PasswordEncoder encoder, RecordRepository recordRepository, PeriodRepository periodRepository, HolidayRepository holidayRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.recordRepository = recordRepository;
        this.periodRepository = periodRepository;
        this.holidayRepository = holidayRepository;
    }

    public static void main(String[] args) { SpringApplication.run(BackendApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        AppUser user1 = userRepository.save(new AppUser(1L, "bigboss@gmail.com", "John", encoder.encode("password"), null, true,null, null, "AB0DE"));
//        user1.setJoiningTimestamp(new Timestamp(1676419200000L));
//        userRepository.save(user1);
//        HashSet<String> roles = new HashSet<>();
//        roles.add("ROLE_ADMIN");
//        roles.add("ROLE_USER");
//        AppUser user3 = userRepository.save
//                (new AppUser(2L, "thebiggestbigestboss@gmail.com", "Michael", roles, encoder.encode("password"), null, true, null, null, "8888800", new Timestamp(1672617600000L)));
//
//
//
//        TrackRecord record1 = recordRepository.save (new TrackRecord(1L, user3, RecordType.DOCTOR_VISIT, new Timestamp(1676440800000L), new Timestamp(1676491200000L),
//                "Visited my psychiatrist", null
//                ));
//        TrackRecord record2 = recordRepository.save (new TrackRecord(2L, user1, RecordType.WORKED_HOURS, new Timestamp(1676440800000L), new Timestamp(1676491200000L),
//                "Worked on this endpoint", null
//                ));
//        TrackRecord record3 = recordRepository.save (new TrackRecord(3L, user3, RecordType.WORKED_HOURS, new Timestamp(1676880000000L), new Timestamp(1676887200000L),
//                "Michael workday ", null
//                ));
//        TrackRecord record4 = recordRepository.save (new TrackRecord(4L, user3, RecordType.PUBLIC_HOLIDAY, new Timestamp(1676966400000L), new Timestamp(1676995200000L),
//                "Tuesday Holiday", null
//                ));
//        TrackRecord record5 = recordRepository.save (new TrackRecord(5L, user3, RecordType.WORKED_HOURS, new Timestamp(1677052800000L), new Timestamp(1677081600000L),
//                "Michael workday ", null
//        ));
//        TrackRecord record6 = recordRepository.save (new TrackRecord(6L, user3, RecordType.WORKED_HOURS, new Timestamp(1677139200000L), new Timestamp(1677153600000L),
//                "Michael workday", null
//        ));
//        TrackRecord record7 = recordRepository.save (new TrackRecord(7L, user3, RecordType.DOCTOR_VISIT, new Timestamp(1677153600000L), new Timestamp(1677168000000L),
//                "rehabilitation", null
//        ));
//        /*TrackRecord record8 = recordRepository.save (new TrackRecord(8L, user3, RecordType.PUBLIC_HOLIDAY, new Timestamp(1677225600000L), new Timestamp(1677254400000L),
//                null, null
//        ));*/
//        TrackRecord record9 = recordRepository.save (new TrackRecord(9L, user3, RecordType.UNPAID_LEAVE, new Timestamp(1676887200000L), new Timestamp(1676908800000L),
//                "finals at school", null
//        ));
//        TrackRecord johnRecord1 = recordRepository.save (new TrackRecord(10L, user1, RecordType.WORKED_HOURS, new Timestamp(1676880000000L), new Timestamp(1676923200000L),
//                "Johnny workday ", null
//        ));
//        TrackRecord johnRecord2 = recordRepository.save (new TrackRecord(11L, user1, RecordType.DOCTOR_ACCOMPANY, new Timestamp(1676966400000L), new Timestamp(1676980800000L),
//                "Visited hamster emergency room", "MRI-Scan_Fluffy.png"
//        ));
//        TrackRecord johnRecord3 = recordRepository.save (new TrackRecord(12L, user1, RecordType.WORKED_HOURS, new Timestamp(1676980800000L), new Timestamp(1676995200000L),
//                "Johnny workday ", null
//        ));
//        TrackRecord johnRecord4 = recordRepository.save (new TrackRecord(13L, user1, RecordType.WORKED_HOURS, new Timestamp(1677052800000L), new Timestamp(1677060000000L),
//                "Johnny workday ", null
//        ));
//        TrackRecord johnRecord5 = recordRepository.save (new TrackRecord(14L, user1, RecordType.LEAVE_FOR_FAMILY_CARE, new Timestamp(1677060000000L), new Timestamp(1677081600000L),
//                "Fluffy needed assistance ", null
//        ));
//        TrackRecord johnRecord6 = recordRepository.save (new TrackRecord(15L, user1, RecordType.OTHER, new Timestamp(1677139200000L), new Timestamp(1677182400000L),
//                "Too upset from watching The Notebook.", null
//        ));
//        /*TrackRecord johnRecord7 = recordRepository.save (new TrackRecord(16L, user1, RecordType.PUBLIC_HOLIDAY, new Timestamp(1677225600000L), new Timestamp(1677254400000L),
//                "Holiday", null
//        ));*/
//        TrackPeriod period1 = periodRepository.save(new TrackPeriod(1L, user1, user3, PeriodStatus.CLOSED, new Timestamp(1676851200000L), new Timestamp(1677456000000L)));
//        TrackPeriod period2 = periodRepository.save(new TrackPeriod(2L, user1, user3, PeriodStatus.SUBMITTED, new Timestamp(1677456000000L), new Timestamp(1678060800000L)));
//        TrackPeriod period3 = periodRepository.save(new TrackPeriod(3L, user1, user3, PeriodStatus.REQUESTED, new Timestamp(1678060800000L), new Timestamp(1678665600000L)));
//        TrackPeriod period4 = periodRepository.save(new TrackPeriod(4L, user3, user3, PeriodStatus.CLOSED, new Timestamp(1676851200000L), new Timestamp(1677456000000L)));
//        TrackPeriod period5 = periodRepository.save(new TrackPeriod(5L, user3, user3, PeriodStatus.REQUESTED, new Timestamp(1677456000000L), new Timestamp(1678060800000L)));
//
//        Holiday holiday1 = holidayRepository.save
//                (new Holiday(
//                        1L,
//                        new Timestamp(1677196800000L),
//                        new Timestamp(1677283199000L),
//                        "Public Holiday in February for John & Michael",
//                        "public holiday"
//                ));
//    }
}
