package com.example.programmer.popularmoviesstage1.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.programmer.popularmoviesstage1.entities.FavMovie;
import com.example.programmer.popularmoviesstage1.ui.FavHelber;

@Database(entities = {FavMovie.class},version = 4,exportSchema = false)
public abstract class BaseRoomDataBase extends RoomDatabase {
    private static BaseRoomDataBase mInstance;
    public static BaseRoomDataBase getInstance(Context context)
    {
        if (mInstance==null)
        {
            synchronized (BaseRoomDataBase.class)
            {
                mInstance= Room.databaseBuilder(context,BaseRoomDataBase.class,"fav4").build();
            }
        }
        return mInstance;
    }
public abstract FavHelber getFavHelber();

}
