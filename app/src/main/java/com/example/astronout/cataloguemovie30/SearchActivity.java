package com.example.astronout.cataloguemovie30;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.astronout.cataloguemovie30.adapter.MoviesAdapter;
import com.example.astronout.cataloguemovie30.api.Client;
import com.example.astronout.cataloguemovie30.api.Service;
import com.example.astronout.cataloguemovie30.model.Movie;
import com.example.astronout.cataloguemovie30.model.MoviesResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.astronout.cataloguemovie30.MainActivity.INTENT_TAG;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view_search)
    RecyclerView recyclerView;


    @BindView(R.id.search_content)
    SwipeRefreshLayout swipeContainer;

    //ProgressDialog pd;
    MoviesAdapter adapter;
    List<Movie> movieList;
    Movie movieResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        movieList = new ArrayList<>();
        movieResult = new Movie();

        if(getIntent() != null){
            if(getIntent().getStringExtra(INTENT_TAG).equals("search")){
                if(savedInstanceState!=null){
                    ArrayList<Movie> list;
                    list = savedInstanceState.getParcelableArrayList("search_movie");
                    adapter.setMovie(list);
                    recyclerView.setAdapter(adapter);
                }else{
                    String q = getIntent().getStringExtra("EXTRA_SEARCH");
                    initViews();
                    loadJSON(q);
                }
            }
        }
    }

    private void initViews(){
//        pd = new ProgressDialog(this);
//        pd.setMessage(getString(R.string.fetching_movies));
//        pd.setCancelable(false);
//        pd.show();

        adapter = new MoviesAdapter(this, movieList);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void loadJSON(final String q){
        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()){
                Toast.makeText(this, "Please get your API key first", Toast.LENGTH_SHORT).show();
                //pd.dismiss();
                return;
            }

            Client Client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<MoviesResponse> call = apiService.getMovieBySearch(q, BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    List<Movie> movies = response.body().getmResults();
                    recyclerView.setAdapter(new MoviesAdapter(getApplicationContext(), movies));
                    recyclerView.smoothScrollToPosition(0);
                    adapter.setMovie(movieList);
                    if (swipeContainer.isRefreshing()){
                        swipeContainer.setRefreshing(false);
                    }
                    //pd.dismiss();
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(SearchActivity.this, getString(R.string.aw_snap), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(SearchActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("search_movie", new ArrayList<>(adapter.getList()));
    }

}