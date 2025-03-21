package com.tqs.lab5_2;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Utils {
    public static LocalDate localDateFromDateParts(String year, String month, String day){
        return LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
    }

    public static LocalDateTime isoTextToLocalDate(String isoText){
        return LocalDate.parse(isoText).atStartOfDay();
    }
}
