package com.example.steps;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.TextView;

public class SensorWork implements SensorEventListener {

    public float mHeight = 1.7f; // User's height in meters
    public float mWeight = 60f; // User's weight in kilograms
    public float mStrideLength = calculateStrideLength(mHeight, mWeight); // Calculate stride length based on height and weight
    public int mStepCount = 0; // Number of steps taken
    public float[] mPrevAcceleration = new float[3]; // Previous accelerometer reading
    public float[] mPrevMagnetometer = new float[3]; // Previous magnetometer reading
    public float mPrevDirection = 0; // Previous direction of movement
    public float mDisplacement = 0; // Displacement of movement
    public SensorManager mSensorManager;
    public Sensor mAccelerometer;
    public Sensor mMagnetometer;
    public Sensor mRotationVectorSensor;
    public Context mContext;
    public boolean mInitialDirectionSet = false;
    public int initialDirection = 0;
    public String mCurrentDirectionLabel = "";

    public boolean stairs = false, lift = false;


    TextView steps, Direction, Stairs_Lift;
    public SensorWork(Context context) {
        mContext = context;
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mRotationVectorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        steps = (TextView) ((Activity)mContext).findViewById(R.id.steps);
        Direction = (TextView) ((Activity)mContext).findViewById(R.id.direction);
        Stairs_Lift = (TextView) ((Activity)mContext).findViewById(R.id.Stair_Lift);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                mPrevAcceleration = event.values.clone();
                updateAcceleration(event.values);
                detectStepCount(event.values);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                mPrevMagnetometer = event.values.clone();
                detectDirection(event.values);
                if (!mInitialDirectionSet) {
                    float[] rotationMatrix = new float[9];
                    SensorManager.getRotationMatrix(rotationMatrix, null, mPrevAcceleration, mPrevMagnetometer);
                    float[] orientation = new float[3];
                    SensorManager.getOrientation(rotationMatrix, orientation);
                    initialDirection = (int) Math.toDegrees(orientation[0]);
                    mPrevDirection = initialDirection;
                    mInitialDirectionSet = true;
                }
                break;
            default:
                break;
        }

        Direction.setText(mCurrentDirectionLabel);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing
    }

    public void updateAcceleration(float[] values) {
        // Apply rotation matrix to the acceleration values to adjust for device orientation
        float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrix(rotationMatrix, null, mPrevAcceleration, mPrevMagnetometer);
        float[] rotatedValues = new float[3];
        SensorManager.getOrientation(rotationMatrix, rotatedValues);
        float azimuth = (float) Math.toDegrees(rotatedValues[0]);
        float pitch = (float) Math.toDegrees(rotatedValues[1]);
        float roll = (float) Math.toDegrees(rotatedValues[2]);
        values[0] = (float) (values[0] * Math.cos(Math.toRadians(pitch)) +
                values[2] * Math.sin(Math.toRadians(pitch)));
        values[1] = (float) (values[0] * Math.sin(Math.toRadians(roll)) * Math.sin(Math.toRadians(pitch)) +
                values[1] * Math.cos(Math.toRadians(roll)) -
                values[2] * Math.sin(Math.toRadians(roll)) * Math.cos(Math.toRadians(pitch)));
        values[2] = (float) (-values[0] * Math.cos(Math.toRadians(roll)) * Math.sin(Math.toRadians(pitch)) +
                values[1] * Math.sin(Math.toRadians(roll)) +
                values[2] * Math.cos(Math.toRadians(roll)) * Math.cos(Math.toRadians(pitch)));

        // Get yaw angle and apply rotation around y-axis
        float yaw = (float) Math.toDegrees(Math.atan2(rotationMatrix[3], rotationMatrix[0]));
        values[0] = (float) (values[0] * Math.cos(Math.toRadians(yaw)) -
                values[1] * Math.sin(Math.toRadians(yaw)));
        values[1] = (float) (values[0] * Math.sin(Math.toRadians(yaw)) +
                values[1] * Math.cos(Math.toRadians(yaw)));


        float delx, dely, delz;
        delx = Math.abs(values[0] - mPrevAcceleration[0]);
        dely = Math.abs(values[1] - mPrevAcceleration[1]);
        delz = Math.abs(values[2] - mPrevAcceleration[2]);

        // Update previous acceleration reading
        mPrevAcceleration = values;


        // Detect step count
//        detectStepCount(values);

        // Calculate change in y-direction
        float deltaY = values[1] - mPrevAcceleration[1];

        if(delz > 2 && dely > 0.4){
            Stairs_Lift.setText("Using stairs");
        }
        else if(delx < 4 && dely < 4 && delz > 0.5){
            Stairs_Lift.setText("Using Lift");
        }
        // Only update displacement if there's a significant change in the y-direction
        if (Math.abs(deltaY) > 0.1) {
            mDisplacement += mStrideLength * mStepCount;
        }
    }


    public void updateMagnetometer(float[] values) {
        // Update previous magnetometer reading
        mPrevMagnetometer = values;
        // Detect direction of movement
        detectDirection(values);
    }

    public void updateRotationVector(float[] values) {
        // Apply rotation matrix to the rotation vector to adjust for device orientation
        float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrixFromVector(rotationMatrix, values);
        float[] orientationAngles = new float[3];
        SensorManager.getOrientation(rotationMatrix, orientationAngles);

        // Update previous direction of movement
        mPrevDirection = calculateDirection(orientationAngles);

        // Log the direction of movement for debugging purposes
        Log.d("SensorValues", "Direction: " + mPrevDirection);
    }


    public void detectStepCount(float[] values) {
        // Calculate the norm of the acceleration vector
        float norm = (float) Math.sqrt(Math.pow(values[0], 2) + Math.pow(values[1], 2) + Math.pow(values[2], 2));

        // Detect a step if the norm of the acceleration vector exceeds a threshold
        if (norm > 16) {
            mStepCount++;
            Log.d("SensorValues", "Step count: " + mStepCount);
            //Steps display
            steps.setText(Integer.toString(mStepCount));
        }
    }

//    public void detectDirection(float[] values) {
//        // Calculate the direction of the movement using the magnetometer and previous direction
//        float[] rotationMatrix = new float[9];
//        SensorManager.getRotationMatrix(rotationMatrix, null, mPrevAcceleration, values);
//        float[] orientation = new float[3];
//        SensorManager.getOrientation(rotationMatrix, orientation);
//        float direction = (float) Math.toDegrees(orientation[0]);
//        direction = (direction + 360) % 360;
//
//        // Calculate the difference between the current and previous direction
//        float diff = Math.abs(direction - mPrevDirection);
//
//        // If the difference is greater than 180 degrees, subtract 360 degrees
//        if (diff > 180) {
//            diff = 360 - diff;
//        }
//
//        // Determine the direction of the movement (left or right) based on the reference direction
//        String movementDirection = "";
//        if (diff > 0 && diff < 180) {
//            if (direction > initialDirection ) {
//                movementDirection = "right";
//                Direction.setText("Moving in "+movementDirection+" direction");
//            } else {
//                movementDirection = "left";
//                Direction.setText("Moving in "+movementDirection+" direction");
//
//            }
//        }
//
//        // Update previous direction of movement
//        mPrevDirection = direction;
//
//        // Calculate displacement of movement based on direction and stride length
//        mDisplacement += mStrideLength * (diff / 360f);
//
//        // Log the displacement and direction of movement for debugging purposes
//        Log.d("SensorValues", "Displacement: " + mDisplacement + ", Direction: " + movementDirection);
//    }
    public void detectDirection(float[] magnetometerValues) {
        float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrix(rotationMatrix, null, mPrevAcceleration, magnetometerValues);
        float[] orientationAngles = new float[3];
        SensorManager.getOrientation(rotationMatrix, orientationAngles);

        float azimuthInRadians = orientationAngles[0];
        float azimuthInDegrees = (float) Math.toDegrees(azimuthInRadians);
        if (azimuthInDegrees < 0) {
            azimuthInDegrees += 360;
        }

        String directionLabel;
        if (azimuthInDegrees >= 337.5 || azimuthInDegrees < 22.5) {
            directionLabel = "Moving in 'NORTH' direction";
        } else if (azimuthInDegrees >= 22.5 && azimuthInDegrees < 67.5) {
            directionLabel = "Moving in 'NORTH EAST ' direction";
        } else if (azimuthInDegrees >= 67.5 && azimuthInDegrees < 112.5) {
            directionLabel = "Moving in 'EAST' direction";
        } else if (azimuthInDegrees >= 112.5 && azimuthInDegrees < 157.5) {
            directionLabel = "Moving in SOUTH EAST direction";
        } else if (azimuthInDegrees >= 157.5 && azimuthInDegrees < 202.5) {
            directionLabel = "Moving in SOUTH direction";
        } else if (azimuthInDegrees >= 202.5 && azimuthInDegrees < 247.5) {
            directionLabel = "Moving in SOUTH WEST direction";
        } else if (azimuthInDegrees >= 247.5 && azimuthInDegrees < 292.5) {
            directionLabel = "Moving in WEST direction";
        } else {
            directionLabel = "Moving in NORTH WEST direction";
        }

        mCurrentDirectionLabel = directionLabel;
    }



    public static float calculateStrideLength(float height, float weight) {
        // Calculate stride length based on height and weight
        float strideLength = (float) ((0.415 * height) + (0.38 * weight) - 0.41);
        return strideLength;
    }

    public static float calculateDirection(float[] values) {
        // Calculate the direction of the movement using the accelerometer and magnetometer
        float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrix(rotationMatrix, null, values, null);
        float[] orientation = new float[3];
        SensorManager.getOrientation(rotationMatrix, orientation);
        float direction = (float) Math.toDegrees(orientation[0]);
        direction = (direction + 360) % 360;
        return direction;
    }

    public void start() {
        // Register the sensor listeners
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mRotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop() {
        // Unregister the sensor listeners
        mSensorManager.unregisterListener(this, mAccelerometer);
        mSensorManager.unregisterListener(this, mMagnetometer);
        mSensorManager.unregisterListener(this, mRotationVectorSensor);
    }

}

