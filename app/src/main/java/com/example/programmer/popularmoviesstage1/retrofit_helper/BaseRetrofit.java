package com.example.programmer.popularmoviesstage1.retrofit_helper;

import com.example.programmer.popularmoviesstage1.retrofit_helper.ui.MoviePopulerApi;
import com.github.leonardoxh.livedatacalladapter.LiveDataCallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseRetrofit {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final BaseRetrofit ourInstance = new BaseRetrofit();
    private MoviePopulerApi api;


    private BaseRetrofit() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(LiveDataCallAdapterFactory.create())

                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(MoviePopulerApi.class);
    }

    public static BaseRetrofit getInstance() {
        return ourInstance;
    }

    public MoviePopulerApi getApi() {
        return api;
    }
}
