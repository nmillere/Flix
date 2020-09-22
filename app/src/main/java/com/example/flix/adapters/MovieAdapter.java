package com.example.flix.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flix.R;
import com.example.flix.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Movie> movies;
    private final int NORMAL = 0, POPULAR = 1;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (Float.parseFloat(movies.get(position).getRating()) > 7) {
            return POPULAR;
        } else return NORMAL;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == POPULAR) {
            View v1 = inflater.inflate(R.layout.popular_movie, parent, false);
            viewHolder = new ViewHolderPopular(v1);
        } else {
            View v2 = inflater.inflate(R.layout.item_movie, parent, false);
            viewHolder = new ViewHolder(v2);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder.getItemViewType() == POPULAR) {
            ViewHolderPopular vh1 = (ViewHolderPopular) viewHolder;
            configureViewHolderPopular(vh1, position);
        } else {
            ViewHolder vh2 = (ViewHolder) viewHolder;
            configureViewHolder(vh2, position);
        }
    }

    private void configureViewHolder(ViewHolder vh, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position);
        // Get the movie at the passed in position
        Movie movie = movies.get(position);
        // Bind the movie data into the VH
        vh.bind(movie);
    }

    private void configureViewHolderPopular(ViewHolderPopular vh, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position);
        // Get the movie at the passed in position
        Movie movie = movies.get(position);
        // Bind the movie data into the VH
        vh.bind(movie);
    }

//    // Inflate a layout from XML and return the holder
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Log.d("MovieAdapter", "onCreateViewHolder");
//        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
//        return new ViewHolder(movieView);
//    }
//
//    // Populate data into item through holder
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Log.d("MovieAdapter", "onBindViewHolder " + position);
//        // Get the movie at the passed in position
//        Movie movie = movies.get(position);
//        // Bind the movie data into the VH
//        holder.bind(movie);
//    }
//
//    // Return total count of items in list
//    @Override
//    public int getItemCount() {
//        return movies.size();
//    }

//    public class ViewHolder extends RecyclerView.ViewHolder {
//        TextView tvTitle;
//        TextView tvOverview;
//        ImageView ivPoster;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            tvTitle = itemView.findViewById(R.id.tvTitle);
//            tvOverview = itemView.findViewById(R.id.tvOverview);
//            ivPoster = itemView.findViewById(R.id.ivPoster);
//        }
//
//        public void bind(Movie movie) {
//            tvTitle.setText(movie.getTitle());
//            tvOverview.setText(movie.getOverview());
//            String imageUrl;
//            // if phone is in landscape
//            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//                imageUrl = movie.getBackdropPath();
//            }
//            else {
//                imageUrl = movie.getPosterPath();
//            }
//            // then imageUrl = back drop image
//            // else imageUrl = poster image
//
//
//            Glide.with(context).load(imageUrl)
//                    .placeholder(R.drawable.hourglass)
//                    .error(R.drawable.error)
//                    .into(ivPoster);
//        }
//    }
}
