package com.example.sensors_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements SensorDataCollector.SensorDataCallback, SensorEventListener {
    private SensorDataCollector sensorDataCollector;
    private SensorManager sensorMgr;
    private Sensor ProximitySens, LightSens, RotationVecSens;
    public boolean ProxyOn, LightOn, GeoON;
    public float ProxValue = -1, LightValue = -1;
    float[] GeoValues = new float[3];
    public DataBaseHelper dataBaseHelper;

    TextView ProxText, LightText, GeoText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Sensors Init
        sensorMgr = (SensorManager)this.getSystemService(Context.SENSOR_SERVICE);
        ProximitySens = sensorMgr.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        LightSens = sensorMgr.getDefaultSensor(Sensor.TYPE_LIGHT);
        RotationVecSens = sensorMgr.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);


        ToggleButton Prox, Light, GeoMag;
        Prox = findViewById(R.id.Proximity);
        Light = findViewById(R.id.Light);
        GeoMag = findViewById(R.id.GeoMagRotate);
        ProxText = findViewById(R.id.ProxText);
        LightText = findViewById(R.id.LightText);
        GeoText = findViewById(R.id.GeoText);
        sensorDataCollector = new SensorDataCollector(this);
        dataBaseHelper = DataBaseHelper.getDB(this);

        Prox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.d("SensorDataCollector", Prox.toString());
                    startCollectingProximityData();

//                    sensorDataCollector.startCollectingProximityData();
//                    ProxText.setText(Float.toString(sensorDataCollector.ProxValue));
                }
                if(!isChecked && ProxyOn) {
                    Log.d("UnregisterProxy", "Inside Sensor Unregistered: "+ProxyOn);
                    stopCollectingProximityData();
                    ProxText.setText("Sensor OFF");
//                    sensorDataCollector.stopCollectingProximityData();
//                    ProxText.setText("SESOR OFF");
                }
            }
        });
        Light.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.d("SensorDataCollector", Light.toString());
                    startCollectingLightData();
//                    sensorDataCollector.startCollectingLightData();
//                    LightText.setText(Float.toString(sensorDataCollector.LightValue));
                }
                if(!isChecked && LightOn) {
                    stopCollectingLightData();
                    LightText.setText("Sensor OFF");
//                    sensorDataCollector.stopCollectingLightData();
//                    LightText.setText("SENSOR OFF");
                }
            }
        });
        GeoMag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.d("SensorDataCollector", GeoMag.toString());
                    startCollectingRotationVectorData();
//                    GeoText.setText(Float.toString(GeoValues[0])+" "+Float.toString(GeoValues[1])+" "+Float.toString(GeoValues[2]));
//                    sensorDataCollector.startCollectingRotationVectorData();
//                    GeoText.setText(Float.toString(sensorDataCollector.GeoValues[0])+" "+Float.toString(sensorDataCollector.GeoValues[1])+" "+Float.toString(sensorDataCollector.GeoValues[0]));
                }
                if(!isChecked && GeoON){
                    stopCollectingRotationVectorData();
                    GeoText.setText("Sensor OFF");
//                    sensorDataCollector.stopCollectingRotationVectorData();
//                    GeoText.setText("SENSOR OFF");
                }
            }
        });
//        DataBaseHelper dataBaseHelper = DataBaseHelper.getDB(this);
////        dataBaseHelper.sensorsDAO().addSensor(
////                new Sensors("Sensor1", "10")
////        );
//        ArrayList<Proximity_Sensors> arr_Sensors = (ArrayList<Proximity_Sensors>) dataBaseHelper.Proxy_sensorsDAO().getAllSensors();
//
//        for(int i = 0 ; i < arr_Sensors.size(); i++)
//            Log.d("SensorDataCollector", " Sensor_Value "+arr_Sensors.get(i).getSensor_value());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    //start Proximity Sensor
    public void startCollectingProximityData() {
        if(!ProxyOn) {
            boolean success = sensorMgr.registerListener(this, ProximitySens, SensorManager.SENSOR_DELAY_NORMAL);
            if(success)
                Log.d("SensorDataCollector", "Proximity sensor started");
            else
                Log.d("SensorDataCollector", "Proximity sensor failed");
            ProxyOn = true;
        }
    }
    // Stop Proximity Sensor
    public void stopCollectingProximityData() {
        if(ProxyOn) {
            sensorMgr.unregisterListener(this, ProximitySens);
            Log.d("UnregisterProxy", "Sensor Unregistered: "+ProxyOn);
            ProxyOn = false;
        }
    }
    //Start Light sensor
    public void startCollectingLightData() {
        if(!LightOn) {
            boolean success = sensorMgr.registerListener(this, LightSens, SensorManager.SENSOR_DELAY_NORMAL);
            if(success)
                Log.d("SensorDataCollector", "Light sensor started");
            else
                Log.d("SensorDataCollector", "Light sensor Failed");
            LightOn = true;
        }
    }
    //Stop Light Sensor
    public void stopCollectingLightData() {
        if(LightOn) {
            sensorMgr.unregisterListener(this, LightSens);
            LightOn = false;
        }
    }
    // Start Geo Sensor
    public void startCollectingRotationVectorData() {
        if(!GeoON){
            boolean success = sensorMgr.registerListener(this, RotationVecSens, SensorManager.SENSOR_DELAY_NORMAL);
            if(success)
                Log.d("SensorDataCollector", "Rotation vector sensor started");
            else
                Log.d("SensorDataCollector", "Rotation vector sensor Failed");
            GeoON = true;
        }
    }

    public void stopCollectingRotationVectorData() {
        if(GeoON) {
            sensorMgr.unregisterListener(this, RotationVecSens);
            GeoON = false;
        }
    }
    @Override
    public void onSensorDataChanged(float[] values) {
        // update UI with sensor data
        ProxText.setText(Float.toString(values[0]));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {

            ProxValue = event.values[0];
            Log.d("SensorDataCollector", "Proximity sensor value: " + event.values[0]);
            ProxText.setText(Float.toString(ProxValue));
            dataBaseHelper.Proxy_sensorsDAO().addSensor(new Proximity_Sensors(Float.toString(ProxValue)));
        } else if (event.sensor.getType() == Sensor.TYPE_LIGHT) {

            LightValue = event.values[0];
            Log.d("SensorDataCollector", "Light sensor value: " + event.values[0]);
            LightText.setText(Float.toString(LightValue));
            dataBaseHelper.Light_sensorsDAO().addSensor(new Light_Sensors(Float.toString(LightValue)));

        } else if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {

            GeoValues[0] = event.values[0] * 180;
            GeoValues[1] = event.values[1] * 180;
            GeoValues[2] = event.values[2] * 180;
            if(GeoValues[2] > -2 && GeoValues[2] < 2)
                GeoText.setText("Device is aligned to Magnetic North");
            else if(GeoValues[2] > 2)
                GeoText.setText("Rotate the device RIGHT by "+Float.toString(GeoValues[2]));
            else if(GeoValues[2] < -2)
                GeoText.setText("Rotate the device LEFT by "+Float.toString(GeoValues[2]));
            else
                GeoText.setText("x = "+Float.toString(GeoValues[0])+" y =  "+Float.toString(GeoValues[1])+" z = "+Float.toString(GeoValues[2]));
            dataBaseHelper.Geo_sensorsDAO().addSensor(new Geo_Sensors(Arrays.toString(GeoValues)));
            Log.d("SensorDataCollector", "Rotation vector sensor values: x=" + GeoValues[0] + ", y=" + GeoValues[1] + ", z=" + GeoValues[2] + ", scalar=" + event.values[3]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        if (sensor == ProximitySens) {
            Log.i("SensorDataCollector", "Proximity sensor accuracy changed to: " + accuracy);
            // Handle changes in proximity sensor accuracy
        } else if (sensor == RotationVecSens) {
            Log.i("SensorDataCollector", "Rotation vector sensor accuracy changed to: " + accuracy);
            // Handle changes in rotation vector sensor accuracy
        }
    }
}