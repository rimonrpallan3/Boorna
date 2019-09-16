package com.voyager.boorna.activity.landing.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.voyager.boorna.R;
import com.voyager.boorna.activity.landing.model.CardList;

import java.util.ArrayList;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class LandingAdapter extends RecyclerView.Adapter<LandingAdapter.ViewHolder>  {


    ArrayList<CardList> cardLists;


    public LandingAdapter(ArrayList<CardList> cardLists) {
        this.cardLists = cardLists;
    }

    @NonNull
    @Override
    public LandingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_landing, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LandingAdapter.ViewHolder holder, int position) {
        CardList cardList = cardLists.get(position);
        int color = holder.cvCell.getContext().getResources().getColor(getColorCode());
        holder.cvCell.setCardBackgroundColor(color);
        holder.ivFrom.setImageResource(cardList.getIvFrom());
        holder.ivFrom.setImageResource(cardList.getIvFrom());
        holder.ivFromFlag.setImageResource(cardList.getIvFromFlag());
        holder.ivTo.setImageResource(cardList.getIvTo());
        holder.ivToFlag.setImageResource(cardList.getIvToFlag());
        holder.ivProductHeight.setImageResource(cardList.getIvProductHeight());
        holder.ivProductLength.setImageResource(cardList.getIvProductLength());
        holder.ivProductLoad.setImageResource(cardList.getIvProductLoad());
        holder.ivProductWidth.setImageResource(cardList.getIvProductWidth());
        holder.ivProductWeight.setImageResource(cardList.getIvProductWeight());
        holder.tvTripCode.setText(cardList.getTvTripCode());
        holder.tvTripStatus.setText(cardList.getTvTripStatus());
        holder.tvTripTotWeight.setText(cardList.getTvTripTotWeight());
        holder.tvFromPlace.setText(cardList.getTvToPlace());
        holder.tvFromPlace.setText(cardList.getTvFromPlace());
        holder.tvLoadCnt.setText(cardList.getTvLoadCnt());
        holder.tvUnLoadCnt.setText(cardList.getTvUnLoadCnt());
        holder.tvProductLoad.setText(cardList.getTvProductLoad());
        holder.tvProductWeight.setText(cardList.getTvProductWeight());
        if(cardList.getTvProductPallets()!=null) {
            holder.llProductWidth.setVisibility(View.GONE);
            holder.llProductHeight.setVisibility(View.GONE);
            holder.llProductLength.setVisibility(View.GONE);
            holder.tvProductPallets.setText(cardList.getTvProductPallets());
        }else {
            holder.llProductPallets.setVisibility(View.GONE);
            holder.tvProductHeight.setText(cardList.getTvProductHeight());
            holder.tvProductLength.setText(cardList.getTvProductLength());
            holder.tvProductWidth.setText(cardList.getTvProductWidth());
        }
    }

    /**
     * This method is used to get random color from an array of colors
     *
     * @return Array of colors
     */
    private int getColorCode() {
        int[] color = {
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
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView ivTripCode;
        AppCompatImageView ivFrom;
        AppCompatImageView ivFromFlag;
        AppCompatTextView tvTripCode;
        AppCompatTextView tvTripTotWeight;
        AppCompatTextView tvTripStatus;
        AppCompatTextView tvLoadCnt;
        AppCompatTextView tvUnLoadCnt;
        AppCompatTextView tvProductLoad;
        AppCompatTextView tvProductWeight;
        AppCompatTextView tvProductPallets;
        AppCompatTextView tvProductHeight;
        AppCompatTextView tvProductWidth;
        AppCompatTextView tvProductLength;
        AppCompatTextView tvToPlace;
        AppCompatTextView tvFromPlace;
        AppCompatImageView ivTo;
        AppCompatImageView ivToFlag;
        AppCompatImageView ivProductLoad;
        AppCompatImageView ivProductWeight;
        AppCompatImageView ivProductPallets;
        AppCompatImageView ivProductHeight;
        AppCompatImageView ivProductWidth;
        AppCompatImageView ivProductLength;
        LinearLayout llProductLoad;
        LinearLayout llProductWeight;
        LinearLayout llProductPallets;
        LinearLayout llProductHeight;
        LinearLayout llProductWidth;
        LinearLayout llProductLength;

        CardView cvCell;
        public ViewHolder(View view) {
            super(view);

            cvCell = view.findViewById(R.id.cvCell);
            ivTripCode = view.findViewById(R.id.ivTripCode);
            tvTripCode = view.findViewById(R.id.tvTripCode);
            tvTripTotWeight = view.findViewById(R.id.tvTripTotWeight);
            tvTripStatus = view.findViewById(R.id.tvTripStatus);
            ivFrom = view.findViewById(R.id.ivFrom);
            ivFromFlag = view.findViewById(R.id.ivFromFlag);
            ivTo = view.findViewById(R.id.ivTo);
            ivToFlag = view.findViewById(R.id.ivToFlag);
            tvLoadCnt = view.findViewById(R.id.tvLoadCnt);
            tvUnLoadCnt = view.findViewById(R.id.tvUnLoadCnt);
            tvProductLoad = view.findViewById(R.id.tvProductLoad);
            tvProductWeight = view.findViewById(R.id.tvProductWeight);
            tvProductPallets = view.findViewById(R.id.tvProductPallets);
            tvProductHeight = view.findViewById(R.id.tvProductHeight);
            tvProductWidth = view.findViewById(R.id.tvProductWidth);
            tvProductLength = view.findViewById(R.id.tvProductLength);
            ivProductLoad = view.findViewById(R.id.ivProductLoad);
            ivProductWeight = view.findViewById(R.id.ivProductWeight);
            ivProductPallets = view.findViewById(R.id.ivProductPallets);
            ivProductHeight = view.findViewById(R.id.ivProductHeight);
            ivProductWidth = view.findViewById(R.id.ivProductWidth);
            ivProductLength = view.findViewById(R.id.ivProductLength);
            llProductLoad = view.findViewById(R.id.llProductLoad);
            llProductWeight = view.findViewById(R.id.llProductWeight);
            llProductPallets = view.findViewById(R.id.llProductPallets);
            llProductHeight = view.findViewById(R.id.llProductHeight);
            llProductWidth = view.findViewById(R.id.llProductWidth);
            llProductLength = view.findViewById(R.id.llProductLength);
            tvToPlace = view.findViewById(R.id.tvToPlace);
            tvFromPlace = view.findViewById(R.id.tvFromPlace);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }
    }

}
