package com.dunghnpd02792.assignmentandroidnetworking.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dunghnpd02792.assignmentandroidnetworking.R;
import com.dunghnpd02792.assignmentandroidnetworking.adapter.CartProductAdapter;
import com.dunghnpd02792.assignmentandroidnetworking.adapter.ProductAdapterH;
import com.dunghnpd02792.assignmentandroidnetworking.adapter.ProductAdapterV;
import com.dunghnpd02792.assignmentandroidnetworking.api.APIService;
import com.dunghnpd02792.assignmentandroidnetworking.api.RetrofitClient;
import com.dunghnpd02792.assignmentandroidnetworking.config.Constants;
import com.dunghnpd02792.assignmentandroidnetworking.fragment.HomeFragment;
import com.dunghnpd02792.assignmentandroidnetworking.model.CartProduct;
import com.dunghnpd02792.assignmentandroidnetworking.model.Product;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dunghnpd02792.assignmentandroidnetworking.fragment.HomeFragment.productList;


public class DetailProductActivity extends AppCompatActivity implements View.OnClickListener {
    public static List<CartProduct> cartproductList = new ArrayList<>();
    private ImageView imgProductDetail, imgBackProductDetail, imgDecreaseQuantityCart, imgIncreaseQuantityCart, imgEditProductDetail;
    private Button btnAddToCart;
    private String idProduct;
    private TextView tvNameProductDetailTitle, tvNameProductDetail, tvNumRatingDetail, tvPriceProductDetail, tvDesProductDetail, tvQuantityCart;
    private RecyclerView rvDetailProduct;
    private ProductAdapterH productDetailAdapter;
    private RecyclerView.LayoutManager layoutManagerDetailProduct;
    int numCount = 1;
    int numPrice;
    String getResponseImgProduct, getResponseNameProduct, getResponseQuantityProduct, getResponsePriceProduct, getResponsePriceProduct2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // In Activity's onCreate() for instance
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_detail_product);
        initView();
        setView();
        getDataViewMore();

    }

    private void getDataViewMore() {
        APIService apiService = RetrofitClient.getInstance().create(APIService.class);
        apiService.searchProduct("").enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    productList = response.body();
                    productDetailAdapter = new ProductAdapterH(
                            DetailProductActivity.this, productList);
                    rvDetailProduct.setAdapter(productDetailAdapter);
                    productDetailAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    private void initView() {
        imgProductDetail = findViewById(R.id.imgProductDetail);
//        imgEditProductDetail = findViewById(R.id.imgEditProductDetail);
        imgDecreaseQuantityCart = findViewById(R.id.imgDecreaseQuantityCart);
        imgIncreaseQuantityCart = findViewById(R.id.imgIncreaseQuantityCart);
        rvDetailProduct = findViewById(R.id.rvDetailProduct);
        tvPriceProductDetail = findViewById(R.id.tvPriceProductDetail);
        tvQuantityCart = findViewById(R.id.tvQuantityCart);
        tvNameProductDetail = findViewById(R.id.tvNameProductDetail);
        tvDesProductDetail = findViewById(R.id.tvDesProductDetail);
        imgBackProductDetail = findViewById(R.id.imgBackProductDetail);
        tvNumRatingDetail = findViewById(R.id.tvNumRatingDetail);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        imgBackProductDetail.setOnClickListener(this);
        btnAddToCart.setOnClickListener(this);
        imgIncreaseQuantityCart.setOnClickListener(this);
        imgDecreaseQuantityCart.setOnClickListener(this);

        layoutManagerDetailProduct = new LinearLayoutManager(DetailProductActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rvDetailProduct.setHasFixedSize(true);
        rvDetailProduct.setLayoutManager(layoutManagerDetailProduct);
        imgProductDetail.setColorFilter(brightIt(-50));//foto is my ImageView
        tvNameProductDetailTitle = findViewById(R.id.tvNameProductDetailTitle);
        idProduct = getIntent().getStringExtra("IDPRODUCT");
        Log.d("DULIEU", "IDPRODUCT: " + idProduct);
    }

    //and below is the brightIt func
    public static ColorMatrixColorFilter brightIt(int fb) {
        ColorMatrix cmB = new ColorMatrix();
        cmB.set(new float[]{
                1, 0, 0, 0, fb,
                0, 1, 0, 0, fb,
                0, 0, 1, 0, fb,
                0, 0, 0, 1, 0});

        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(cmB);
//Canvas c = new Canvas(b2);
//Paint paint = new Paint();
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(colorMatrix);
//paint.setColorFilter(f);
        return f;
    }

    private void setView() {
        APIService apiService = RetrofitClient.getInstance().create(APIService.class);
        apiService.getProductByID(idProduct).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Log.d("DULIEU", "onResponse.isSuccessfu: " + response.body().getNameFood());
                    tvNameProductDetailTitle.setText(response.body().getNameFood());
                    getResponseNameProduct = response.body().getNameFood();
                    tvNameProductDetail.setText(response.body().getNameFood());
                    tvPriceProductDetail.setText(response.body().getPriceFood());
                    getResponsePriceProduct2 = response.body().getPriceFood();
                    numPrice = Integer.parseInt(response.body().getPriceFood().replace(",", ""));

                    tvNumRatingDetail.setText(response.body().getNumRatingFood());
                    tvDesProductDetail.setText(response.body().getDescriptionFood());
                    Glide
                            .with(DetailProductActivity.this)
                            .load(Constants.ROOT_URL + "/uploads/" + response.body().getImageFood())
                            .placeholder(R.drawable.placeholder_avatar)
                            .centerCrop()
                            .into(imgProductDetail);
                    getResponseImgProduct = response.body().getImageFood();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.d("DULIEU", "onFailure: " + t.getMessage());
            }
        });

    }


    private void displayQuantityCart(int number) {
        tvQuantityCart.setText("" + number);
        getResponseQuantityProduct = String.valueOf(number);
        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedNumber = formatter.format(numPrice * number);
        String convertToComma = formattedNumber.replace(".", ",");
        tvPriceProductDetail.setText(convertToComma);
        getResponsePriceProduct = convertToComma;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBackProductDetail:
                finish();
                break;
            case R.id.imgIncreaseQuantityCart:
                numCount = numCount + 1;
                displayQuantityCart(numCount);
                break;
            case R.id.imgDecreaseQuantityCart:
//                    int count2= Integer.parseInt(String.valueOf(myViewHolder.tvQuantity.getText()));
//                count = Integer.parseInt(String.valueOf(1));
                if (numCount == 1) {
                    tvQuantityCart.setText("1");
                } else {
//                    count -= 1;
                    numCount = numCount - 1;
                    displayQuantityCart(numCount);
                }
                break;
            case R.id.btnAddToCart:
//                cartproductList.clear();
                if (getResponseQuantityProduct == null && getResponsePriceProduct == null) {
                    cartproductList.add(new CartProduct(getResponseImgProduct, getResponseNameProduct, String.valueOf(1), getResponsePriceProduct2));
                    Log.d("DULIEU", "onClick: " + getResponseImgProduct);
                    Toast.makeText(this, "Add to cart successfully", Toast.LENGTH_SHORT).show();
                } else {
                    cartproductList.add(new CartProduct(getResponseImgProduct, getResponseNameProduct, getResponseQuantityProduct, getResponsePriceProduct));
                    Log.d("DULIEU", "onClick: " + getResponseImgProduct);
                    Toast.makeText(this, "Add to cart successfully", Toast.LENGTH_SHORT).show();
                }
////                Log.d("DULIEU", "Tên sản phẩm: " + getResponseNameProduct + "\n Link ảnh: " + getResponseImgProduct + "\n Số lượng: " + getResponseQuantityProduct + "\n Số tiền: " + getResponsePriceProduct);
//
//                View viewCartProduct = getLayoutInflater().inflate(R.layout.bsdialog_cartproduct, null);
//                BottomSheetDialog dialog = new BottomSheetDialog(this);
//                dialog.setContentView(viewCartProduct);
//                tvTotalPriceProductCart = viewCartProduct.findViewById(R.id.tvTotalPriceProductCart);
//                if (getResponsePriceProduct == null) {
//                    tvTotalPriceProductCart.setText(getResponsePriceProduct2);
//                } else {
//                    tvTotalPriceProductCart.setText(getResponsePriceProduct);
//
//                }
//
//                btnMakeOderCartProduct = viewCartProduct.findViewById(R.id.btnMakeOderCartProduct);
//                rvCartProduct = viewCartProduct.findViewById(R.id.rvCartProduct);
//                layoutManagerCartProduct = new LinearLayoutManager(DetailProductActivity.this);
//                rvCartProduct.setHasFixedSize(true);
//                rvCartProduct.setLayoutManager(layoutManagerCartProduct);
//
//                cartProductAdapter = new CartProductAdapter(
//                        DetailProductActivity.this, cartproductList);
//                rvCartProduct.setAdapter(cartProductAdapter);
//                cartProductAdapter.notifyDataSetChanged();
//                dialog.show();
//                break;
//            case R.id.imgEditProductDetail:
//                startActivity(new Intent(this, AddProductActivity.class));
//                finish();
//                break;
        }
    }


}