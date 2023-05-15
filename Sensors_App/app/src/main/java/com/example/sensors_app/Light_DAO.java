package com.example.sensors_app;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao

public interface Light_DAO {
    @Query("select * from Light_sensors")
    List<Proximity_Sensors> getAllSensors();

    @Insert
    void addSensor(Light_Sensors sensor);

    @Update
    void UpdateSensor(Light_Sensors sensor);

    @Delete
    void DeleteSensor(Light_Sensors sensor);
}
