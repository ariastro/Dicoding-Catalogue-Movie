package com.example.astronout.cataloguemovie30.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.example.astronout.cataloguemovie30.model.Movie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private Context mContext;
    private List<Movie> movieList;

    public static String EXTRA_ID = "extra_id";
    public static String EXTRA_TITLE = "extra_title";
    public static String EXTRA_OVERVIEW = "extra_overview";
    public static String EXTRA_DATE = "extra_time";
    public static String EXTRA_POSTER = "extra_poster";
    public static String EXTRA_RATING = "extra_rating";

    public MoviesAdapter(Context mContext, List<Movie> movieList) {
        this.mContext = mContext;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MoviesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_movie, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.MyViewHolder holder, int position) {
        holder.item_title.setText(movieList.get(position).getmOriginalTitle());
        holder.item_overview.setText(movieList.get(position).getmOverview());
        String vote = Double.toString(movieList.get(position).getmVoteAverage());
        //holder.item_rating .setText(vote);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(movieList.get(position).getmReleaseDate());

            SimpleDateFormat mDateformat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            String release_date = mDateformat.format(date);
            holder.subTitle.setText(release_date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        String poster = "https://image.tmdb.org/t/p/w500" + movieList.get(position).getmPosterPath();

        Glide.with(mContext)
                .load(poster)
                .apply(new RequestOptions().placeholder(R.drawable.ic_refresh_black_24dp))
                .into(holder.item_poster);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void setMovie(List<Movie> movie) {
        this.movieList = movie;
    }

    public List<Movie> getList(){
        return movieList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.img_poster) ImageView item_poster;
        @BindView(R.id.mov_title) TextView item_title;
        @BindView(R.id.overview) TextView item_overview;
        @BindView(R.id.subtitle) TextView subTitle;
//        @BindView(R.id.rating) TextView item_rating;

        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(mContext, DetailActivity.class);
                        intent.putExtra(EXTRA_TITLE, movieList.get(pos).getmOriginalTitle());
                        intent.putExtra(EXTRA_POSTER, movieList.get(pos).getmPosterPath());
                        intent.putExtra(EXTRA_OVERVIEW, movieList.get(pos).getmOverview());
                        intent.putExtra(EXTRA_RATING, Double.toString(movieList.get(pos).getmVoteAverage()));
                        intent.putExtra(EXTRA_DATE, movieList.get(pos).getmReleaseDate());
                        intent.putExtra(EXTRA_ID, movieList.get(pos).getmId());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }

}
