package com.dunghnpd02792.assignmentandroidnetworking.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dunghnpd02792.assignmentandroidnetworking.R;
import com.dunghnpd02792.assignmentandroidnetworking.adapter.FoodBrandAdapter;
import com.dunghnpd02792.assignmentandroidnetworking.adapter.ProductAdapterH;
import com.dunghnpd02792.assignmentandroidnetworking.adapter.ProductAdapterV;
import com.dunghnpd02792.assignmentandroidnetworking.api.APIService;
import com.dunghnpd02792.assignmentandroidnetworking.api.RetrofitClient;
import com.dunghnpd02792.assignmentandroidnetworking.config.Constants;
import com.dunghnpd02792.assignmentandroidnetworking.config.SharedPreferencesToken;
import com.dunghnpd02792.assignmentandroidnetworking.model.FoodBrand;
import com.dunghnpd02792.assignmentandroidnetworking.model.Product;
import com.dunghnpd02792.assignmentandroidnetworking.model.UserInfo;
import com.dunghnpd02792.assignmentandroidnetworking.ui.AddProductActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener, TextWatcher, View.OnTouchListener {
    public static List<Product> productList = new ArrayList<>();
    RecyclerView.Adapter adapterFoodBrand;
    RecyclerView.LayoutManager layoutManager, layoutManager2;
    public static RecyclerView rvProductV, rvProductH, rvFoodBrand;
    ArrayList<FoodBrand> dataFoodBrand;
    ProductAdapterV productAdapterV;
    ProductAdapterH productAdapterH;
    LinearLayout lnTrendingBrands;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View rootView;
    private EditText edSearchProduct;
    private CircleImageView imgUserDashboard;
    private TextView tvFullnameUserDashboard;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FloatingActionButton floatbtnAddProduct;
    private static final int REQUESTCODE = 101;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("KIEMTRAVONGDOI", "onCreate: ");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        Log.d("KIEMTRAVONGDOI", "onCreateView: ");

        initView(rootView);
        getDataFoodBrand();
        getSharedPreferencesToken(new SharedPreferencesToken(getActivity()).isCheckLogin());
        getData();
        return rootView;
    }

    private void getDataFoodBrand() {
        rvFoodBrand.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvFoodBrand.setLayoutManager(layoutManager);
        rvFoodBrand.setItemAnimator(new DefaultItemAnimator());
        dataFoodBrand = new ArrayList<FoodBrand>();
        for (int i = 0; i < Constants.nameArray.length; i++) {
            dataFoodBrand.add(new FoodBrand(
                    Constants.nameArray[i],
                    Constants.drawableArray[i]
            ));
        }
        adapterFoodBrand = new FoodBrandAdapter(dataFoodBrand);
        rvFoodBrand.setAdapter(adapterFoodBrand);
    }

    private void getSharedPreferencesToken(String checkLogin) {
        APIService apiService = RetrofitClient.getInstance().create(APIService.class);
        apiService.getTokenAPI(checkLogin).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (response.isSuccessful()) {
                    tvFullnameUserDashboard.setText(response.body().getFullName() + "!");
                    Glide
                            .with(getActivity())
                            .load(Constants.ROOT_URL + "/uploads/" + response.body().getAvatar())
                            .error(R.drawable.placeholder_avatar)
                            .centerCrop()
                            .into(imgUserDashboard);
                    Log.d("DULIEU", "onResponse: " + Constants.ROOT_URL + "/uploads/" + response.body().getAvatar());
//                    }
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.d("DULIEU", "onFailure MainActivity: " + t.getMessage().toString());
            }
        });
    }

    private void initView(View rootView) {
        rvProductV = rootView.findViewById(R.id.rvProductV);
        rvProductH = rootView.findViewById(R.id.rvProductH);
        lnTrendingBrands = rootView.findViewById(R.id.linearLayout3);
        rvFoodBrand = rootView.findViewById(R.id.rvFoodBrand);
        edSearchProduct = rootView.findViewById(R.id.edSearchProduct);
        floatbtnAddProduct = rootView.findViewById(R.id.floatbtnAddProduct);
        tvFullnameUserDashboard = rootView.findViewById(R.id.tvFullnameUserDashboard);
        imgUserDashboard = rootView.findViewById(R.id.imgUserDashboard);
        floatbtnAddProduct.setOnClickListener(this);
        imgUserDashboard.setOnClickListener(this);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager2 = new LinearLayoutManager(getActivity());
        rvProductV.setHasFixedSize(true);
        rvProductH.setHasFixedSize(true);
        edSearchProduct.addTextChangedListener(this);
        rvProductV.setLayoutManager(layoutManager2);
        rvProductH.setLayoutManager(layoutManager);
        edSearchProduct.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatbtnAddProduct:
//                startActivity(new Intent(getActivity(), AddProductActivity.class));
                startActivityForResult(new Intent(getContext(), AddProductActivity.class), REQUESTCODE);
                break;
            case R.id.imgUserDashboard:
                FragmentTransaction tr = getFragmentManager().beginTransaction();
                tr.replace(R.id.container, ProfileFragment.newInstance("", ""));
                tr.commit();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        lnTrendingBrands.setVisibility(View.GONE);
        if (edSearchProduct.getText().toString().isEmpty()) {
            lnTrendingBrands.setVisibility(View.VISIBLE);
        }
        getData();
    }

    private void getData() {
        APIService apiService = RetrofitClient.getInstance().create(APIService.class);
        apiService.searchProduct(edSearchProduct.getText().toString()).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    productList.clear();
                    productList = response.body();
                    Log.d("DULIEU", String.valueOf(response.body()) + "  TextTruyenVao: " + edSearchProduct.getText().toString());
                    if (productList != null) {
                        //rv Horizontal
                        productAdapterH = new ProductAdapterH(getActivity(), productList);
                        rvProductH.setAdapter(productAdapterH);

                        //rv Vertical
                        productAdapterV = new ProductAdapterV(getActivity(), productList);
                        rvProductV.setAdapter(productAdapterV);
                        Log.d("DULIEU", String.valueOf(productList.size()));
                    } else {
                        Log.d("DULIEU", "error list");
                    }
                }
//                }


            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("DULIEU", "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        int inType = edSearchProduct.getInputType(); // backup the input type
//        edSearchProduct.setInputType(InputType.TYPE_NULL); // disable soft input
//        edSearchProduct.onTouchEvent(event); // cal
//        l native handler
//        edSearchProduct.setInputType(inType); // restore input type
//        return true; // consume touch even
        return false; // consume touch even
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("KIEMTRAVONGDOI", "onAttach: ");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("KIEMTRAVONGDOI", "onActivityCreated: ");
    }

    @Override
    public void onStart() {
        super.onStart();
//        FragmentTransaction tr = getFragmentManager().beginTransaction();
//        tr.replace(R.id.container, ProfileFragment.newInstance("", ""));
//        tr.commit();
        Log.d("KIEMTRAVONGDOI", "onStart: ");

    }

    @Override
    public void onResume() {
        super.onResume();
//        productAdapterH.notifyDataSetChanged();
        Log.d("KIEMTRAVONGDOI", "onResume: ");

    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d("KIEMTRAVONGDOI", "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("KIEMTRAVONGDOI", "onStop: ");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("KIEMTRAVONGDOI", "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("KIEMTRAVONGDOI", "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("KIEMTRAVONGDOI", "onDetach: ");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE && resultCode == RESULT_OK) {
            FragmentTransaction tr = getFragmentManager().beginTransaction();
            tr.replace(R.id.container, HomeFragment.newInstance("", ""));
            tr.commit();
            Log.d("DULIEU", "finish: Nhan dc");

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}