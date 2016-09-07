package co.dijam.michael.typea101.mainscreen;

/**
 * Created by mdd23 on 9/7/2016.
 */
public interface MainScreenContract {
    interface View {
        void onFABclick();

        // Nav drawer
        void startTagListFeature();
        void startLifeTimeStatsFeature();
        void startExportDataFeature();
        void openSettings();

        //Toolbar
        void showDate(String formattedDate);
        void onPreviousDay();
        void openDatePicker();
        void onToday();
        void onNextDay();

        //Snackbar
        void showTimerSnackbar(String taskName, String tag, String formattedTime);
        void hideSnackbar();

        //Main View
        void swapMainView(long dateTime);
    }

    interface Presenter {
        boolean currentTaskExists();
        boolean isToday(long dateTime);

        void runTimer();
        void stopTimer();
    }
}
