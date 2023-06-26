package com.ctrlaltelite.backend.utilities;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class IsWeekendTest {
    @Test
    public void saturday_is_weekend(){
        IsWeekend createNewInstance = new IsWeekend();
        Timestamp saturday = new Timestamp(1677933064000L);
        assertTrue(IsWeekend.isWeekend(saturday));
    }
    @Test
    public void sunday_is_weekend(){
        Timestamp sunday = new Timestamp(1678024830000L);
        assertTrue(IsWeekend.isWeekend(sunday));
    }
    @Test
    public void monday_is_not_weekend(){
        Timestamp monday = new Timestamp(1678105864000L);
        assertFalse(IsWeekend.isWeekend(monday));
    }
}