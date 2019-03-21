package com.example.astronout.cataloguemovie30;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.astronout.cataloguemovie30.data.FavoriteHelper;
import com.example.astronout.cataloguemovie30.model.MovieFavorite;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static String EXTRA_ID = "extra_id";
    public static String EXTRA_TITLE = "extra_title";
    public static String EXTRA_OVERVIEW = "extra_overview";
    public static String EXTRA_DATE = "extra_time";
    public static String EXTRA_POSTER = "extra_poster";
    public static String IS_FAVORITE = "is_favorite";
    public static String EXTRA_RATING = "extra_rating";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.image_header)
    ImageView imagePoster;
//    @BindView(R.id.movie_title)
//    TextView movieTitle;
    @BindView(R.id.tv_movie_overview)
    TextView movieOverview;
    @BindView(R.id.tv_movie_rating)
    TextView movieRating;
    @BindView(R.id.tv_movie_release_date)
    TextView movieReleaseDate;
    @BindView(R.id.btn_favorite)
    Button btnFavorite;

    private FavoriteHelper favoriteHelper;
    private boolean isFavorite = false;
    private int favorite;
    private final AppCompatActivity activity = DetailActivity.this;
    private SQLiteDatabase mDb;

    String imageMovie, mTitle, mOverview, mRating, mReleaseDate, mId;
    int movie_id;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

//        initCollapsingToolbar();

        Intent intentThatStartThisActivity = getIntent();
        if (intentThatStartThisActivity.hasExtra(EXTRA_TITLE)) {
            imageMovie = getIntent().getExtras().getString(EXTRA_POSTER);
            mTitle = getIntent().getExtras().getString(EXTRA_TITLE);
            mOverview = getIntent().getExtras().getString(EXTRA_OVERVIEW);
            mRating = getIntent().getExtras().getString(EXTRA_RATING);
            mReleaseDate = getIntent().getExtras().getString(EXTRA_DATE);
            mId = getIntent().getExtras().getString(EXTRA_ID);

            String poster = "https://image.tmdb.org/t/p/w500" + imageMovie;

            Glide.with(this)
                    .load(poster)
                    .apply(new RequestOptions().placeholder(R.drawable.ic_refresh_black_24dp))
                    .into(imagePoster);

            //movieTitle.setText(mTitle);
            movieOverview.setText(mOverview);
            movieRating.setText(mRating);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = dateFormat.parse(mReleaseDate);

                SimpleDateFormat mDateformat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
                String release_date = mDateformat.format(date);
                movieReleaseDate.setText(release_date);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            ((CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar)).setTitle(mTitle);
        } else {
            Toast.makeText(this, "No Api Data Found", Toast.LENGTH_SHORT).show();
        }

        favoriteHelper = new FavoriteHelper(this);
        favoriteHelper.open();

        favorite = getIntent().getIntExtra(IS_FAVORITE, 0);
        if (favorite == 1) {
            isFavorite = true;
            btnFavorite.setText(getString(R.string.delete_favorite));
        }

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFavorite) {
                    addFavorite();
                    Toast.makeText(DetailActivity.this, "Added to Favorite", Toast.LENGTH_LONG).show();
                } else {
                    removeFavorite();
                    Toast.makeText(DetailActivity.this, "Removed from Favorite", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void addFavorite() {
        MovieFavorite favorites = new MovieFavorite();
        favorites.setTitle(getIntent().getStringExtra(EXTRA_TITLE));
        favorites.setOverview(getIntent().getStringExtra(EXTRA_OVERVIEW));
        favorites.setRelease_date(getIntent().getStringExtra(EXTRA_DATE));
        favorites.setPoster(getIntent().getStringExtra(EXTRA_POSTER));
        favoriteHelper.insert(favorites);
    }

    private void removeFavorite(){
        favoriteHelper.delete(getIntent().getIntExtra(EXTRA_ID, 0));
    }
}
