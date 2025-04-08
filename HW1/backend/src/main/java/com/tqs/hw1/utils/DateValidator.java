package com.tqs.hw1.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DateValidator {
    public static boolean isValid(String dateStr) {
        try {
            LocalDate.parse(dateStr);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}