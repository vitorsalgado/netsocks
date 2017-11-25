package com.netsocks.campaign.libs;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public final class DateUtils {
    public static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}
