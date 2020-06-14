package com.example.programmer.popularmoviesstage1.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.programmer.popularmoviesstage1.models.PopulerMovie;
import com.example.programmer.popularmoviesstage1.retrofit_helper.BaseRetrofit;
import com.example.programmer.popularmoviesstage1.retrofit_helper.ui.MoviePopulerApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RespMovieApi {
    private static final RespMovieApi ourInstance = new RespMovieApi();
    private MutableLiveData<PopulerMovie> liveDataPopuler;
    private MutableLiveData<PopulerMovie> liveDataTrend;
    private BaseRetrofit retrofit;

    private RespMovieApi() {
        retrofit = BaseRetrofit.getInstance();
        fillPopulerMovie();
        fillTrendMovie();


    }

    public static RespMovieApi getInstance() {
        return ourInstance;
    }

    public LiveData<PopulerMovie> getLiveDataPopuler() {
        return liveDataPopuler;
    }

    public LiveData<PopulerMovie> getLiveDataTrend() {
        return liveDataTrend;
    }

    private void fillTrendMovie() {
        liveDataTrend = new MutableLiveData<>();
        retrofit.getApi().getAllMoviesTrend(MoviePopulerApi.API_KEY)
                .enqueue(new Callback<PopulerMovie>() {
                    @Override
                    public void onResponse(Call<PopulerMovie> call, Response<PopulerMovie> response) {

                        liveDataTrend.postValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<PopulerMovie> call, Throwable t) {

                    }
                });
    }

    private void fillPopulerMovie() {
        liveDataPopuler = new MutableLiveData<>();
        retrofit.getApi().getAllMovies(MoviePopulerApi.API_KEY).enqueue(new Callback<PopulerMovie>() {
            @Override
            public void onResponse(Call<PopulerMovie> call, Response<PopulerMovie> response) {
                liveDataPopuler.postValue(response.body());


            }

            @Override
            public void onFailure(Call<PopulerMovie> call, Throwable t) {


            }
        });

    }
}
