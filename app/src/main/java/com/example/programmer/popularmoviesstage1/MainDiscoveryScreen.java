package com.example.programmer.popularmoviesstage1;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programmer.popularmoviesstage1.adapters.RecycleFavAdapter;
import com.example.programmer.popularmoviesstage1.adapters.RecycleMoviesAdapter;
import com.example.programmer.popularmoviesstage1.entities.FavMovie;
import com.example.programmer.popularmoviesstage1.models.PopulerMovie;
import com.example.programmer.popularmoviesstage1.models.Results;
import com.example.programmer.popularmoviesstage1.retrofit_helper.BaseRetrofit;
import com.example.programmer.popularmoviesstage1.view_models.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainDiscoveryScreen extends AppCompatActivity implements RecycleMoviesAdapter.OnItemClick, RecycleFavAdapter.OnItemClick {

    public static final String KEY_OBJ = "obj";
    public static final String KEY_OBJ2 = "obj2";
    private List<Results> results;
    private List<FavMovie> resultFav;


    // ui
    private RecyclerView recMovies;
    private ProgressBar progressBar;


    // var
    private BaseRetrofit baseRetrofit;
    private RecycleMoviesAdapter adapter;
    private BaseViewModel viewModel;
    private RecycleFavAdapter favAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_discovery_screen);

        checkInternet();
    }


    public void checkInternet() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (manager.getActiveNetworkInfo() == null) {
            showDialog();
        } else {
            setupRecycleMovie();

            setupProgessBar();

            buildViewModel();

            fillRecycleView();
        }

    }

    private void buildViewModel() {
        viewModel = ViewModelProviders.of(this).get(BaseViewModel.class);

    }

    private void showDialog() {
        new AlertDialog.Builder(MainDiscoveryScreen.this).setTitle("No Internet Connection")
                .setPositiveButton("try again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        checkInternet();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).setMessage("turn on internet connection and pressed try again").create().show();
    }


    private void setupProgessBar() {
        progressBar = findViewById(R.id.progress_wait);
        showProgess();
    }

    private void showProgess() {
        recMovies.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgess() {
        recMovies.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }


    private void fillRecycleView() {
        favAdapter = new RecycleFavAdapter(this, this);

        baseRetrofit = BaseRetrofit.getInstance();
        adapter = new RecycleMoviesAdapter(MainDiscoveryScreen.this, this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        fillPopulerData();

    }

    public void fillPopulerData() {


        viewModel.getLiveDataPopuler().observe(this, new Observer<PopulerMovie>() {
            @Override
            public void onChanged(PopulerMovie populerMovie) {
                results = new ArrayList<>(populerMovie.getResults());
                adapter.setList(results);
                recMovies.setAdapter(adapter);
                hideProgess();
            }
        });

    }

    void fillTerndData() {
        viewModel.getLiveDataTrend().observe(this, new Observer<PopulerMovie>() {
            @Override
            public void onChanged(PopulerMovie populerMovie) {
                results = new ArrayList<>(populerMovie.getResults());
                adapter.setList(results);
                recMovies.setAdapter(adapter);
                hideProgess();
            }
        });

    }

    void fillFavData() {
        viewModel.getListLiveData().observe(this, new Observer<List<FavMovie>>() {
            @Override
            public void onChanged(List<FavMovie> favMovies) {
                resultFav = new ArrayList<>(favMovies);
                favAdapter.setList(resultFav);
                recMovies.setAdapter(favAdapter);
                hideProgess();
            }
        });

    }

    private void setupRecycleMovie() {
        recMovies = findViewById(R.id.rec_movie_poster);
        recMovies.setHasFixedSize(true);
        recMovies.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

    }

    @Override
    public void onClick(int pos) {
        if (results != null && !results.isEmpty()) {

            Intent intent = new Intent(getApplicationContext(), MovieDetoelsStag2Activity.class);
            Results result = results.get(pos);
            intent.putExtra(KEY_OBJ, result);

            startActivity(intent);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sort_top_trend:
                showProgess();
                fillTerndData();
                return true;

            case R.id.menu_sort_populer:
                showProgess();
                fillPopulerData();
                return true;

            case R.id.menu_sort_fav:
                showProgess();
                fillFavData();

                return true;


            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickFav(int pos) {
        if (resultFav != null && !resultFav.isEmpty()) {

            Intent intent = new Intent(getApplicationContext(), MovieDetoelsStag2Activity.class);
            FavMovie result = resultFav.get(pos);
            intent.putExtra(KEY_OBJ2, result);

            startActivity(intent);
        }

    }
}
