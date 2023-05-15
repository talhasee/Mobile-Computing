package com.example.sensors_app;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SensorsDAO {
    @Query("select * from Proximity_sensors")
    List<Proximity_Sensors> getAllSensors();

    @Insert
    void addSensor(Proximity_Sensors sensor);

    @Update
    void UpdateSensor(Proximity_Sensors sensor);

    @Delete
    void DeleteSensor(Proximity_Sensors sensor);
}
