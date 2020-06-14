package com.example.programmer.popularmoviesstage1.ui;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.programmer.popularmoviesstage1.entities.FavMovie;

import java.util.List;

@Dao
public interface FavHelber {
    @Query("select * from fav_items")
    List<FavMovie> getAllFav();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFav(FavMovie favMovie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFav(FavMovie favMovie);

    @Delete
    void deleteFav(FavMovie favMovie);

    @Query("select is_fav from fav_items where id=:id")
    boolean getisFav(int id);
}
