package com.dunghnpd02792.assignmentandroidnetworking.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dunghnpd02792.assignmentandroidnetworking.R;
import com.dunghnpd02792.assignmentandroidnetworking.api.APIService;
import com.dunghnpd02792.assignmentandroidnetworking.api.RetrofitClient;
import com.dunghnpd02792.assignmentandroidnetworking.config.Constants;
import com.dunghnpd02792.assignmentandroidnetworking.model.CartProduct;
import com.dunghnpd02792.assignmentandroidnetworking.model.UserData;
import com.dunghnpd02792.assignmentandroidnetworking.model.UserInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.RecyclerViewHolder> {
    Activity context;
    List<CartProduct> itemCartProductList;

    public CartProductAdapter(Activity context, List<CartProduct> itemCartProductList) {
        this.context = context;
        this.itemCartProductList = itemCartProductList;
    }

    @Override
    public CartProductAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_cart_product, parent, false);
        return new CartProductAdapter.RecyclerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final CartProductAdapter.RecyclerViewHolder holder, final int position) {


        holder.tvNameProductCartItem.setText(itemCartProductList.get(position).getTvNameProductCart());
        holder.tvQuantityCartItem.setText(itemCartProductList.get(position).getTvQuantityProductCart());
        holder.tvPriceProductCartItem.setText(itemCartProductList.get(position).getTvPriceProductCart());
        Glide
                .with(context)
                .load(Constants.ROOT_URL + "/uploads/" + itemCartProductList.get(position).getImgProductCart())
                .placeholder(R.drawable.placeholder_avatar)
                .centerCrop()
                .into(holder.imgProductCartItem);
        holder.itemCartProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "NameProduct: " + itemCartProductList.get(position).getTvNameProductCart(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemCartProductList.size();
    }

//    public void removeItem(int position) {
//        APIService apiService = RetrofitClient.getInstance().create(APIService.class);
//        apiService.deleteUser(itemUserList.get(position).getId()).enqueue(new Callback<UserInfo>() {
//            @Override
//            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
//                if (response.isSuccessful()) {
//                    Toast.makeText(context, "Delete success", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserInfo> call, Throwable t) {
//
//            }
//        });
//        itemUserList.remove(position);
//        notifyItemRemoved(position);
//    }

//    public void restoreItem(UserData item, int position) {
//        itemUserList.add(position, item);
//        notifyItemInserted(position);
//    }
//
//    public List<UserData> getData() {
//        return itemUserList;
//    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProductCartItem;
        TextView tvNameProductCartItem, tvQuantityCartItem, tvPriceProductCartItem;
        LinearLayout itemCartProduct;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            imgProductCartItem = itemView.findViewById(R.id.imgProductCartItem);
            tvNameProductCartItem = itemView.findViewById(R.id.tvNameProductCartItem);
            tvPriceProductCartItem = itemView.findViewById(R.id.tvPriceProductCartItem);
            tvQuantityCartItem = itemView.findViewById(R.id.tvQuantityCartItem);
            itemCartProduct = itemView.findViewById(R.id.itemCartProduct);
        }
    }
}

