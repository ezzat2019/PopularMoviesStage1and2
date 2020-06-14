package com.example.programmer.popularmoviesstage1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programmer.popularmoviesstage1.adapters.RecycleMoviesAdapter;
import com.example.programmer.popularmoviesstage1.adapters.ReviewsAdapter;
import com.example.programmer.popularmoviesstage1.adapters.TrailersAdapter;
import com.example.programmer.popularmoviesstage1.databinding.ActivityMovieDetoelsStag2Binding;
import com.example.programmer.popularmoviesstage1.entities.FavMovie;
import com.example.programmer.popularmoviesstage1.models.Results;
import com.example.programmer.popularmoviesstage1.models.reviews.Reviews;
import com.example.programmer.popularmoviesstage1.models.reviews.ReviwesResult;
import com.example.programmer.popularmoviesstage1.models.video.VideoTraier;
import com.example.programmer.popularmoviesstage1.retrofit_helper.BaseRetrofit;
import com.example.programmer.popularmoviesstage1.retrofit_helper.ui.MoviePopulerApi;
import com.example.programmer.popularmoviesstage1.view_models.BaseViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetoelsStag2Activity extends AppCompatActivity implements TrailersAdapter.TrailerClick, ReviewsAdapter.ReivewsClick {

    private static boolean isFav = false;
    // ui
    private RecyclerView recTrailer;
    private RecyclerView recReview;
    // var
    private ActivityMovieDetoelsStag2Binding binding;
    private Results results;
    private List<com.example.programmer.popularmoviesstage1.models.video.Results> list;
    private TrailersAdapter adapter;
    private ReviewsAdapter reviewsAdapter;
    private List<ReviwesResult> reviwesResultList;
    private BaseViewModel viewModel;
    private FavMovie favMovie;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detoels_stag2);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detoels_stag2);
        getSupportActionBar().setTitle("Movie Detiels");

        if (getIntent().hasExtra(MainDiscoveryScreen.KEY_OBJ)) {
            results = getIntent().getParcelableExtra(MainDiscoveryScreen.KEY_OBJ);
            favMovie = new FavMovie(results.getPoster_path(), results.getId(), results.getRelease_date(),
                    results.getVote_average(), results.getOverview(), results.getTitle());

            fillAllView();

            isFavMovie();
        } else if (getIntent().hasExtra(MainDiscoveryScreen.KEY_OBJ2)) {
            favMovie = getIntent().getParcelableExtra(MainDiscoveryScreen.KEY_OBJ2);


            fillAllView2();

            isFavMovie();
        } else {
            Toast.makeText(this, "error fetching data :(", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }

    }

    private void isFavMovie() {
        if (preferences.getBoolean(favMovie.getId() + "", false)) {
            binding.btnFav.setText(getString(R.string.fave_on));
        } else {
            binding.btnFav.setText(getString(R.string.fave_off));
        }


    }

    private void fillAllView() {
        preferences = getSharedPreferences("ezzat1", MODE_PRIVATE);
        editor = preferences.edit();

        viewModel = ViewModelProviders.of(this).get(BaseViewModel.class);

        Picasso.with(this).load(RecycleMoviesAdapter.BASE_URL_IMAGE.concat(results.getPoster_path()))
                .placeholder(R.drawable.image_not_available).into(binding.imgMoviePoster2);

        binding.txtTitle.setText(results.getTitle());
        binding.txtOverview.setText(results.getOverview());
        binding.txtVoteAverage.setText(results.getVote_average() + "/10");

        String[] split = results.getRelease_date().split("-");
        binding.txtYear.setText(split[0]);

        fillRecycleTrailer(results.getId());
        fillRecycleReviews(results.getId());

        binding.btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (preferences.getBoolean(favMovie.getId() + "", false)) {
                    editor.putBoolean(favMovie.getId() + "", false);
                    editor.commit();
                    viewModel.getFavMovie().delete(favMovie);
                    Toast.makeText(MovieDetoelsStag2Activity.this, "cleared", Toast.LENGTH_SHORT).show();


                } else {


                    editor.putBoolean(favMovie.getId() + "", true);
                    editor.commit();
                    viewModel.getFavMovie().insert(favMovie);

                    Toast.makeText(MovieDetoelsStag2Activity.this, "saved", Toast.LENGTH_SHORT).show();
                }
                isFavMovie();

            }
        });


    }

    private void fillAllView2() {
        preferences = getSharedPreferences("ezzat1", MODE_PRIVATE);
        editor = preferences.edit();

        viewModel = ViewModelProviders.of(this).get(BaseViewModel.class);

        Picasso.with(this).load(RecycleMoviesAdapter.BASE_URL_IMAGE.concat(favMovie.getPoster_path()))
                .placeholder(R.drawable.image_not_available).into(binding.imgMoviePoster2);

        binding.txtTitle.setText(favMovie.getTitle());
        binding.txtOverview.setText(favMovie.getOverview());
        binding.txtVoteAverage.setText(favMovie.getVote_average() + "/10");

        String[] split = favMovie.getRelease_date().split("-");
        binding.txtYear.setText(split[0]);

        fillRecycleTrailer(favMovie.getId());
        fillRecycleReviews(favMovie.getId());

        binding.btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (preferences.getBoolean(favMovie.getId() + "", false)) {
                    editor.putBoolean(favMovie.getId() + "", false);
                    editor.commit();
                    viewModel.getFavMovie().delete(favMovie);
                    Toast.makeText(MovieDetoelsStag2Activity.this, "cleared", Toast.LENGTH_SHORT).show();


                } else {


                    editor.putBoolean(favMovie.getId() + "", true);
                    editor.commit();
                    viewModel.getFavMovie().insert(favMovie);

                    Toast.makeText(MovieDetoelsStag2Activity.this, "saved", Toast.LENGTH_SHORT).show();
                }
                isFavMovie();

            }
        });


    }


    private void fillRecycleReviews(int id) {
        recReview = findViewById(R.id.rec_review);
        recReview.setHasFixedSize(true);
        recReview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recReview.setNestedScrollingEnabled(false);
        reviewsAdapter = new ReviewsAdapter(this);
        BaseRetrofit.getInstance().getApi().getReviesById(id, MoviePopulerApi.API_KEY)
                .enqueue(new Callback<Reviews>() {
                    @Override
                    public void onResponse(Call<Reviews> call, Response<Reviews> response) {
                        reviwesResultList = new ArrayList<>(response.body().getResults());
                        reviewsAdapter.setList(reviwesResultList);
                        recReview.setAdapter(reviewsAdapter);
                    }

                    @Override
                    public void onFailure(Call<Reviews> call, Throwable t) {

                    }
                });
    }

    private void fillRecycleTrailer(int id) {

        recTrailer = findViewById(R.id.rec_trailer);
        recTrailer.setHasFixedSize(true);
        recTrailer.setNestedScrollingEnabled(false);
        recTrailer.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new TrailersAdapter(this);
        BaseRetrofit.getInstance().getApi().getTrailerById(id, MoviePopulerApi.API_KEY).enqueue(new Callback<VideoTraier>() {
            @Override
            public void onResponse(Call<VideoTraier> call, Response<VideoTraier> response) {
                list = new ArrayList<>(response.body().getResults());
                adapter.setList(list);
                recTrailer.setAdapter(adapter);

            }


            @Override
            public void onFailure(Call<VideoTraier> call, Throwable t) {


            }
        });


    }


    @Override
    public void onClick(int pos) {
        showVideoInYoutube(pos);

    }

    @Override
    public void onClickShare(int pos) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT,
                "Hey check out this trailer at:" + Uri.parse("http://www.youtube.com/watch?v=" + list.get(pos).getKey()));
        intent.setType("text/plain");
        startActivity(intent);
    }

    private void showVideoInYoutube(int pos) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + list.get(pos).getKey()));
        if (appIntent.resolveActivity(getPackageManager()) != null)
            startActivity(appIntent);
        else {
            Intent app = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + list.get(pos).getKey()));

            startActivity(app);
        }

    }


    @Override
    public void onClickonReives(int pos) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(reviwesResultList.get(pos).getUrl()));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();

    }
}
