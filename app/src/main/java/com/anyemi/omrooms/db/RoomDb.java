package com.anyemi.omrooms.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

@Database(entities = {RoomBooking.class}, version = 5,exportSchema = false)
public abstract class RoomDb extends RoomDatabase {

    private static final String LOG_TAG = RoomDb.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "order.db";
    private static RoomDb sInstance;

    public static RoomDb getsInstance(Context context){
        if(sInstance == null){
            synchronized (LOCK){
                Log.d(LOG_TAG,"Creating new Database Instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        RoomDb.class,RoomDb.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG,"getting the Database Instance");
        return sInstance;
    }

    public abstract BookingDao bookingDao();

}
