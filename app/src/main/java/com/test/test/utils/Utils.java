package com.test.test.utils;

import org.joda.time.LocalDate;
import org.joda.time.Years;

import java.util.Date;

public final class Utils {

    private Utils() {
    }

    public static String formatName(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public static int getAge(Date birthdayDate) {
        LocalDate birthday = new LocalDate(birthdayDate);
        LocalDate now = new LocalDate();
        return Years.yearsBetween(birthday, now).getYears();
    }
}
