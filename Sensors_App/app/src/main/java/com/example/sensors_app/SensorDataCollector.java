package com.example.sensors_app;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.lights.Light;
import android.util.Log;
import android.widget.TextView;
import android.widget.TextView;


public class SensorDataCollector implements SensorEventListener {
    private String TAG = "SensorDataCollector";
    private SensorManager mSensorManager;
    private Sensor mProximitySensor;
    private Sensor mLightSensor;
    private Sensor mRotationVectorSensor;
    private SensorDataCallback callback;


//    private OnProximityDataChangedListener mOnProximityDataChangedListener;
//    private OnLightDataChangedListener mOnLightDataChangedListener;
//    private OnRotationVectorDataChangedListener mOnRotationVectorDataChangedListener;

    public boolean ProxyOn, LightOn, GeoON;
    public float ProxValue = -1, LightValue = -1;
    float[] GeoValues = new float[3];
    public SensorDataCollector(Context context) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mProximitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mRotationVectorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }

    public void startCollectingProximityData() {
        if(!ProxyOn) {
            boolean success = mSensorManager.registerListener(this, mProximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
            if(success)
                Log.d("SensorDataCollector", "Proximity sensor started");
            else
                Log.d("SensorDataCollector", "Proximity sensor failed");
            ProxyOn = true;
        }
    }

    public void stopCollectingProximityData() {
        if(ProxyOn) {
            mSensorManager.unregisterListener(this, mProximitySensor);
            ProxyOn = false;
        }
    }

//    public void setOnProximityDataChangedListener(OnProximityDataChangedListener listener) {
//        mOnProximityDataChangedListener = listener;
//    }

    public void startCollectingLightData() {
        if(!LightOn) {
            boolean success = mSensorManager.registerListener(this, mLightSensor, SensorManager.SENSOR_DELAY_NORMAL);
            if(success)
                Log.d("SensorDataCollector", "Light sensor started");
            else
                Log.d("SensorDataCollector", "Light sensor Failed");
            LightOn = true;
        }
    }

    public void stopCollectingLightData() {
        if(LightOn) {
            mSensorManager.unregisterListener(this, mLightSensor);
            LightOn = false;
        }
    }

//    public void setOnLightDataChangedListener(OnLightDataChangedListener listener) {
//        mOnLightDataChangedListener = listener;
//    }

    public void startCollectingRotationVectorData() {
        if(!GeoON){
            boolean success = mSensorManager.registerListener(this, mRotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
            if(success)
                Log.d("SensorDataCollector", "Rotation vector sensor started");
            else
                Log.d("SensorDataCollector", "Rotation vector sensor Failed");
            GeoON = true;
        }
    }

    public void stopCollectingRotationVectorData() {
        if(GeoON) {
            mSensorManager.unregisterListener(this, mRotationVectorSensor);
            GeoON = false;
        }
    }

//    public void setOnRotationVectorDataChangedListener(OnRotationVectorDataChangedListener listener) {
//        mOnRotationVectorDataChangedListener = listener;
//    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
//            if (mOnProximityDataChangedListener != null) {
//                mOnProximityDataChangedListener.onProximityDataChanged(event.values[0]);
                float[] values = event.values;
                // notify callback if set
                if (callback != null) {
                    callback.onSensorDataChanged(values);
                }
                ProxValue = event.values[0];
                Log.d("SensorDataCollector", "Proximity sensor value: " + event.values[0]);
//            }
        } else if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
//            if (mOnLightDataChangedListener != null) {
//                mOnLightDataChangedListener.onLightDataChanged(event.values[0]);
//                float[] values = event.values;
//                // notify callback if set
//                if (callback != null) {
//                    callback.onSensorDataChanged(values);
//                }
                LightValue = event.values[0];
                Log.d("SensorDataCollector", "Light sensor value: " + event.values[0]);
//            }
        } else if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
//            if (mOnRotationVectorDataChangedListener != null) {
//                mOnRotationVectorDataChangedListener.onRotationVectorDataChanged(event.values);
                GeoValues[0] = event.values[0]*180;
                GeoValues[1] = event.values[1]*180;
                GeoValues[2] = event.values[2]*180;
//                float[] values = event.values;
//                // notify callback if set
//                if (callback != null) {
//                    callback.onSensorDataChanged(values);
//                }
                Log.d("SensorDataCollector", "Rotation vector sensor values: x=" + GeoValues[0] + ", y=" + GeoValues[1] + ", z=" + GeoValues[2] + ", scalar=" + event.values[3]);
//                if((GeoValues[2] < -178 && GeoValues[2] > -180) || (GeoValues[2] > 178 && GeoValues[2] < 180))
//                    Log.d("")
            float[] rotationMatrix = new float[9];
            float[] orientationAngles = new float[3];

            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
            SensorManager.getOrientation(rotationMatrix, orientationAngles);

            float azimuth = orientationAngles[0] * 180 / (float) Math.PI;

            if (azimuth > 5.0f) {
                Log.d("SensorDataCollector", "Rotate Right");
//                feedbackText.setText("Rotate right");
            } else if (azimuth < -5.0f) {
                Log.d("SensorDataCollector", "Rotate Left");
//                feedbackText.setText("Rotate left");
            } else {
                Log.d("SensorDataCollector", "Success");
//                feedbackText.setText("Success!");
            }
//            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        if (sensor == mProximitySensor) {
            Log.i(TAG, "Proximity sensor accuracy changed to: " + accuracy);
            // Handle changes in proximity sensor accuracy
        } else if (sensor == mRotationVectorSensor) {
            Log.i(TAG, "Rotation vector sensor accuracy changed to: " + accuracy);
            // Handle changes in rotation vector sensor accuracy
        }
    }


    public interface SensorDataCallback {
        void onSensorDataChanged(float[] values);
    }


}
