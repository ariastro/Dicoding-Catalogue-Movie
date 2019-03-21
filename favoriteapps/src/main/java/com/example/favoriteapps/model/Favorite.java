package com.example.favoriteapps.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.favoriteapps.database.DatabaseContract;

import static com.example.favoriteapps.database.DatabaseContract.getColumnInt;
import static com.example.favoriteapps.database.DatabaseContract.getColumnString;

public class Favorite implements Parcelable {
    private int id;
    private String title;
    private String release_date;
    private String overview;
    private String poster;

    public Favorite(Parcel in) {
        title = in.readString();
        release_date = in.readString();
        overview = in.readString();
        poster = in.readString();
        id= in.readInt();
    }

    public Favorite(Cursor cursor){
        this.id = getColumnInt(cursor, DatabaseContract.FavoriteColumns._ID);
        this.title = getColumnString(cursor, DatabaseContract.FavoriteColumns.COLUMN_TITLE);
        this.overview = getColumnString(cursor, DatabaseContract.FavoriteColumns.COLUMN_OVERVIEW);
        this.release_date = getColumnString(cursor, DatabaseContract.FavoriteColumns.COLUMN_RELEASE_DATE);
        this.poster = getColumnString(cursor, DatabaseContract.FavoriteColumns.COLUMN_POSTER_PATH);
    }

    public static final Parcelable.Creator<Favorite> CREATOR = new Parcelable.Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };

    public Favorite() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static Parcelable.Creator<Favorite> getCREATOR() {
        return CREATOR;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(release_date);
        dest.writeString(overview);
        dest.writeString(poster);
        dest.writeInt(id);
    }
}

