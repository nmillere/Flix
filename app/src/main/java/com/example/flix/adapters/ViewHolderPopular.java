package com.example.flix.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flix.R;
import com.example.flix.models.Movie;

public class ViewHolderPopular extends RecyclerView.ViewHolder {
    Context context;
    TextView tvTitle;
    TextView tvOverview;
    TextView tvRating;
    ImageView ivPoster;

    public ViewHolderPopular(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvOverview = itemView.findViewById(R.id.tvOverview);
        tvRating = itemView.findViewById(R.id.tvRating);
        ivPoster = itemView.findViewById(R.id.ivPoster);
    }

    public void bind(Movie movie) {
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        tvRating.setText(movie.getRating());
        String imageUrl;
        // if phone is in landscape
        imageUrl = movie.getBackdropPath();
        // then imageUrl = back drop image
        // else imageUrl = poster image


        Glide.with(context).load(imageUrl)
                .placeholder(R.drawable.hourglass)
                .error(R.drawable.error)
                .into(ivPoster);
    }
}
