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

public class alarm2_Fragment extends Fragment {
    View view;
    Button HomeAlarm1;
    TimePicker timeP2;
    String time;
    int hours = 0, minutes = 0;
    boolean alarmChecked = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_alarm2_, container, false);
        Button alarm2 = (Button) view.findViewById(R.id.alrmBtn2);
        Button cancel2 = (Button) view.findViewById(R.id.cancelBtn2);
        Intent intent = new Intent(view.getContext(), AlarmService.class);

        alarm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    HomeAlarm1 = (Button) view.findViewById(R.id.fragButton1);
                    timeP2 = view.findViewById(R.id.timePicker2);
                    if(alarmChecked == true) {
                        alarmChecked = false;
                        intent.putExtra("INPUT_TIME", time);
//                        view.getContext().stopService(intent);
                    }
                    alarmChecked = true;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        hours = timeP2.getCurrentHour();
                    }
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        minutes = timeP2.getCurrentMinute();
                    }
                    time = String.valueOf(hours) +":"+ String.valueOf(minutes);
//                    intent.putExtra("INPUT_TIME", time);
                    if(alarmChecked = true)
                        intent.putExtra("INPUT_TIME", time);

//                        view.getContext().startService(intent);

                    Log.i("TIME DEKH FRAGMENT - 2", time);
//                    HomeAlarm1.setText(time);
                }
                catch (Exception e){
                    Log.i("DIKKAT HOGYI FRAGMENT-2", e.toString());
                }
            }
        });

        cancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {
//                    alarmChecked = false;
//                    view.getContext().stopService(intent);
//                    Log.i("SERVICE_STARTED", "SERVICE IS STOPPED FRAG-2!!!");
//                }
//                catch (Exception e){
//                    Log.i("SERVICE_STARTED FRAG-2", e.toString());
//                }

            }
        });
        return view;
    }
}