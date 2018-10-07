package com.example.zhangzheng.myapplication1;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Scroller;

public class ScrollViewLinearLayout extends LinearLayout implements View.OnTouchListener {
    private LinearLayout top;
    private ScrollView sv;
    private boolean isFrist = true;
    private float y1, y2;
    private int high = 60;
    private Scroller mScroller;
    private final String TAG = "ScrollViewLinearLayout";

    public ScrollViewLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
        setLongClickable(true);
        mScroller = new Scroller(context);
    }

    protected void smoothScrollBy(int dx, int dy) {
        //set the scroll values
        mScroller.startScroll(0, mScroller.getFinalY(), 0, dy);
        invalidate();
    }

    protected void smoothScrollTo(int fx, int fy) {
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        smoothScrollBy(0, dy);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed && isFrist) {
            sv = (ScrollView) getChildAt(0);
            sv.setOverScrollMode(View.OVER_SCROLL_NEVER);
            sv.setOnTouchListener(this);
            isFrist = false;
        }

    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                y2 = event.getY();
                int scrollY = v.getScrollY();
                int height = v.getHeight();
                int scrollViewMeasuredHeight = sv.getChildAt(0).getMeasuredHeight();
                // the springback effect of head
                if (y2 - y1 > 0 && v.getScaleY() <= 0) {
                    smoothScrollTo(0, -(int) (y2 - y1) / 2);
                    Log.d(TAG, "topMargin = " + (int) (y2 - y1) / 2);
                    return false;
                }
                //the Springback effect fo bottom
                if (y2 - y1 < 0 && (scrollY + height) == scrollViewMeasuredHeight) {
                    smoothScrollTo(0, -(int) (y2 - y1) / 2);
                    return false;
                }
                break;
            default:
                break;

        }
        return false;
    }
}
