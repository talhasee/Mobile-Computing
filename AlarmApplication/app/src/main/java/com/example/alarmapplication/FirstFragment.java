package com.example.alarmapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class FirstFragment extends Fragment {
    View view;
    int hours  = 0, minutes = 0;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first, container, false);
        Button Alarm1, Alarm2, Start, Stop;
        TextView a1, a2;
        TimePicker timePicker;
        Alarm1 = (Button)view.findViewById(R.id.fragButton1);
        Alarm2 = (Button)view.findViewById(R.id.fragButton2);
        Start = (Button)view.findViewById(R.id.homeBtn);
        Stop = (Button) view.findViewById(R.id.stopBtn);
        a1 = (TextView)view.findViewById(R.id.Alarm1Text);
        a2 = (TextView)view.findViewById(R.id.Alarm2Text);
        timePicker = (TimePicker)view.findViewById(R.id.TimePicker);
        Intent intent  = new Intent(view.getContext(), AlarmService.class);

        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.getContext().startService(intent);
                Toast toast = Toast.makeText(view.getContext(), "Service Started!!!..", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.getContext().stopService(intent);
                Toast toast = Toast.makeText(view.getContext(), "Service Stopped!!!..", Toast.LENGTH_SHORT);
                toast.show();
                a1.setText("Alarm 1 Time");
                a2.setText("Alarm 2 Time");
            }
        });

            Alarm1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        hours = timePicker.getCurrentHour();
                    }
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        minutes = timePicker.getCurrentMinute();
                    }
                    String Time  = String.valueOf(hours) + ":" + String.valueOf(minutes);
    //                Log.i("SERVICE_STARTED", "TIME ISSUE IS - "+Time);
                    if(Time.charAt(2) != ':'){
                        String temp = "0"+Time;
                        Time = temp;
                    }
                    else if(Integer.parseInt(Time.substring(3)) < 10){
                        String temp = Time.substring(0,3);
                        Time = temp + "0"+ Time.substring(3);
                    }
                    a1.setText("Alarm 1 at "+Time );
                    intent.putExtra("INPUT_TIME_ALARM1", Time);
                }
            });

        Alarm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    hours = timePicker.getCurrentHour();
                }
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    minutes = timePicker.getCurrentMinute();
                }
                String Time  = String.valueOf(hours) + ":" + String.valueOf(minutes);
//                Log.i("SERVICE_STARTED", "TIME ISSUE IS - "+Time);
                if(Time.charAt(2) != ':'){
                    String temp = "0"+Time;
                    Time = temp;
//                    Log.i("SERVICE_STARTED", "TIME ISSUE IS - "+Time);
                }
                if(Integer.parseInt(Time.substring(3)) < 10){
                    String temp = Time.substring(0,3);
                    Time = temp + "0"+ Time.substring(3);
                }
//                Log.i("SERVICE_STARTED", "TIME ISSUE IS YAHA - "+Time);
                a2.setText("Alarm 2 at "+Time);
                intent.putExtra("INPUT_TIME_ALARM2", Time);
            }
        });
        return view;
    }
}