package com.example.astronout.cataloguemovie30;


import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment {

    private MoviesAdapter adapter;
    private List<Movie> movieList;
    ProgressDialog pd;
    @BindView(R.id.recycler_view_now_playing)
    RecyclerView recyclerView;
    @BindView(R.id.now_playing_content)
    SwipeRefreshLayout swipeContainer;
    public static final String LOG_TAG = MoviesAdapter.class.getName();
    private Cursor list;


    public NowPlayingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_now_playing, container, false);

        ButterKnife.bind(this, v);

        initViews();

        if(savedInstanceState!=null){
            ArrayList<Movie> list;
            list = savedInstanceState.getParcelableArrayList("now_movie");
            adapter.setMovie(list);
            recyclerView.setAdapter(adapter);
            pd.dismiss();
        }else{
            loadJSON();
        }

        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initViews();
                Toast.makeText(getActivity(), getString(R.string.movie_refreshed), Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    private void initViews(){
        pd = new ProgressDialog(getActivity());
        pd.setMessage(getString(R.string.fetching_movies));
        pd.setCancelable(false);
        pd.show();

        movieList = new ArrayList<>();
        adapter = new MoviesAdapter(getActivity(), movieList);

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }else {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        loadJSON();

    }

    private void loadJSON(){
        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()){
                Toast.makeText(getActivity(), "Please get your API key first", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                return;
            }

            Client Client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<MoviesResponse> call = apiService.getNowPlayingMovie(BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    List<Movie> movies = response.body().getmResults();
                    recyclerView.setAdapter(new MoviesAdapter(getActivity(), movies));
                    recyclerView.smoothScrollToPosition(0);
                    adapter.setMovie(movies);
                    if (swipeContainer.isRefreshing()){
                        swipeContainer.setRefreshing(false);
                    }
                    pd.dismiss();
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    //Toast.makeText(getActivity(), getString(R.string.aw_snap), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("now_movie", new ArrayList<>(adapter.getList()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null){
            ArrayList<Movie> list;
            list = savedInstanceState.getParcelableArrayList("now_movie");
            adapter.setMovie(list);
            recyclerView.setAdapter(adapter);
            pd.dismiss();
        }
    }
}
