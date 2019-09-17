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
import com.voyager.boorna.activity.landing.model.TripOtherDetails;

import java.util.ArrayList;
import java.util.Random;

public class TripOtherDetailsAdapter extends RecyclerView.Adapter<TripOtherDetailsAdapter.ViewHolder> {


    ArrayList<TripOtherDetails> tripOtherDetails = new ArrayList<>();

    public TripOtherDetailsAdapter(ArrayList<TripOtherDetails> tripOtherDetails) {
        this.tripOtherDetails = tripOtherDetails;
    }

    @NonNull
    @Override

    public TripOtherDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_trip_other_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripOtherDetailsAdapter.ViewHolder holder, int position) {
        final TripOtherDetails otherDetails = tripOtherDetails.get(position);
        int colorWhite = holder.llTripOther.getContext().getResources().getColor(R.color.white);
        int colorGrey = holder.llTripOther.getContext().getResources().getColor(R.color.cell_gray);

        Drawable pick = holder.llTripOther.getContext().getResources().getDrawable(R.drawable.arrow_point_to_up);
        Drawable drop = holder.llTripOther.getContext().getResources().getDrawable(R.drawable.arrow_point_to_down);

        holder.ivProductLoad.setImageResource(R.drawable.dot_inside_a_circle);
        holder.tvProductName.setText(otherDetails.getOtherDetail1());

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
        if (tripOtherDetails != null) {
            return tripOtherDetails.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView tvProductName;
        AppCompatImageView ivProductLoad;
        LinearLayout llTripOther;


        public ViewHolder(View view) {
            super(view);
            llTripOther = view.findViewById(R.id.llTripOther);
            ivProductLoad = view.findViewById(R.id.ivProductLoad);
            tvProductName = view.findViewById(R.id.tvProductName);


        }

        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }
    }

}
