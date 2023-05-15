package com.example.sensors_app;



import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Proximity_sensors")
public class Proximity_Sensors {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "sensor_value")
    private String sensor_value;

    Proximity_Sensors(int id, String value){
        this.id = id;
        this.sensor_value = value;
    }

    Proximity_Sensors(String value){
        this.sensor_value = value;
    }
    Proximity_Sensors(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getSensor_value() {
        return sensor_value;
    }

    public void setSensor_value(String sensor_value) {
        this.sensor_value = sensor_value;
    }
}
