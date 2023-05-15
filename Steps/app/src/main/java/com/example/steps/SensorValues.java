package com.example.steps;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


public class SensorValues implements SensorEventListener{
    private float mHeight = 1.7f; // User's height in meters
    private float mWeight = 60f; // User's weight in kilograms
    private float mStrideLength = calculateStrideLength(mHeight, mWeight); // Calculate stride length based on height and weight
    private int mStepCount = 0; // Number of steps taken
    private float[] mPrevAcceleration = new float[3]; // Previous accelerometer reading
    private float[] mPrevMagnetometer = new float[3]; // Previous magnetometer reading
    private float mPrevDirection = 0; // Previous direction of movement
    private float mDisplacement = 0; // Displacement of movement
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mMagnetometer;
    private Context mContext;

    public SensorValues(Context context) {
        mContext = context;
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == mAccelerometer) {
            updateAcceleration(event.values);
        } else if (event.sensor == mMagnetometer) {
            updateMagnetometer(event.values);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void updateAcceleration(float[] values) {
        // Update previous acceleration reading
        mPrevAcceleration = values;
        // Detect step count and update displacement
        detectStepCount(values);
        // Calculate displacement of movement
        mDisplacement += mStrideLength * mStepCount;
    }

    private void updateMagnetometer(float[] values) {
        // Update previous magnetometer reading
        mPrevMagnetometer = values;
        // Detect direction of movement
        detectDirection(values);
    }

    private float calculateStrideLength(float height, float weight) {
        // Calculate stride length based on height and weight
        float strideLength = 0.415f * height - 0.0022f * (height * height) + 0.221f * weight - 0.0114f * (weight * height) - 0.00269f * (weight * weight);
        return strideLength;
    }

    private void detectStepCount(float[] values) {
        // Calculate magnitude of acceleration vector
        float magnitude = (float) Math.sqrt(values[0] * values[0] + values[1] * values[1] * values[2] * values[2]);
    // Calculate difference between current and previous magnitude of acceleration vector
        float deltaMagnitude = magnitude - (float) Math.sqrt(mPrevAcceleration[0] * mPrevAcceleration[0] + mPrevAcceleration[1] * mPrevAcceleration[1] + mPrevAcceleration[2] * mPrevAcceleration[2]);
    // Check if the difference in magnitude is above a certain threshold to detect a step
        if (deltaMagnitude > 3) {
            mStepCount++;
        }
    }
    private void detectDirection(float[] values) {
        // Calculate orientation matrix from current magnetometer and accelerometer readings
        float[] rotationMatrix = new float[9];
        float[] inclinationMatrix = new float[9];
        boolean success = SensorManager.getRotationMatrix(rotationMatrix, inclinationMatrix, mPrevAcceleration, mPrevMagnetometer);
        if (success) {
        // Calculate orientation angles from rotation matrix
            float[] orientationAngles = new float[3];
            SensorManager.getOrientation(rotationMatrix, orientationAngles);
        // Convert orientation angle to degrees
            float direction = (float) Math.toDegrees(orientationAngles[0]);
        // Calculate change in direction of movement
            float deltaDirection = direction - mPrevDirection;
        // Update previous direction of movement
            mPrevDirection = direction;
        // Update displacement of movement based on direction
            mDisplacement += Math.sin(Math.toRadians(deltaDirection)) * mStrideLength * mStepCount;
        }
    }



}
