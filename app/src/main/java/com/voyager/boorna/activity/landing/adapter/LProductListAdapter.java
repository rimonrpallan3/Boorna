package com.voyager.boorna.activity.landing.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.voyager.boorna.R;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class LProductListAdapter extends RecyclerView.Adapter<LProductListAdapter.ViewHolder>  {


    @NonNull
    @Override
    public LProductListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_body_left_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LProductListAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView ivProductProperty;
        AppCompatTextView tvProductProperty;
        public ViewHolder(View view) {
            super(view);

            ivProductProperty = view.findViewById(R.id.ivProductProperty);
            tvProductProperty = view.findViewById(R.id.tvProductProperty);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }
    }

}
