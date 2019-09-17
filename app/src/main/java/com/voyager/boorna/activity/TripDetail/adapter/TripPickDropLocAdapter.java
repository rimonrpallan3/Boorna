package com.voyager.boorna.activity.TripDetail.adapter;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.voyager.boorna.R;
import com.voyager.boorna.activity.TripDetail.TripDetailActivity;
import com.voyager.boorna.activity.landing.model.CardList;
import com.voyager.boorna.activity.landing.model.TripPickDropLoc;

import java.util.ArrayList;
import java.util.Random;

public class TripPickDropLocAdapter extends RecyclerView.Adapter<TripPickDropLocAdapter.ViewHolder>  {


    ArrayList<TripPickDropLoc> tripPickDropLocs;

    public TripPickDropLocAdapter(ArrayList<TripPickDropLoc> tripPickDropLocs) {
        this.tripPickDropLocs = tripPickDropLocs;
    }

    @NonNull
    @Override
    public TripPickDropLocAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_pick_drop, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripPickDropLocAdapter.ViewHolder holder, int position) {
        final TripPickDropLoc tripPickDropLoc = tripPickDropLocs.get(position);
        int colorWhite = holder.llPickDrop.getContext().getResources().getColor(R.color.white);
        int colorGrey = holder.llPickDrop.getContext().getResources().getColor(R.color.cell_gray);
        Drawable pick = holder.llPickDrop.getContext().getResources().getDrawable(R.drawable.arrow_point_to_up);
        Drawable drop = holder.llPickDrop.getContext().getResources().getDrawable(R.drawable.arrow_point_to_down);
        if(tripPickDropLoc.getTripCharacter().equals("pick")) {
            holder.ivFrom.setImageResource(R.drawable.arrow_point_to_up);
        }else if(tripPickDropLoc.getTripCharacter().equals("drop")){
            holder.ivFrom.setImageResource(R.drawable.arrow_point_to_down);
        }
        holder.ivFromFlag.setImageResource(tripPickDropLoc.getMapIcon());
        holder.tvFromPlace.setText(tripPickDropLoc.getTripPlace());
        holder.ivProductLoad.setImageResource(R.drawable.calendar);
        holder.tvProductName.setText(tripPickDropLoc.getTripDate());

    }

    /**
     * This method is used to get random color from an array of colors
     *
     * @return Array of colors
     */
    private int getColorCode() {
        int[] color = {
                R.color.white,
                R.color.cell_gray,
                R.color.white,
                R.color.cell_gray,
                R.color.white,
                R.color.cell_gray,
                R.color.white,
                R.color.cell_gray,
                R.color.white,
                R.color.cell_gray,
                R.color.white,
                R.color.cell_gray
        };
        int min = 0, max = color.length;
        Random r = new Random();
        int i = r.nextInt(max - min);
        return color[i];
    }

    @Override
    public int getItemCount() {
        if(tripPickDropLocs!=null) {
            return tripPickDropLocs.size();
        }else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView ivFrom;
        AppCompatImageView ivFromFlag;
        AppCompatTextView tvProductName;
        AppCompatTextView tvFromPlace;
        AppCompatImageView ivProductLoad;
        LinearLayout llPickDrop;

        public ViewHolder(View view) {
            super(view);
            llPickDrop = view.findViewById(R.id.llPickDrop);
            ivFrom = view.findViewById(R.id.ivFrom);
            ivFromFlag = view.findViewById(R.id.ivFromFlag);
            tvFromPlace = view.findViewById(R.id.tvFromPlace);
            ivProductLoad = view.findViewById(R.id.ivProductLoad);
            tvProductName = view.findViewById(R.id.tvProductName);


        }

        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }
    }

}
