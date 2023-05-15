package com.example.alarmapplication;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class AlarmService extends Service {
    static final String TAG = "SERVICE_STARTED";
    String INPUT_TIME = "00:00:00", INPUT_TIME2 = "00:00:00";
    private Timer timer;
    MyBroadcastReceiver myReceiver;
    private MediaPlayer mediaPlayer;
    private Handler handler;
    Boolean Played = false, PlayedAlarm1 = false, PlayedAlarm2 = false;
    int flag = 1;
    String Time, Time2;
    public AlarmService() {
    }
    @Override
    public void onCreate(){
        super.onCreate();
        Log.i(TAG, "Service is started");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        mediaPlayer = new MediaPlayer();
        timer = new Timer();
        myReceiver = new MyBroadcastReceiver();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_LOW);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_BATTERY_OKAY);
        registerReceiver(myReceiver, filter);

        Time = intent.getStringExtra("INPUT_TIME_ALARM1");
        Time2 = intent.getStringExtra("INPUT_TIME_ALARM2");
//        String temp2 = Time.substring(3);
////        int x = (Time.length() - Integer.parseInt(Time.substring(3)));
////        Log.i(TAG, "TIME IDHAR -->"+String.valueOf(x));
//        if(Time.charAt(2) != ':'){
//            String temp = "0"+Time;
//            Time = temp;
//        }
//        else if(Integer.parseInt(Time.substring(3)) < 10){
//            String temp = Time.substring(0,3);
//            Time = temp + "0"+ Time.substring(3);
//        }
        Log.i("SERVICE_STARTED", Time + " : "+ Time2);
        if(Time == null) {
            INPUT_TIME2 = Time2;
            PlayedAlarm2 = false;
        }
        else if(Time2 == null) {
            PlayedAlarm1 = false;
            INPUT_TIME = Time;
        }
        else{
            INPUT_TIME = Time;
            INPUT_TIME2 = Time2;
            PlayedAlarm1 = false;
            PlayedAlarm2 = false;
        }
        Log.i(TAG, "Service is started!!....."+Time);
//        timer = new Timer();
//        timer.scheduleAtFixedRate(new CheckTimeTask(), 0, 10000);

        // Create a new MediaPlayer instance and prepare it
        mediaPlayer = MediaPlayer.create(this, R.raw.ramdarshan);
        mediaPlayer.setLooping(false);

        // Create a new Handler instance

        handler = new Handler(Looper.getMainLooper());
        Log.i(TAG, "Handler initializing!!.....");
        timer = new Timer();
        timer.scheduleAtFixedRate(new CheckTimeTask(), 0, 10000);
        return Service.START_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        try{
            timer.cancel();
            unregisterReceiver(myReceiver);
            mediaPlayer.pause();
            Time = Time2 = null;
//            mediaPlayer.release();
            Log.i("Service stopping..", "Service Stopped!!");
        }
        catch (Exception e){
            Log.i(TAG, e.toString());
        }
    }
    private class CheckTimeTask extends TimerTask {

        @Override
        public void run() {
            // Get the current time
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String currentTime = sdf.format(calendar.getTime());
            Log.i(TAG, "Time is : "+INPUT_TIME);
            Log.i(TAG, "Current Time : "+currentTime);
            // Check if the current time matches the input time
            try {
                if (currentTime.equals(INPUT_TIME) && !PlayedAlarm1) {
                    PlayedAlarm1 = true;
                    showToastAndLog("Alarm 1 - " + Time + " ringing");
                    Time = null;
                    // Start the music and show the toast and log messages
                    Log.i(TAG, "Time1 : "+INPUT_TIME);
                    mediaPlayer.start();

                    if(handler == null)
                        Log.i(TAG, "Handler not initialized!!");
//                  Stop the music after 10 seconds using a Handler
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            mediaPlayer.pause();
                            mediaPlayer.pause();
                            showToastAndLog("Music stopped.");
                        }
                    }, 10000);
                }
                else if(currentTime.equals(INPUT_TIME2) && !PlayedAlarm2) {
                    PlayedAlarm2 = true;
                    showToastAndLog("Alarm - 2 " + Time2 + " ringing");
                    Time2 = null;
                    // Start the music and show the toast and log messages
                    Log.i(TAG, "Time2 : "+INPUT_TIME2);
                    mediaPlayer.start();

//                  Stop the music after 10 seconds using a Handler
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            mediaPlayer.pause();
                            mediaPlayer.pause();
                            showToastAndLog("Music stopped.");
                        }
                    }, 10000);
                }

            }
            catch (Exception e){
                Log.i(TAG, e.toString());
            }

        }
        private void showToastAndLog(String message) {
            // Show a toast and log the message
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast toast = Toast.makeText(AlarmService.this, message, Toast.LENGTH_SHORT);
                    Log.i("ALARM", message);
                    toast.show();
                }
            });
//            Toast.makeText(AlarmService.this, message, Toast.LENGTH_SHORT).show();
            System.out.println(message);
        }
    }
    }
