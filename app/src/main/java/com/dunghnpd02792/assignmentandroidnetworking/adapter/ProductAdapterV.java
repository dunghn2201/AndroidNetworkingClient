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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.dunghnpd02792.assignmentandroidnetworking.R;
import com.dunghnpd02792.assignmentandroidnetworking.api.APIService;
import com.dunghnpd02792.assignmentandroidnetworking.config.Constants;
import com.dunghnpd02792.assignmentandroidnetworking.api.RetrofitClient;
import com.dunghnpd02792.assignmentandroidnetworking.model.Product;
import com.dunghnpd02792.assignmentandroidnetworking.ui.DetailProductActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapterV extends RecyclerView.Adapter<ProductAdapterV.RecyclerViewHolder> {
    Activity context;
    List<Product> itemProductList;

    public ProductAdapterV(Activity context, List<Product> itemProductList) {
        this.context = context;
        this.itemProductList = itemProductList;
    }

    @Override
    public ProductAdapterV.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_product_vertical, parent, false);
        return new ProductAdapterV.RecyclerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ProductAdapterV.RecyclerViewHolder holder, final int position) {


        holder.tvItemNameProduct.setText(itemProductList.get(position).getNameFood());
        holder.tvItemAddressProductV.setText(itemProductList.get(position).getAddressFood());
        holder.tvItemPriceProduct.setText(itemProductList.get(position).getPriceFood() + " VND");
        Glide
                .with(context)
                .load(Constants.ROOT_URL + "/uploads/" + itemProductList.get(position).getImageFood())
                .placeholder(R.drawable.placeholder_avatar)
                .centerCrop()
                .into(holder.imageItemProductV);
        holder.itemProductV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailProductActivity.class);
                intent.putExtra("IDPRODUCT", itemProductList.get(position).getId());
                context.startActivity(intent);
            }
        });
        holder.itemProductV.setOnLongClickListener(new View.OnLongClickListener() {
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

    @Override
    public int getItemCount() {
        return itemProductList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageItemProductV;
        TextView tvItemNameProduct, tvItemPriceProduct, tvItemAddressProductV;
        CardView itemProductV;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            imageItemProductV = itemView.findViewById(R.id.imgItemProductV);
            tvItemNameProduct = itemView.findViewById(R.id.tvItemNameProductV);
            tvItemAddressProductV = itemView.findViewById(R.id.tvItemAddressProductV);
            tvItemPriceProduct = itemView.findViewById(R.id.tvItemPriceProductV);
            itemProductV = itemView.findViewById(R.id.itemProductV);
        }
    }
}

