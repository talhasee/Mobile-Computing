package com.example.steps;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SensorWork mSensorWork;
    TextView Steps, direction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout relativeLayout = findViewById(R.id.RelativeID);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5500);
        animationDrawable.start();

        Steps = findViewById(R.id.steps);
        direction = findViewById(R.id.direction);

        mSensorWork = new SensorWork(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorWork.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorWork.mSensorManager.unregisterListener(mSensorWork);
    }
}