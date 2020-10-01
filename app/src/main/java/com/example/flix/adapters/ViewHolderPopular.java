package com.example.flix.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flix.R;
import com.example.flix.activities.MovieDetailsActivity;
import com.example.flix.models.Movie;

import org.parceler.Parcels;

public class ViewHolderPopular extends RecyclerView.ViewHolder {

    RelativeLayout container;
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
        container = itemView.findViewById(R.id.container);
    }

    public void bind(final Movie movie) {
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

        // 1. Register click listener on the whole row
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 2. Navigate to a new activity on tap
                Intent i = new Intent(context, MovieDetailsActivity.class);
                i.putExtra("movie", Parcels.wrap(movie));

                context.startActivity(i);
            }
        });
    }
}
