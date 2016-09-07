package co.dijam.michael.typea101;

import android.app.Application;
import android.content.Context;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by mdd23 on 9/6/2016.
 */
public class MyApplication extends Application {
    private static Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        JodaTimeAndroid.init(this);

    }

    public static Context getContext(){
        return context;
    }
}
