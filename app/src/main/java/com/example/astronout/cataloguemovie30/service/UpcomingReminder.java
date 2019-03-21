package com.example.astronout.cataloguemovie30.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.astronout.cataloguemovie30.BuildConfig;
import com.example.astronout.cataloguemovie30.DetailActivity;
import com.example.astronout.cataloguemovie30.R;
import com.example.astronout.cataloguemovie30.api.Client;
import com.example.astronout.cataloguemovie30.api.Service;
import com.example.astronout.cataloguemovie30.model.Movie;
import com.example.astronout.cataloguemovie30.model.MoviesResponse;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingReminder extends GcmTaskService {

    public static String TAG_TASK_UPCOMING = "upcoming movies";

    @Override
    public int onRunTask(TaskParams taskParams) {
        int result = 0;
        if (taskParams.getTag().equals(TAG_TASK_UPCOMING)) {
            loadJSON();
            result = GcmNetworkManager.RESULT_SUCCESS;
        }

        return result;
    }

    private void loadJSON(){
        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()){
                Toast.makeText(getApplicationContext(), "Please get your API key first", Toast.LENGTH_SHORT).show();
                return;
            }

            Client Client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<MoviesResponse> call = apiService.getUpcomingMovie(BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
//                final List<MovieModel> movies = response.body().getResults();
                    if (response.isSuccessful()) {
                        List<Movie> items = response.body().getmResults();
                        int index = new Random().nextInt(items.size());

                        Movie item = items.get(index);
                        String title = items.get(index).getmTitle();
                        String message = items.get(index).getmOverview();
                        String time = items.get(index).getmReleaseDate();
                        String poster = items.get(index).getmPosterPath();
                        int notifId = 200;

                        showNotification(getApplicationContext(), title, message, notifId, item);

                    } else loadFailed();
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                }
            });
        }catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFailed() {
        Toast.makeText(this, R.string.error_load_failed, Toast.LENGTH_SHORT).show();
    }

    private void showNotification(Context context, String title, String message, int notifId, Movie item) {
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        String time = item.getmReleaseDate();
        String poster = item.getmPosterPath();
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_TITLE, title);
        intent.putExtra(DetailActivity.EXTRA_OVERVIEW, message);
        intent.putExtra(DetailActivity.EXTRA_DATE, time);
        intent.putExtra(DetailActivity.EXTRA_POSTER, poster);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.movie_icon)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        notificationManagerCompat.notify(notifId, builder.build());
    }
}