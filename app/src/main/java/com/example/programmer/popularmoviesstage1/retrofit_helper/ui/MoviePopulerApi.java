package com.example.programmer.popularmoviesstage1.retrofit_helper.ui;

import com.example.programmer.popularmoviesstage1.models.PopulerMovie;
import com.example.programmer.popularmoviesstage1.models.reviews.Reviews;
import com.example.programmer.popularmoviesstage1.models.video.VideoTraier;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviePopulerApi {
    String API_KEY = "3012115775d0bfc1e30270eb56d072b1";

    @GET("movie/popular")
    Call<PopulerMovie> getAllMovies(@Query("api_key") String key);

    @GET("movie/top_rated")
    Call<PopulerMovie> getAllMoviesTrend(@Query("api_key") String key);

    @GET("movie/{id}/videos")
    Call<VideoTraier> getTrailerById(@Path("id") int id, @Query("api_key") String key);

    @GET("movie/{id}/reviews")
    Call<Reviews> getReviesById(@Path("id") int id, @Query("api_key") String key);


}
