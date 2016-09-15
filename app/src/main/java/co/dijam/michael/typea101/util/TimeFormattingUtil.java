package co.dijam.michael.typea101.util;

import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import co.dijam.michael.typea101.MyApplication;
import co.dijam.michael.typea101.R;

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

    public static String percentageFormatter(float percentage){
        return String.format("%.3f %s", percentage, MyApplication.getContext().getString(R.string.percent_of_day));
    }
    public static String percentageFormatter(long durationMillis){
        float percentage = (durationMillis / ((float) DateTimeConstants.MILLIS_PER_DAY)) * 100;
        return percentageFormatter(percentage);
    }
    public static String percentageFormatter(long startTime, long endTime) {
        long durationMillis = endTime - startTime;
        return percentageFormatter(durationMillis);
    }
}
