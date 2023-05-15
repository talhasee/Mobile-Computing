package com.example.sensors_app;



import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Proximity_Sensors.class, Light_Sensors.class, Geo_Sensors.class}, exportSchema = false, version = 4)
public abstract class DataBaseHelper extends RoomDatabase {
    public static final String DB_NAME = "sensorsdb";
    public static DataBaseHelper instance;
    public static synchronized DataBaseHelper getDB(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context, DataBaseHelper.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract SensorsDAO Proxy_sensorsDAO();
    public abstract Light_DAO Light_sensorsDAO();
    public abstract Geo_DAO Geo_sensorsDAO();
}
