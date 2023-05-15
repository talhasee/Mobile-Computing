package com.example.sensors_app;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface Geo_DAO {
    @Query("select * from Geo_sensors")
    List<Proximity_Sensors> getAllSensors();

    @Insert
    void addSensor(Geo_Sensors sensor);

    @Update
    void UpdateSensor(Geo_Sensors sensor);

    @Delete
    void DeleteSensor(Geo_Sensors sensor);
}
