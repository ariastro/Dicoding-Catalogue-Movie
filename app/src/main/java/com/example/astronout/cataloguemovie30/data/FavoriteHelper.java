package com.example.astronout.cataloguemovie30.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.astronout.cataloguemovie30.model.MovieFavorite;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.astronout.cataloguemovie30.data.FavoriteContract.FavoriteEntry.COLUMN_OVERVIEW;
import static com.example.astronout.cataloguemovie30.data.FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH;
import static com.example.astronout.cataloguemovie30.data.FavoriteContract.FavoriteEntry.COLUMN_RELEASE_DATE;
import static com.example.astronout.cataloguemovie30.data.FavoriteContract.FavoriteEntry.COLUMN_TITLE;
import static com.example.astronout.cataloguemovie30.data.FavoriteContract.FavoriteEntry.TABLE_NAME;

public class FavoriteHelper {
    private static String DATABASE_TABLE = TABLE_NAME;
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public FavoriteHelper(Context context){
        this.context = context;
    }

    public FavoriteHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    public ArrayList<MovieFavorite> query(){
        ArrayList<MovieFavorite> arrayList = new ArrayList<MovieFavorite>();
        Cursor cursor = database.query(DATABASE_TABLE,null,null,null,null,null,_ID +" DESC",null);
        cursor.moveToFirst();
        MovieFavorite movieFavorite;
        if (cursor.getCount()>0) {
            do {

                movieFavorite = new MovieFavorite();
                movieFavorite.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movieFavorite.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                movieFavorite.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OVERVIEW)));
                movieFavorite.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RELEASE_DATE)));
                movieFavorite.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POSTER_PATH)));
                arrayList.add(movieFavorite);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }


    public long insert(MovieFavorite note){
        ContentValues initialValues =  new ContentValues();
        initialValues.put(COLUMN_TITLE, note.getTitle());
        initialValues.put(COLUMN_OVERVIEW, note.getOverview());
        initialValues.put(COLUMN_RELEASE_DATE, note.getRelease_date());
        initialValues.put(COLUMN_POSTER_PATH, note.getPoster());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int delete(int id){
        return database.delete(TABLE_NAME, _ID + " = '"+id+"'", null);
    }


    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null
                ,_ID + " = ?"
                ,new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }
    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null
                ,_ID + " DESC");
    }
    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);
    }

    public int updateProvider(String id,ContentValues values){
        return database.update(DATABASE_TABLE,values,_ID +" = ?",new String[]{id} );
    }


    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,_ID + " = ?", new String[]{id});
    }
}