package com.example.zhangzheng.myapplication1;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Button;
import android.widget.Scroller;
import android.widget.TextView;

public class TestButton extends AppCompatButton implements View.OnClickListener, GestureDetector.OnGestureListener {
    private int mLastX;
    private int mLastY;
    private int mLastRawX;
    private int mLastRawY;
    private final String TAG = "TestButton TouchEvent";

    public TestButton(Context context) {
        this(context, null);
    }

    public TestButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }



    private void GestureDetctorLearning(MotionEvent motionEvent) {
        // velocity
        VelocityTracker velocityTracker = VelocityTracker.obtain();
        velocityTracker.addMovement(motionEvent);
        velocityTracker.computeCurrentVelocity(1000);
        int xVelocity = (int) velocityTracker.getXVelocity();
        int yVelocity = (int) velocityTracker.getYVelocity();

        //gestureDetector
        GestureDetector gestureDetector = new GestureDetector(this);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: ");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
      /*  Log.d(TAG,"rawX = "+rawX+"  rawY= "+rawY);
        Log.d(TAG,"x = "+x+"  y = "+y);*/
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                mLastRawX = rawX;
                mLastRawY = rawY;
               // Log.d(TAG, "onTouchEvent: ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE: {
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;

                int deltaRawX = rawX - mLastRawX;
                int deltaRawY = rawY - mLastRawY;
                Log.d(TAG, "onTouchEvent: x = "+x+"  y = "+y);
                Log.d(TAG, "onTouchEvent: rawX = "+rawX+"  rawY = "+rawY);
                Log.d(TAG,"deltaX = "+deltaX+"  deltaY = "+deltaY);
                Log.d(TAG,"deltaRawX = "+deltaRawX+"  deltaRawY = "+deltaRawY);
                Log.d(TAG,"getLeft()0 = "+getLeft()+"  getTop()0 = "+getTop());
               layout(getLeft() + deltaX, getTop() + deltaY, getRight() + deltaX, getBottom() + deltaY);
                Log.d(TAG,"getLeft() = "+getLeft()+"  getTop() = "+getTop());
               //scrollTo(rawX,rawY);
              //  Log.d(TAG, "onTouchEvent: ACTION_MOVE");
            }
            break;
            case MotionEvent.ACTION_UP:
              //  Log.d(TAG, "onTouchEvent: ACTION_UP");
                break;
            default:
                break;
        }
        return true;
    }
}
