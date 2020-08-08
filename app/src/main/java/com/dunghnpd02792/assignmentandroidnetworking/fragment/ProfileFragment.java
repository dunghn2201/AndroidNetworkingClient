package com.dunghnpd02792.assignmentandroidnetworking.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dunghnpd02792.assignmentandroidnetworking.R;
import com.dunghnpd02792.assignmentandroidnetworking.api.APIService;
import com.dunghnpd02792.assignmentandroidnetworking.api.RetrofitClient;
import com.dunghnpd02792.assignmentandroidnetworking.api.SharedPreferencesToken;
import com.dunghnpd02792.assignmentandroidnetworking.model.UserData;
import com.dunghnpd02792.assignmentandroidnetworking.model.UserInfo;
import com.dunghnpd02792.assignmentandroidnetworking.ui.LoginActivity;
import com.dunghnpd02792.assignmentandroidnetworking.ui.adapter.UserAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment  implements UpdateUserBottomSheetDialog.ItemClickListener{
    List<UserInfo> userInfoList = new ArrayList<>();
    List<UserData> userDataList = new ArrayList<>();
    APIService apiService;
    RecyclerView rvUser;
    UserAdapter userAdapter;
    TextView tvProfileFullName, tvProfileAddress;
    Button btnLogOut;
    CircleImageView profileImage;
    Button btnUpdateUser;
    TextView tvStatebottomsheet;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View rootView;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        initView(rootView);
        getSharedPreferencesToken(new SharedPreferencesToken(getActivity()).isCheckLogin());


        return rootView;
    }

    private void initView(View rootView) {
        rvUser = rootView.findViewById(R.id.rv);
        tvProfileFullName = rootView.findViewById(R.id.tvFullname);
        tvProfileAddress = rootView.findViewById(R.id.tvAddressProfile);
        btnUpdateUser = rootView.findViewById(R.id.imgBtnUpdateUser);
        profileImage = rootView.findViewById(R.id.profile_image);
        btnLogOut = rootView.findViewById(R.id.btnLogout);
        tvStatebottomsheet = rootView.findViewById(R.id.tvStatebottomsheet);

        btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateBottomSheetDialog();
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SharedPreferencesToken(getActivity()).loginOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

    }

    private void showUpdateBottomSheetDialog() {
        UpdateUserBottomSheetDialog updateUserBottomSheetDialog =
                UpdateUserBottomSheetDialog.newInstance();
        updateUserBottomSheetDialog.show(getActivity().getSupportFragmentManager(),
                UpdateUserBottomSheetDialog.TAG);
    }

    private void getSharedPreferencesToken(String checkLogin) {
        apiService = RetrofitClient.getInstance().create(APIService.class);
        apiService.getTokenAPI(checkLogin).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (response.body() != null) {
                    tvProfileFullName.setText("Fullname: " + response.body().getFullName());
                    tvProfileAddress.setText("Address : " + response.body().getAddress());
                    Glide
                            .with(getActivity())
                            .load(response.body().getAvatar())
                            .placeholder(R.drawable.placeholder_avatar)
                            .centerCrop()
                            .into(profileImage);
                    if (response.body().getPermission()) {
                        userInfoList.add(new UserInfo(response.body().getId(),
                                response.body().getFullName(),
                                response.body().getEmail(),
                                response.body().getHash(),
                                response.body().getAddress(),
                                response.body().getAvatar(),
                                response.body().getPermission(),
                                response.body().getUserData()));
                        for (int i = 0; i < response.body().getUserData().size(); i++) {
                            userDataList.add(new UserData(response.body().getUserData().get(i).getId(),
                                    response.body().getUserData().get(i).getFullName(),
                                    response.body().getUserData().get(i).getEmail(),
                                    response.body().getUserData().get(i).getHash(),
                                    response.body().getUserData().get(i).getAddress(),
                                    response.body().getUserData().get(i).getAvatar(),
                                    response.body().getUserData().get(i).getPermission()));
                        }
                        userAdapter = new UserAdapter(getActivity(), userDataList);
                        rvUser.setAdapter(userAdapter);
                        rvUser.setLayoutManager(new LinearLayoutManager(getActivity()));
                    } else {
                        rvUser.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.d("DULIEU", "onFailure MainActivity: " + t.getMessage().toString());
            }
        });
//        Log.d("DULIEU", "getSharedPreferencesToken: " + checkLogin);
    }


    @Override
    public void onItemClick(String item) {
        tvStatebottomsheet.setText("Selected action item is " + item);
    }
}