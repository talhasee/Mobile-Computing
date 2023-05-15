package com.example.alarmapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import java.sql.Time;

public class alarm1Fragment extends Fragment {
    View view;
    Button HomeAlarm1;
    TimePicker timeP1;
    String time;
    int hours = 0, minutes = 0;
    boolean alarmChecked = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_alarm1, container, false);
        Button alarm1 = (Button) view.findViewById(R.id.alrmBtn1);
        Button cancel1 = (Button) view.findViewById(R.id.cancelBtn1);
        Intent intent = new Intent(view.getContext(), AlarmService.class);

        alarm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    HomeAlarm1 = (Button) view.findViewById(R.id.fragButton1);
                    timeP1 = view.findViewById(R.id.timePicker1);
                    if(alarmChecked == true) {
                        alarmChecked = false;
                        intent.putExtra("INPUT_TIME", time);
//                        view.getContext().stopService(intent);
                    }
                    alarmChecked = true;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        hours = timeP1.getCurrentHour();
                    }
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        minutes = timeP1.getCurrentMinute();
                    }
                    time = String.valueOf(hours) +":"+ String.valueOf(minutes);
//                    intent.putExtra("INPUT_TIME", time);
                    if(alarmChecked = true)
                        intent.putExtra("INPUT_TIME", time);
//                        view.getContext().startService(intent);

                    Log.i("TIME DEKH", time);
//                    HomeAlarm1.setText(time);
                }
                catch (Exception e){
                    Log.i("DIKKAT HOGYI", e.toString());
                }
            }
        });

//        cancel1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    alarmChecked = false;
//                    view.getContext().stopService(intent);
//                    Log.i("SERVICE_STARTED", "SERVICE IS STOPPED!!!");
//                }
//                catch (Exception e){
//                    Log.i("SERVICE_STARTED", e.toString());
//                }
//
//            }
//        });
        return view;
    }
}