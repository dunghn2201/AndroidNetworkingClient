package com.dunghnpd02792.assignmentandroidnetworking.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dunghnpd02792.assignmentandroidnetworking.R;
import com.dunghnpd02792.assignmentandroidnetworking.api.APIService;
import com.dunghnpd02792.assignmentandroidnetworking.api.RetrofitClient;
import com.dunghnpd02792.assignmentandroidnetworking.config.Constants;
import com.dunghnpd02792.assignmentandroidnetworking.model.Product;
import com.dunghnpd02792.assignmentandroidnetworking.ui.DetailProductActivity;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapterH extends RecyclerView.Adapter<ProductAdapterH.RecyclerViewHolder> {
    Activity context;
    List<Product> itemProductList;

    public ProductAdapterH(Activity context, List<Product> itemProductList) {
        this.context = context;
        this.itemProductList = itemProductList;
    }

    @Override
    public ProductAdapterH.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_product_horizontal, parent, false);
        return new ProductAdapterH.RecyclerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ProductAdapterH.RecyclerViewHolder holder, final int position) {


        holder.tvItemNameProductH.setText(itemProductList.get(position).getNameFood());
        holder.tvItemDesProductH.setText(itemProductList.get(position).getDescriptionFood());
        holder.tvItemDesProductH.setText(itemProductList.get(position).getDescriptionFood());
        holder.itemNumRatingBar.setRating(Float.parseFloat(itemProductList.get(position).getNumRatingFood()));
//        holder.tvItemRateNumProductH.setText(itemProductList.get(position).getNumRatingFood());
        Glide
                .with(context)
                .load(Constants.ROOT_URL + "/uploads/" + itemProductList.get(position).getImageFood())
                .placeholder(R.drawable.placeholder_avatar)
                .centerCrop()
                .into(holder.imageItemProduct);
        holder.itemProductH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailProductActivity.class);
                intent.putExtra("IDPRODUCT", itemProductList.get(position).getId());
                context.startActivity(intent);
            }
        });
        holder.itemProductH.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete Product")
                        .setMessage("Are you sure you want to delete this product?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                APIService apiService = RetrofitClient.getInstance().create(APIService.class);
                                apiService.deleteProduct(itemProductList.get(position).getId()).enqueue(new Callback<Product>() {
                                    @Override
                                    public void onResponse(Call<Product> call, Response<Product> response) {
                                        if (response.isSuccessful()) {
                                            itemProductList.remove(position);
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position, itemProductList.size());
                                            Toast.makeText(context, "Delete Product Successful", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Product> call, Throwable t) {
                                        Log.d("DULIEU", "onFailure: " + t.getMessage());
                                    }
                                });
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton("Cancel", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

                return false;
            }
        });

    }

    public void updateData(List<Product> viewModels) {
        itemProductList.clear();
        itemProductList.addAll(viewModels);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemProductList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageItemProduct;
        TextView tvItemRateNumProductH;
        TextView tvItemNameProductH, tvItemDesProductH;
        CardView itemProductH;
        ScaleRatingBar itemNumRatingBar;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            imageItemProduct = itemView.findViewById(R.id.imgItemProductH);
            tvItemNameProductH = itemView.findViewById(R.id.tvItemNameProductH);
            tvItemDesProductH = itemView.findViewById(R.id.tvItemDesProductH);
            itemNumRatingBar = itemView.findViewById(R.id.rBItemRateNumProductH);
//            tvItemRateNumProductH = itemView.findViewById(R.id.tvItemRateNumProductH);
            itemProductH = itemView.findViewById(R.id.itemProductH);
        }
    }
}

