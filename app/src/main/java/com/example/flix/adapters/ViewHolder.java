package com.example.flix.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flix.R;
import com.example.flix.activities.MovieDetailsActivity;
import com.example.flix.models.Movie;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ViewHolder extends RecyclerView.ViewHolder {

    RelativeLayout container;
    Context context;
    TextView tvTitle;
    TextView tvOverview;
    TextView tvRating;
    ImageView ivPoster;

    public ViewHolder(@NonNull View itemView) {
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
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            imageUrl = movie.getBackdropPath();
        }
        else {
            imageUrl = movie.getPosterPath();
        }
        // then imageUrl = back drop image
        // else imageUrl = poster image

        Glide.with(context).load(imageUrl)
                .transform(new RoundedCorners(30))
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
                Pair<View, String> p1 = Pair.create((View)tvOverview, "overview");
                Pair<View, String> p2 = Pair.create((View)tvTitle, "title");
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) context, p1, p2);
                context.startActivity(i, options.toBundle());
            }
        });
    }
}
