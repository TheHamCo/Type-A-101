package co.dijam.michael.typea101.modifytask.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

/**
 * Created by mdd23 on 9/15/2016.
 */
public class ModifyTaskAnimator {
    public void rotateButton(final ImageView b) {
        float deg = (b.getRotation() == 180f) ? 0f : 180F;
        b.animate()
                .rotation(deg)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        b.setRotation(deg);
                    }
                })
                .start();
    }

    public void toggleExpandCollapseAnimation(final View v, final View clickedView) {
        if (viewIsCollapsed(v)) {
            expandView(v, clickedView);
        } else {
            collapseView(v, clickedView);
        }
    }

    private boolean viewIsCollapsed(View v) {
        return v.getHeight() == 0;
    }

    private void collapseView(final View v, final View clickedView) {
        viewMeasure(v);
        ValueAnimator va = ValueAnimator.ofInt(v.getMeasuredHeight(), -1);
        valueAnimatorDecorator(va, v);
        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                clickedView.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                v.setVisibility(View.GONE);
                clickedView.setClickable(true);
                super.onAnimationEnd(animation);
            }
        });
        va.start();
    }

    private void expandView(final View v, final View clickedView) {
        v.setVisibility(View.VISIBLE);
        viewMeasure(v);
        ValueAnimator va = ValueAnimator.ofInt(0, v.getMeasuredHeight());
        valueAnimatorDecorator(va, v);
        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                clickedView.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                clickedView.setClickable(true);
                super.onAnimationEnd(animation);
            }
        });
        va.start();
    }
    private void viewMeasure(final View v) {
        v.measure(View.MeasureSpec.makeMeasureSpec(getParentWidth(v), View.MeasureSpec.AT_MOST)
                , View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
    }

    private void valueAnimatorDecorator(final ValueAnimator va, final View v) {
        va.setDuration(400);
        va.addUpdateListener(animation -> {
            v.getLayoutParams().height = (Integer) animation.getAnimatedValue();
            v.requestLayout();
        });
    }

    //http://stackoverflow.com/a/35479780/5302182
    private int getParentWidth(View viewToScale) {
        return ((View) viewToScale.getParent()).getWidth();
    }

}
