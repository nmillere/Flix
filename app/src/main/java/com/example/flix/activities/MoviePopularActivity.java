package com.example.flix.activities;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;

import com.example.flix.R;
import com.example.flix.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.util.Locale;

import okhttp3.Headers;

public class MoviePopularActivity extends YouTubeBaseActivity {

    private static final String YOUTUBE_API_KEY = "AIzaSyCb16MoC01McpJabCV2SDAiy4ZHY5vLR9U";
    private static final String VIDEOS_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private static final String TAG = "MoviePopularActivity";

    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_movie_popular);

        final Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        assert movie != null;

        youTubePlayerView = findViewById(R.id.player);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(Locale.ENGLISH, VIDEOS_URL, movie.getMovieId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    if (results.length() == 0) {
                        return;
                    }
                    String youtubeKey = results.getJSONObject(0).getString("key");
                    Log.d(TAG, youtubeKey);

                    initializeYoutube(youtubeKey, Float.parseFloat(movie.getRating()));
                } catch (JSONException e) {
                    Log.e(TAG, "Failed to parse JSON");
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }

    private void initializeYoutube(final String youtubeKey, final Float rating) {
        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d(TAG, "onInitializationSuccess");
                // do any work here to cue video, play video, etc
                youTubePlayer.loadVideo(youtubeKey);
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d(TAG, "onInitializationFailure");
            }
        });
    }
}