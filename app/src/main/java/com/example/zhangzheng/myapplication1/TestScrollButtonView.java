package com.example.zhangzheng.myapplication1;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.widget.Button;
import android.widget.Scroller;

public class TestScrollButtonView extends AppCompatButton {
    private final String TAG = "TestScrollButtonView";
    int i = 0;
    Scroller mScroller;
    public TestScrollButtonView(Context context) {
        super(context);
        mScroller = new Scroller(context);
    }

    private void smoothScrollTo(int destX,int destY){
        int scrollX = getScrollX();
        int deltaX = destX - scrollX;
        mScroller.startScroll(scrollX,0,deltaX,0,1000);
        invalidate();

    }

    @Override
    public void computeScroll() {
        Log.d(TAG, "computeScroll: "+(i++));
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }
}
