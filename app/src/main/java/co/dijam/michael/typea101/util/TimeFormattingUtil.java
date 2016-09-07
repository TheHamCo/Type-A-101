package co.dijam.michael.typea101.util;

import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import co.dijam.michael.typea101.MyApplication;

/**
 * Created by mdd23 on 9/6/2016.
 */
public class TimeFormattingUtil {
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormat
            .forPattern("h:mm:ss a")
            .withZone(DateTimeZone.forTimeZone(MyApplication.getTimeZone()));
    public static final PeriodFormatter durationFormatter = new PeriodFormatterBuilder()
            .printZeroNever()
            .appendHours().appendSuffix(" hr ")
            .appendMinutes().appendSuffix(" min ")
            .printZeroAlways()
            .appendSeconds().appendSuffix(" sec")
            .toFormatter();
}
