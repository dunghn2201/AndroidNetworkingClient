package com.dunghnpd02792.assignmentandroidnetworking.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.dunghnpd02792.assignmentandroidnetworking.R;
import com.dunghnpd02792.assignmentandroidnetworking.api.APIService;
import com.dunghnpd02792.assignmentandroidnetworking.api.RetrofitClient;
import com.dunghnpd02792.assignmentandroidnetworking.config.Constants;
import com.dunghnpd02792.assignmentandroidnetworking.model.UserData;
import com.dunghnpd02792.assignmentandroidnetworking.model.UserInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                .load(Constants.ROOT_URL + "/uploads/" + itemUserList.get(position).getAvatar())
                .placeholder(R.drawable.placeholder_avatar)
                .centerCrop()
                .into(holder.image);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View view = inflater.inflate(R.layout.bsdialog_update_user, null);
//                BottomSheetDialog dialog = new BottomSheetDialog(context);
//                dialog.setContentView(view);
//                CircleImageView imgAvatarUpdateUser = view.findViewById(R.id.imgAvatarUpdateUser);
//                ImageView imgUpdateUserImage2 = view.findViewById(R.id.imgUpdateUserImage2);
//                imgAvatarUpdateUser.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });
//                imgUpdateUserImage2.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });
//
//                imgAvatarUpdateUser.setVisibility(View.VISIBLE);
//                TextInputEditText edFullNameUS = view.findViewById(R.id.edFullNameUS);
//                TextInputEditText ednumberPhoneUS = view.findViewById(R.id.ednumberPhoneUS);
//                TextInputEditText eddateOfbirthUS = view.findViewById(R.id.eddateOfbirthUS);
//                TextInputEditText edbioUS = view.findViewById(R.id.edbioUS);
//                TextInputEditText edAddressUS = view.findViewById(R.id.edAddressUS);
//                Glide
//                        .with(context)
//                        .load(Constants.ROOT_URL + "/uploads/" + itemUserList.get(position).getAvatar())
//                        .error(R.drawable.placeholder_avatar)
//                        .centerCrop()
//                        .into(imgAvatarUpdateUser);
//                edFullNameUS.setText(itemUserList.get(position).getFullName());
//                ednumberPhoneUS.setText(itemUserList.get(position).getNumberPhone());
//                eddateOfbirthUS.setText(itemUserList.get(position).getDateOfbirth());
//                edbioUS.setText(itemUserList.get(position).getBio());
//                edAddressUS.setText(itemUserList.get(position).getAddress());
//                Button btnStartUpdateUser = view.findViewById(R.id.btnStartUpdateUser);
//                btnStartUpdateUser.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (edFullNameUS.getText().toString().isEmpty() || ednumberPhoneUS.getText().toString().isEmpty() || eddateOfbirthUS.getText().toString().isEmpty() || edbioUS.getText().toString().isEmpty() || edAddressUS.getText().toString().isEmpty()) {
//                            Toast.makeText(context, "Please complete all fields", Toast.LENGTH_SHORT).show();
//                        } else {
//                            APIService apiService = RetrofitClient.getInstance().create(APIService.class);
//                            apiService.updateInfoUser(
//                                    itemUserList.get(position).getId(),
//                                    RequestBody.create(MediaType.parse("text/plain"), edFullNameUS.getText().toString()),
//                                    RequestBody.create(MediaType.parse("text/plain"), ednumberPhoneUS.getText().toString()),
//                                    RequestBody.create(MediaType.parse("text/plain"), eddateOfbirthUS.getText().toString()),
//                                    RequestBody.create(MediaType.parse("text/plain"), edbioUS.getText().toString()),
//                                    RequestBody.create(MediaType.parse("text/plain"), edAddressUS.getText().toString())).enqueue(new Callback<ResponseBody>() {
//                                @Override
//                                public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
//                                    if (response.isSuccessful()) {
//                                        dialog.dismiss();
//                                        notifyDataSetChanged();
//                                        Log.d("DULIEU", "onResponse: Update User Info Success");
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(@NotNull Call<ResponseBody> call, Throwable t) {
//                                    Log.d("DULIEU", Objects.requireNonNull(t.getMessage()));
//                                    dialog.dismiss();
//                                }
//                            });
//
//                        }
//
//                    }
//                });
//                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemUserList.size();
    }

    public void removeItem(int position) {
        APIService apiService = RetrofitClient.getInstance().create(APIService.class);
        apiService.deleteUser(itemUserList.get(position).getId()).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Delete success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });
        itemUserList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(UserData item, int position) {
        itemUserList.add(position, item);
        notifyItemInserted(position);
    }

    public List<UserData> getData() {
        return itemUserList;
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

