package co.dijam.michael.typea101.mainscreen.view;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.HeaderScrollingViewBehavior;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * Behavior moves the NestedScrollView up if there is a Snackbar
 * Source: http://stackoverflow.com/a/35904421/5302182
 */
public class MoveUpIfSnackbarBehavior extends HeaderScrollingViewBehavior {
    private static final boolean SNACKBAR_BEHAVIOR_ENABLED;

    public MoveUpIfSnackbarBehavior() {
        super();
    }

    public MoveUpIfSnackbarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public View findFirstDependency(List<View> views) {
        for (int i = 0, z = views.size(); i < z; i++) {
            View view = views.get(i);
            if (view instanceof AppBarLayout) {
                return (AppBarLayout) view;
            }
        }
        return null;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return SNACKBAR_BEHAVIOR_ENABLED && dependency instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        float translationY = Math.min(0, dependency.getTranslationY() - dependency.getHeight());
        child.setTranslationY(translationY);
        return true;
    }

    // Snackbar swipe-to-dismiss
    @Override
    public void onDependentViewRemoved(CoordinatorLayout parent, View child, View dependency) {
        super.onDependentViewRemoved(parent, child, dependency);
        child.setTranslationY(0);
    }

    static {
        SNACKBAR_BEHAVIOR_ENABLED = Build.VERSION.SDK_INT >= 11;
    }
}
