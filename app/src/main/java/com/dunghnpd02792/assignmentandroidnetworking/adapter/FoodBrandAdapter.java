package com.dunghnpd02792.assignmentandroidnetworking.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dunghnpd02792.assignmentandroidnetworking.R;
import com.dunghnpd02792.assignmentandroidnetworking.model.FoodBrand;
import com.dunghnpd02792.assignmentandroidnetworking.ui.MainActivity;

import java.util.ArrayList;

/**
 * Created on 8/17/2020
 */
public class FoodBrandAdapter extends RecyclerView.Adapter<FoodBrandAdapter.MyViewHolder> {
    private ArrayList<FoodBrand> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.tvFoodBrand);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imgFoodBrand);
        }
    }

    public FoodBrandAdapter(ArrayList<FoodBrand> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_foodbrand, parent, false);

//        view.setOnClickListener(MainActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        ImageView imageView = holder.imageViewIcon;

        textViewName.setText(dataSet.get(listPosition).getNameFoodBrand());
        imageView.setImageResource(dataSet.get(listPosition).getImgFoodBrand());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
