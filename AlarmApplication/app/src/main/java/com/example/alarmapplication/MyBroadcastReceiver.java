package com.example.alarmapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.i("BROADCAST", "ENTERED OnRECEIVE()" );
        if (action != null) {
            if (action.equals(Intent.ACTION_POWER_CONNECTED) || action.equals(Intent.ACTION_BATTERY_OKAY)) {
                // Stop the MyService instance
                Intent serviceIntent = new Intent(context, AlarmService.class);
                Log.i("BROADCAST", "SERVICE STOPPED BY BROADCAST!!");
                Toast.makeText(context.getApplicationContext(), "SERVICE STOPPED BY BROADCAST", Toast.LENGTH_SHORT).show();
                context.stopService(serviceIntent);
            }
        }
    }
}
