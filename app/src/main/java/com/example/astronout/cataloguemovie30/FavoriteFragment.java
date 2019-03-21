package com.example.astronout.cataloguemovie30;


import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.astronout.cataloguemovie30.adapter.FavoriteAdapter;
import com.example.astronout.cataloguemovie30.adapter.MoviesAdapter;
import com.example.astronout.cataloguemovie30.data.FavoriteHelper;
import com.example.astronout.cataloguemovie30.model.MovieFavorite;

import java.util.ArrayList;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    @BindView(R.id.recycler_view_favorite)
    RecyclerView rvFavorite;
    @BindView(R.id.favorite_content)
    SwipeRefreshLayout swipeContainer;

    private LinkedList<MovieFavorite> list;
    private FavoriteHelper favoriteHelper;
    private FavoriteAdapter favoriteAdapter;

    private FavoriteFragment activity = FavoriteFragment.this;

    public static final String LOG_TAG = MoviesAdapter.class.getName();


    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    private class LoadDB extends AsyncTask<Void, Void, ArrayList<MovieFavorite>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (list.size() > 0) {
                list.clear();
            }

        }

        @Override
        protected void onPostExecute(ArrayList<MovieFavorite> favorites) {
            super.onPostExecute(favorites);
            list.addAll(favorites);
            favoriteAdapter.setListFavorite(list);
            favoriteAdapter.notifyDataSetChanged();

            if (list.size() == 0) {
                Toast.makeText(getActivity(), "No Favorite Movie", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected ArrayList<MovieFavorite> doInBackground(Void... voids) {
            return favoriteHelper.query();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (favoriteHelper != null) {
            favoriteHelper.close();
        }
    }

    @Override
    public void onResume() {

        rvFavorite.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            rvFavorite.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            rvFavorite.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        }
        rvFavorite.setHasFixedSize(true);

        favoriteHelper = new FavoriteHelper(getActivity());
        favoriteHelper.open();

        list = new LinkedList<>();

        favoriteAdapter = new FavoriteAdapter(getActivity());
        favoriteAdapter.setListFavorite(list);
        rvFavorite.setAdapter(favoriteAdapter);
        new LoadDB().execute();
        super.onResume();
    }
}
