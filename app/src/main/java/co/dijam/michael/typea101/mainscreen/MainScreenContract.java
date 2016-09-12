package co.dijam.michael.typea101.mainscreen;

/**
 * Created by mdd23 on 9/7/2016.
 */
public interface MainScreenContract {
    interface View {
        //Main View
        void showTracker();
        void hideTracker();
        void updateList(long dateTime);

        // FAB
        void onFabClick();
        void styleFabAdd();
        void styleFabFinish();
        void showAddFabs();
        void hideAddFabs();

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
        void disableNextDayButton();
        void enableNextDayButton();

        //Snackbar
        void showSnackbar();
        void updateTimerSnackbar(String taskName, String tag, String formattedTime);
        void hideSnackbar();
    }

    interface Presenter {
        boolean currentTaskExists();
        boolean isToday(long dateTime);

        void presentCorrectMainView(long dateTime);

        void runSnackbarTimer();
        void stopSnackBarTimer();
    }
}
