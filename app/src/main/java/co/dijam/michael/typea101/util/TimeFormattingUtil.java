package co.dijam.michael.typea101.util;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 * Created by mdd23 on 9/6/2016.
 */
public class TimeFormattingUtil {
    public static final DateTimeFormatter timeFormatter = DateTimeFormat
            // e.g. 7:32:12 AM
            .forPattern("h:mm:ss a");
    public static final PeriodFormatter durationFormatter = new PeriodFormatterBuilder()
            .printZeroNever()
            .appendHours().appendSuffix(" hr ")
            .appendMinutes().appendSuffix(" min ")
            .printZeroAlways()
            .appendSeconds().appendSuffix(" sec")
            .toFormatter();
    public static final DateTimeFormatter dateFormatter = DateTimeFormat
            //e.g. Tue, 12 Jun 2016
            .forPattern("E, d MMM yyyy");
}
