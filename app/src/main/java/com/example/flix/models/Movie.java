package com.example.flix.models;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;

import okhttp3.Headers;

@Parcel
public class Movie {
    public static final String GENRE_URL = "https://api.themoviedb.org/3/genre/movie/list?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String TAG = "Movie";

    int movieId;
    String backdropPath;
    String posterPath;
    String title;
    String overview;
    String rating;
    String releaseDate;
    String genreString;
    ArrayList<String> genreIds;
    HashMap<String, String> genres;

    // empty constructor needed by the Parceler library
    public Movie() {}

    public Movie(JSONObject jsonObject) throws JSONException {
        backdropPath = jsonObject.getString("backdrop_path");
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        rating = jsonObject.getString("vote_average");
        movieId = jsonObject.getInt("id");
        releaseDate = jsonObject.getString("release_date");
        genreIds = buildGenreIds(jsonObject.getJSONArray("genre_ids"));
        genres = new HashMap<>(genreIds.size());

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(GENRE_URL, new JsonHttpResponseHandler() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                try {
                    JSONArray results = json.jsonObject.getJSONArray("genres");
                    for (int i = 0; i < results.length(); i++) {
                        String id = results.getJSONObject(i).getString("id");
                        Log.i(TAG, "Id " + id);
                        String genre = results.getJSONObject(i).getString("name");
                        Log.i(TAG, "Genre " + genre);
                        genres.put(id, genre);
                    }

                    StringJoiner genreJoiner = new StringJoiner(", ");
                    Log.i(TAG, genreIds.get(0));
                    for (int i = 0; i < genreIds.size(); i++) {
                        genreJoiner.add(genres.get(genreIds.get(i)));
                    }
                    genreString = genreJoiner.toString();
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < movieJsonArray.length(); i++) {
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public ArrayList<String> buildGenreIds(JSONArray genre_ids) throws JSONException {
        ArrayList<String> results = new ArrayList<>();
        for (int i = 0; i < genre_ids.length(); i++) {
           results.add(genre_ids.getString(i));
        }
        return results;
    }

    public ArrayList<String> getGenreIds() {
        return genreIds;
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w780/%s", backdropPath);
    }

    public String getPosterPath() {
        // Proper method involves using Configurations API to get all available image sizes
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getRating() {
        return rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getGenreString() {
        return genreString;
    }
}
