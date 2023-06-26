package com.ctrlaltelite.backend.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.sql.Timestamp;


public class DoIntervalsOverlap {
    private Timestamp start1;
    private Timestamp end1;
    private Timestamp start2;
    private Timestamp end2;

    private static MessageSource messageSource;

    public static boolean doIntervalsOverlap(Timestamp start1, Timestamp end1, Timestamp start2, Timestamp end2) {
        if (start1 == null || end1 == null || start2 == null || end2 == null || start1.after(end1) || start2.after(end2)) {
            String err = messageSource.getMessage("overlap.err.invalid", null, LocaleContextHolder.getLocale());
            throw new IllegalArgumentException(err);
        }

        return start1.before(end2) && start2.before(end1);
    }
}
