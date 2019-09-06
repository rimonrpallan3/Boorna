package com.voyager.boorna.activity.landing.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.share.internal.LikeButton;
import com.voyager.boorna.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LandingAdapter extends RecyclerView.Adapter<LandingAdapter.ViewHolder>  {


    @NonNull
    @Override
    public LandingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_landing, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LandingAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llHome;
        TextView tvFavValue;
        TextView tvHomeAmt;
        TextView tvHeading;
        ImageView ivHome;
        ImageView ivFav1;
        ImageView ivFav2;
        ImageView ivFav3;
        ImageView ivFav4;
        ImageView ivFav5;
        LikeButton lbHomeFav;

        public ViewHolder(View view) {
            super(view);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }
    }

}
