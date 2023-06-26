package com.ctrlaltelite.backend.utilities;

import java.sql.Timestamp;

public class WorkingHrsForShift {

    public static int getShiftIndex(Timestamp dateOfJoin, Timestamp startOfPeriod, String schedule){
        long totalEmployedTimeInDays = (startOfPeriod.getTime() - dateOfJoin.getTime() ) / 86400000;

        return (int) ((totalEmployedTimeInDays) % schedule.length());
    }

    public static int hexToDecimal(char c){
        if (Character.isDigit(c)) {
            return Character.digit(c, 16);
        } else {
            switch (Character.toUpperCase(c)) {
                case 'A': return 10;
                case 'B': return 11;
                case 'C': return 12;
                case 'D': return 13;
                case 'E': return 14;
                case 'F': return 15;
                case 'G': return 16;
                default: return -1;
            }
        }
    }
}


