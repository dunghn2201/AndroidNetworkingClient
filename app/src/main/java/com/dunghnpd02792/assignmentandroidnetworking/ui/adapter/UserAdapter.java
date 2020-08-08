package com.dunghnpd02792.assignmentandroidnetworking.ui.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.dunghnpd02792.assignmentandroidnetworking.R;
import com.dunghnpd02792.assignmentandroidnetworking.model.UserData;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.RecyclerViewHolder> {
    Activity context;
    List<UserData> itemUserList;

    public UserAdapter(Activity context, List<UserData> itemUserList) {
        this.context = context;
        this.itemUserList = itemUserList;
    }

    @Override
    public UserAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_user, parent, false);
        return new UserAdapter.RecyclerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final UserAdapter.RecyclerViewHolder holder, final int position) {


        holder.tvNameItem.setText(itemUserList.get(position).getFullName());
        holder.tvAddressItem.setText(itemUserList.get(position).getEmail());
        Glide
                .with(context)
                .load(itemUserList.get(position).getAvatar())
                .placeholder(R.drawable.placeholder_avatar)
                .centerCrop()
                .into(holder.image);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, DetailActivity.class);
//                intent.putExtra("id", data.get(position).getId());
//                context.startActivity(intent);
                Log.d("DULIEU", "onBindViewHolder: " + itemUserList.get(position).getAvatar());
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemUserList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView tvNameItem, tvAddressItem;
        CardView item;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgAvatar);
            tvNameItem = itemView.findViewById(R.id.tvFullName);
            tvAddressItem = itemView.findViewById(R.id.tvAddress);
            item = itemView.findViewById(R.id.item);
        }
    }
}

