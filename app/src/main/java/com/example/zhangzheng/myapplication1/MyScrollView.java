package com.example.zhangzheng.myapplication1;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class MyScrollView extends LinearLayout {


    private static final String TAG = "MyScrollView";

    /**
     * 内容高度
     */
    int measuredHeight;
    /**
     * 显示高度
     */
    int height;

    /**
     * 滑动辅助类
     */
    Scroller mScroller;
    /**
     * 瞬时滑动速度测量类
     */
    VelocityTracker mVelocityTracker;

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        mScroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measuredHeight = 0;
        //得到控件原始显示高度
        height = getMeasuredHeight();
        Log.i(TAG, "onMeasure: " + getMeasuredHeight());
        //得到高度模式 如果是未指定大小 那么重新测量控件的大小
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.UNSPECIFIED) {
            int count = getChildCount();
            //循环所有子控件重新测量并获得高度
            for (int i = 0; i < count; i++) {
                View v = getChildAt(i);
                measureChild(v, widthMeasureSpec, heightMeasureSpec);
                //注意获取子控件的margin
                MarginLayoutParams lp = (MarginLayoutParams) v.getLayoutParams();
                //加上所有的子控件的高度 就是我们当前控件所展示的高度
                measuredHeight += v.getMeasuredHeight() + lp.bottomMargin + lp.topMargin;
            }
            //调用此方法 重新更改高度
            setMeasuredDimension(getMeasuredWidth(), measuredHeight);
        } else {
            measuredHeight = height;
        }
        Log.i(TAG, "onMeasure: " + getMeasuredHeight());
    }

    float downY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (null == mVelocityTracker) {
                    mVelocityTracker = VelocityTracker.obtain();
                }
                //每当手指按下屏幕时 停止一切的滚动
                mScroller.forceFinished(true);

                mVelocityTracker.clear();
                //添加坐标 以便以得到手指滑动速度
                mVelocityTracker.addMovement(event);
                downY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                mVelocityTracker.addMovement(event);
                int move = (int) (downY - y);
                if (move < 0) {
                    //手指向下滑动  允许滑动超过正常范围的-500
                    if (getScrollY() > -500) {
                        //当滑动超过正常范围之后 滑动效果减为四分之一
                        // 造成滑动阻力的样子（这里为了简单方便直接减少为1/4，就不用通过距离算阻尼系数） 否则正常滑动
                        if (getScrollY() < 0) {
                            scrollBy(0, move * 1 / 4);
                            downY = y;
                        } else {
                            scrollBy(0, move);
                            downY = y;
                        }
                    }
                } else if (move > 0) {
                    //手指向上   允许滑动超过正常范围500
                    if (getScrollY() < measuredHeight - height + 500) {
                        //当滑动超过正常范围之后 滑动效果减为四分之一 造成滑动阻力的样子 否则正常滑动
                        if (getScrollY() > measuredHeight - height) {
                            scrollBy(0, move * 1 / 4);
                            downY = y;
                        } else {
                            scrollBy(0, move);
                            downY = y;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //当手指离开屏幕的时候 如果控件已经偏移到内容之外那么就滚动复原 反之就继续惯性滚动
                if (getScrollY() > measuredHeight - height || getScrollY() < 0) {
                    scrollReset();
                } else {
                    scrollFling();
                }
                break;
        }
        return true;
    }
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
    /**
     * 滚动复位
     */
    private void scrollReset() {
        int scrollY = getScrollY();
        if (scrollY < 0) {
            mScroller.startScroll(0, scrollY, 0, -scrollY);
            invalidate();
        } else {
            mScroller.startScroll(0, scrollY, 0, -(scrollY - (measuredHeight - height)));
            invalidate();
        }
    }
    /**
     * 惯性滚动
     */
    private void scrollFling() {
        mVelocityTracker.computeCurrentVelocity(1000);
        float yVelocity = mVelocityTracker.getYVelocity();
        /**
         * fling 方法参数注解
         *
         * startX 滚动起始点X坐标
         * startY 滚动起始点Y坐标
         * velocityX   当滑动屏幕时X方向初速度，以每秒像素数计算
         * velocityY   当滑动屏幕时Y方向初速度，以每秒像素数计算
         * minX    X方向的最小值，scroller不会滚过此点。
         *　maxX    X方向的最大值，scroller不会滚过此点。
         *　minY    Y方向的最小值，scroller不会滚过此点。
         *　maxY    Y方向的最大值，scroller不会滚过此点。
         */
        mScroller.fling(0, getScrollY(), 0, (int) -yVelocity * 2, 0, 0, 0, measuredHeight - height);
        invalidate();
    }


}
