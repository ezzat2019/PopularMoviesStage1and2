package com.example.programmer.popularmoviesstage1.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.programmer.popularmoviesstage1.Repository.RespMovieApi;
import com.example.programmer.popularmoviesstage1.Repository.RespsFavMovie;
import com.example.programmer.popularmoviesstage1.entities.FavMovie;
import com.example.programmer.popularmoviesstage1.models.PopulerMovie;

import java.util.List;

public class BaseViewModel extends AndroidViewModel {
    private RespMovieApi api;
    private RespsFavMovie favMovie;
    private LiveData<List<FavMovie>> listLiveData;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        api = RespMovieApi.getInstance();
        favMovie = RespsFavMovie.getInstance(application);

    }

    public RespsFavMovie getFavMovie() {
        return favMovie;
    }

    public LiveData<PopulerMovie> getLiveDataPopuler() {
        return api.getLiveDataPopuler();
    }

    public LiveData<PopulerMovie> getLiveDataTrend() {
        return api.getLiveDataTrend();
    }

    public LiveData<List<FavMovie>> getListLiveData() {
        listLiveData = favMovie.getAllMoviesFav();
        return listLiveData;
    }

    public LiveData<Boolean> getMoviesFav(int id) {

        return favMovie.getMoviesFav(id);
    }
}
