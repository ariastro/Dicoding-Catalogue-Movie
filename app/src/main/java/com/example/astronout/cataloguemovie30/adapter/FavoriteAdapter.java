package com.example.astronout.cataloguemovie30.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.astronout.cataloguemovie30.DetailActivity;
import com.example.astronout.cataloguemovie30.R;
import com.example.astronout.cataloguemovie30.model.MovieFavorite;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private LinkedList<MovieFavorite> listFavorites;
    private Activity activity;
    private Context context;

    public FavoriteAdapter(Context context) {
        this.context = context;
    }

    public LinkedList<MovieFavorite> getListFavorite() {
        return listFavorites;
    }

    public void setListFavorite(LinkedList<MovieFavorite> listFavorites) {
        this.listFavorites = listFavorites;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.movieTitle.setText(listFavorites.get(position).getTitle());

        holder.movieDescription.setText(listFavorites.get(position).getOverview());

        //holder.movieRating.setText(listFavorites.get(position).get);

        String poster = "https://image.tmdb.org/t/p/w500";

        String time = listFavorites.get(position).getRelease_date();
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = parser.parse(time);
            SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM d, yyyy");
            String mDateFormat = formatter.format(date);
            holder.movieDate.setText(mDateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Glide.with(context)
                .load(poster + listFavorites.get(position).getPoster())
                .apply(new RequestOptions().placeholder(R.drawable.ic_refresh_black_24dp))
                .into(holder.imgPoster);


        holder.RvMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent(position, v);
            }
        });


    }

    private void intent(int position, View v) {

        Intent i = new Intent(v.getContext(), DetailActivity.class);
        i.putExtra(DetailActivity.EXTRA_ID, listFavorites.get(position).getId());
        i.putExtra(DetailActivity.EXTRA_TITLE, listFavorites.get(position).getTitle());
        i.putExtra(DetailActivity.EXTRA_OVERVIEW, listFavorites.get(position).getOverview());
        i.putExtra(DetailActivity.EXTRA_DATE, listFavorites.get(position).getRelease_date());
        i.putExtra(DetailActivity.EXTRA_POSTER, listFavorites.get(position).getPoster());
        i.putExtra(DetailActivity.IS_FAVORITE, 1);
        i.putExtra(DetailActivity.EXTRA_RATING, listFavorites.get(position).getRating());
        v.getContext().startActivity(i);

    }


    @Override
    public int getItemCount() {
        return getListFavorite().size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder  {
        @BindView(R.id.mov_title)
        TextView movieTitle;
        @BindView(R.id.subtitle) TextView movieDate;
        @BindView(R.id.overview)
        TextView movieDescription;
        @BindView(R.id.img_poster)
        ImageView imgPoster;

        public CardView RvMain;

        public ViewHolder(View v) {
            super(v);
            RvMain = v.findViewById(R.id.movie_card);
            ButterKnife.bind(this, v);
        }
    }
}