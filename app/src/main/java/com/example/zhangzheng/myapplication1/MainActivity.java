package com.example.zhangzheng.myapplication1;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Scroller;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.Unit;
import interfaces.heweather.com.interfacesmodule.bean.weather.Weather;
import interfaces.heweather.com.interfacesmodule.bean.weather.forecast.ForecastBase;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GestureDetector.OnGestureListener {
    Button button, button1;
    EditText textView;
    TestButton testButton;
    private final String TAG = "MainActivityContent ";
    public static final String TAG1 = "compare";
    public static final String TAG2 = "he_weather";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button.setOnClickListener(this);
        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(this);
        textView = findViewById(R.id.textview);
        testButton = findViewById(R.id.test_button);
        testButton.setOnClickListener(this);
        if (savedInstanceState != null) {
            Log.d(TAG1, TAG + " onCreate savedInstanceState != null " + savedInstanceState);
        } else {
            Log.d(TAG1, TAG + " onCreate savedInstanceState == null");
        }
        Log.d(TAG1, TAG + " onCreate");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button: {
              /*  Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);*/
                requestPermission();
            }
            break;
            case R.id.button1: {
           /*     Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                startActivity(intent);*/
                setButtonAnimator();
            }
            break;
            case R.id.test_button:
                Log.d(TAG, "onClick: ");
            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        Log.d(TAG1, TAG + " onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG1, TAG + " onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG1, TAG + " onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG1, TAG + " onSaveInstanceState");
        if (outState != null) {
            Log.d(TAG1, TAG + " onSaveInstanceState : " + outState);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG1, TAG + " onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        Log.d(TAG1, TAG + "onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d(TAG1, TAG + " onNewIntent");
        if (intent != null) {
            Log.d(TAG1, TAG + intent.getBooleanExtra("key", false));
        }
        super.onNewIntent(intent);
    }

    private void getGsonFromHeWeather() {
        HeWeather.getWeather(this, "CN101010100", Lang.CHINESE_TRADITIONAL, Unit.METRIC, new HeWeather.OnResultWeatherDataListBeansListener() {
            @Override
            public void onError(Throwable throwable) {
                Log.d(TAG2, "error:" + throwable.toString());
            }

            @Override
            public void onSuccess(List<Weather> list) {
               /* Log.d(TAG2,""+list.size());
                Log.d(TAG2,""+new Gson().toJson(list));*/
                Log.d(TAG2, "" + list.get(0).getDaily_forecast().size());
                for (ForecastBase forecastBase : list.get(0).getDaily_forecast()) {
                    Log.d(TAG2, forecastBase.getDate() + "最高气温" + forecastBase.getTmp_max()
                            + forecastBase.getDate() + "最低气温" + forecastBase.getTmp_min());
                }


            }
        });
    }

    private void requestPermission() {
        String[] mPermissions = {Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (AndPermission.hasPermissions(this, mPermissions)) {
            getGsonFromHeWeather();
        } else {
            AndPermission.with(this)
                    .runtime()
                    .permission(mPermissions)
                    .onGranted(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> data) {
                            getGsonFromHeWeather();
                        }
                    })
                    .onDenied(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> data) {
                            Log.d(TAG2, "onDenied");
                        }
                    })
                    .start();
        }
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


    private void setButtonAnimator() {
        ObjectAnimator.ofFloat(button, "translationY", 0, 1000).setDuration(1000).start();
        //ObjectAnimator.ofFloat(button, "translationY", 0, -100).setDuration(1000).start();

    }


}
