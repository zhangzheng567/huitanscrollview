package com.example.zhangzheng.myapplication1;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.yanzhenjie.permission.AndPermission;

public class Main2Activity extends Activity implements PullScrollView.OnTurnListener, View.OnClickListener {
    private PullScrollView mScrollView;
    private ImageView mHeadImg;
    private final String TAG = "Main2Activity ";

    private TableLayout mMainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        initData();
        initEvent();
        showTable();
        Log.d(MainActivity.TAG1, TAG + "onCreate");
    }

    @Override
    protected void onStart() {
        Log.d(MainActivity.TAG1, TAG + "onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(MainActivity.TAG1, TAG + "onResume");
        super.onResume();
    }


    protected void initView() {
        mScrollView = (PullScrollView) findViewById(R.id.scroll_view);
        mHeadImg = (ImageView) findViewById(R.id.background_img);
        mMainLayout = (TableLayout) findViewById(R.id.table_layout);
        mScrollView.setHeader(mHeadImg);


    }

    private void initData() {

    }

    private void initEvent() {
        mHeadImg.setOnClickListener(this);
        mScrollView.setOnTurnListener(this);
    }

    public void showTable() {
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.leftMargin = 30;
        layoutParams.bottomMargin = 10;
        layoutParams.topMargin = 10;

        for (int i = 0; i < 30; i++) {
            TableRow tableRow = new TableRow(this);
            TextView textView = new TextView(this);
            textView.setText("Test pull down scroll view " + i);
            textView.setTextSize(20);
            textView.setPadding(15, 15, 15, 15);

            tableRow.addView(textView, layoutParams);
            if (i % 2 != 0) {
                tableRow.setBackgroundColor(Color.LTGRAY);
            } else {
                tableRow.setBackgroundColor(Color.WHITE);
            }

            final int n = i;
            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Main2Activity.this, "Click item " + n, Toast.LENGTH_SHORT).show();
                }
            });

            mMainLayout.addView(tableRow);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.background_img:
                Intent intent = new Intent(this,Main3Activity.class);
                startActivity(intent);
                break;
            default:
                break;

        }
    }

    @Override
    public void onTurn() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d(MainActivity.TAG1, TAG + " onNewIntent");
        super.onNewIntent(intent);
    }

    @Override
    public void onBackPressed() {
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.putExtra("key", true);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//        finish();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        Log.d(MainActivity.TAG1,TAG+"onDestroy");
        super.onDestroy();
    }

}
