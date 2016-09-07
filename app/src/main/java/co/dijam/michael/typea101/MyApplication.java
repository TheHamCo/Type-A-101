package co.dijam.michael.typea101;

import android.app.Application;
import android.content.Context;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by mdd23 on 9/6/2016.
 */
public class MyApplication extends Application {
    private static Context context = null;
    private static TimeZone timeZone = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        timeZone = Calendar.getInstance().getTimeZone();
    }

    public static Context getContext(){
        if (context == null){
            context = this;
        }
        return context;
    }

    public static TimeZone getTimeZone() {
        if (timeZone == null){
            timeZone = Calendar.getInstance().getTimeZone();
        }
        return timeZone;
    }
}
