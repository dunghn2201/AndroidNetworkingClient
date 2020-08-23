package com.dunghnpd02792.assignmentandroidnetworking.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dunghnpd02792.assignmentandroidnetworking.R;
import com.dunghnpd02792.assignmentandroidnetworking.adapter.CartProductAdapter;
import com.dunghnpd02792.assignmentandroidnetworking.model.CartProduct;
import com.dunghnpd02792.assignmentandroidnetworking.ui.DetailProductActivity;
import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.ScaleRatingBar;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import static com.dunghnpd02792.assignmentandroidnetworking.ui.DetailProductActivity.cartproductList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ConstraintLayout noticeEmptyCart;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View rootView;
    private Button btnMakeOderCartProduct;
    private TextView tvTotalPriceProductCart;
    private RecyclerView rvCartProduct;
    private CartProductAdapter cartProductAdapter;
    private RecyclerView.LayoutManager layoutManagerCartProduct;
    int totalPrice = 0;
    String formattedNumber;
    String convertToComma;

    public OrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
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
        rootView = inflater.inflate(R.layout.fragment_order, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        rvCartProduct = rootView.findViewById(R.id.rvCartProduct);
        noticeEmptyCart = rootView.findViewById(R.id.noticeEmptyCart);
        Log.d("DULIEU", "initView: " + cartproductList.size());
        if (cartproductList.size() == 0) {
            noticeEmptyCart.setVisibility(View.VISIBLE);
            rvCartProduct.setVisibility(View.GONE);
        } else {
            noticeEmptyCart.setVisibility(View.GONE);
            rvCartProduct.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < cartproductList.size(); i++) {
            String getConvertPrice = (cartproductList.get(i).getTvPriceProductCart()).replace(",", "");
            totalPrice += Integer.parseInt(getConvertPrice);
            NumberFormat formatter = new DecimalFormat("#,###");
            formattedNumber = formatter.format(totalPrice);
            convertToComma = formattedNumber.replace(".", ",");
        }
        Log.d("DULIEU", "totalPrice: " + convertToComma);
        tvTotalPriceProductCart = rootView.findViewById(R.id.tvTotalPriceProductCart);
        if (convertToComma == null) {
            tvTotalPriceProductCart.setText("0");
        } else {
            tvTotalPriceProductCart.setText(convertToComma);
        }

        btnMakeOderCartProduct = rootView.findViewById(R.id.btnMakeOderCartProduct);
        btnMakeOderCartProduct.setOnClickListener(this);
        layoutManagerCartProduct = new LinearLayoutManager(getActivity());
        rvCartProduct.setHasFixedSize(true);
        rvCartProduct.setLayoutManager(layoutManagerCartProduct);
        cartProductAdapter = new CartProductAdapter(
                getActivity(), cartproductList);
        rvCartProduct.setAdapter(cartProductAdapter);
        cartProductAdapter.notifyDataSetChanged();
    }

//    private int grandTotal(List<CartProduct> getTotalPrice) {
//
//        int totalPrice = 0;
//        for (int i = 0; i < cartproductList.size(); i++) {
//            totalPrice += Integer.parseInt(getTotalPrice.get(i).getTvPriceProductCart());
//        }
//
//        return totalPrice;
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMakeOderCartProduct:
                Log.d("DULIEU", "btnMakeOderCartProduct: ");
                break;

        }
    }
}