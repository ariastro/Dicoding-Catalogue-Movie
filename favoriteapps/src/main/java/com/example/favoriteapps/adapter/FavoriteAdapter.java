package com.example.favoriteapps.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.favoriteapps.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.favoriteapps.database.DatabaseContract.FavoriteColumns.COLUMN_OVERVIEW;
import static com.example.favoriteapps.database.DatabaseContract.FavoriteColumns.COLUMN_POSTER_PATH;
import static com.example.favoriteapps.database.DatabaseContract.FavoriteColumns.COLUMN_RELEASE_DATE;
import static com.example.favoriteapps.database.DatabaseContract.FavoriteColumns.COLUMN_TITLE;
import static com.example.favoriteapps.database.DatabaseContract.getColumnString;

public class FavoriteAdapter extends CursorAdapter {

    private Context mContex;

    public FavoriteAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        this.mContex = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_movie, parent, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View itemView, Context context, Cursor cursor) {
        if (cursor != null){
            TextView tvTitle = itemView.findViewById(R.id.mov_title);
            TextView tvOverview = itemView.findViewById(R.id.overview);
            ImageView imgMovie = itemView.findViewById(R.id.img_poster);
            TextView tvDate = itemView.findViewById(R.id.date);

            String time = getColumnString(cursor, COLUMN_RELEASE_DATE);
            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = parser.parse(time);
                SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM d, yyyy");
                String mDateFormat = formatter.format(date);
                tvDate.setText(mDateFormat);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            tvTitle.setText(getColumnString(cursor, COLUMN_TITLE));
            tvOverview.setText(getColumnString(cursor, COLUMN_OVERVIEW));
            String url = "http://image.tmdb.org/t/p/w500/";
            Glide.with(context).load(url + getColumnString(cursor, COLUMN_POSTER_PATH)).into(imgMovie);
        }
    }
}