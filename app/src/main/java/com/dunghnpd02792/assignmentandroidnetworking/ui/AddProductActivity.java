package com.dunghnpd02792.assignmentandroidnetworking.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dunghnpd02792.assignmentandroidnetworking.R;
import com.dunghnpd02792.assignmentandroidnetworking.api.APIService;
import com.dunghnpd02792.assignmentandroidnetworking.api.RetrofitClient;
import com.dunghnpd02792.assignmentandroidnetworking.config.ProgressRequestBody;
import com.dunghnpd02792.assignmentandroidnetworking.fragment.HomeFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pl.aprilapps.easyphotopicker.ChooserType;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dunghnpd02792.assignmentandroidnetworking.fragment.HomeFragment.rvProductH;


public class AddProductActivity extends AppCompatActivity implements View.OnClickListener, ProgressRequestBody.UploadCallbacks {
    EditText edname_food, edaddress_food, edprice_food, eddescription_food;
    Button btnAddProduct;
    ImageView imgProduct, imgBackAddPoroduct;
    TextView tvStatusUploadImage;
    Bitmap mBitmap;
    String nameImage;
    private EasyImage easyImage;
    private static final int CAMERA_REQUEST_CODE = 7500;
    private static final int CHOOSER_PERMISSIONS_REQUEST_CODE = 7459;
    private static final int DOCUMENTS_REQUEST_CODE = 7501;
    byte[] byteArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // In Activity's onCreate() for instance
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_add_product);
        edname_food = findViewById(R.id.edname_food);
        edaddress_food = findViewById(R.id.edaddress_food);
        edprice_food = findViewById(R.id.edprice_food);
        eddescription_food = findViewById(R.id.eddescription_food);
        tvStatusUploadImage = findViewById(R.id.tvStatusUploadImageProduct);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        imgProduct = findViewById(R.id.imgFood);
        imgBackAddPoroduct = findViewById(R.id.imgBackAddPoroduct);
        btnAddProduct.setOnClickListener(this);
        imgBackAddPoroduct.setOnClickListener(this);
        imgProduct.setOnClickListener(this);
        setUpCamera();
    }

    private void setUpCamera() {
        easyImage = new EasyImage.Builder(this)
                .setChooserTitle("Pick media")
                .setCopyImagesToPublicGalleryFolder(false)
                .setChooserType(ChooserType.CAMERA_AND_GALLERY)
                .setFolderName("Pick your food")
                .allowMultiple(true)
                .build();
    }


    @Override
    public void onClick(View v) {
        APIService apiServices = RetrofitClient.getInstance().create(APIService.class);
        switch (v.getId()) {
            case R.id.btnAddProduct:
                addProduct(apiServices);
                break;
            case R.id.imgFood:
                chooseCamera();
                break;
            case R.id.imgBackAddPoroduct:
                finish();
                break;
        }
    }

    private void addProduct(APIService apiServices) {
        if (byteArray != null) {
            try {
                File filesDir = getApplicationContext().getFilesDir();
                File file = new File(filesDir, "IMG_" + Calendar.getInstance().getTimeInMillis() + ".png");
                FileOutputStream fos = null;
                fos = new FileOutputStream(file);
                fos.write(byteArray);
                fos.flush();
                fos.close();
                ProgressRequestBody fileBody = new ProgressRequestBody(file, this);
                apiServices.addProduct(
                        RequestBody.create(MediaType.parse("text/plain"), edname_food.getText().toString()),
                        RequestBody.create(MediaType.parse("text/plain"), edaddress_food.getText().toString()),
                        RequestBody.create(MediaType.parse("text/plain"), edprice_food.getText().toString()),
                        RequestBody.create(MediaType.parse("text/plain"), eddescription_food.getText().toString()),
                        MultipartBody.Part.createFormData("upload", file.getName(), fileBody),
                        RequestBody.create(MediaType.parse("text/plain"), nameImage)).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            finish();
//                            HomeFragment fragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("homeFragment");
//                            getSupportFragmentManager().beginTransaction().detach(fragment).attach(fragment).commit();
                            Toast.makeText(AddProductActivity.this, "Adding product successfully", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, Throwable t) {
                        Log.d("DULIEU", Objects.requireNonNull(t.getMessage()));
                        Toast.makeText(AddProductActivity.this, "Loi roi", Toast.LENGTH_SHORT).show();

                    }
                });

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(this, "Please add picture your gift!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CHOOSER_PERMISSIONS_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            easyImage.openChooser(AddProductActivity.this);
        } else if (requestCode == CAMERA_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            easyImage.openCameraForImage(AddProductActivity.this);
        } else if (requestCode == DOCUMENTS_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            easyImage.openDocuments(AddProductActivity.this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        easyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onMediaFilesPicked(MediaFile[] imageFiles, MediaSource source) {
                if (resultCode == Activity.RESULT_OK) {
                    String filePath = imageFiles[0].getFile().getPath();
                    nameImage = imageFiles[0].getFile().getName();
                    if (filePath != null) {
                        mBitmap = BitmapFactory.decodeFile(filePath);
                        Log.d("DULIEU", "onMediaFilesPicked: filePath:" + filePath + "-----" + "mBitmap: " + mBitmap);
                        imgProduct.setImageBitmap(mBitmap);
                        getByteArrayInBackground();
                    }
                }
            }

            private void getByteArrayInBackground() {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        mBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
                        byteArray = bos.toByteArray();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvStatusUploadImage.setText("Selected");
                            }
                        });
                    }
                };
                thread.start();
            }

            @Override
            public void onImagePickerError(@NonNull Throwable error, @NonNull MediaSource source) {
                error.printStackTrace();
            }

            @Override
            public void onCanceled(@NonNull MediaSource source) {

            }
        });
    }

    private void chooseCamera() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chooser Type");
        builder.setMessage("Please choose photo by your camera or your documents");
        builder.setCancelable(false);
        builder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("CAMERA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String[] necessaryPermissions = new String[]{Manifest.permission.CAMERA};
                if (arePermissionsGranted(necessaryPermissions)) {
                    easyImage.openCameraForImage(AddProductActivity.this);
                } else {
                    requestPermissionsCompat(necessaryPermissions, CAMERA_REQUEST_CODE);
                }
            }
        });
        builder.setNegativeButton("DOCUMENTS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String[] necessaryPermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
                if (arePermissionsGranted(necessaryPermissions)) {
                    easyImage.openDocuments(AddProductActivity.this);
                } else {
                    requestPermissionsCompat(necessaryPermissions, DOCUMENTS_REQUEST_CODE);
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private boolean arePermissionsGranted(String[] necessaryPermissions) {
        for (String permission : necessaryPermissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    private void requestPermissionsCompat(String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(AddProductActivity.this, permissions, requestCode);
    }


    @Override
    public void onProgressUpdate(int percentage) {
        tvStatusUploadImage.setText("Waiting " + percentage + "%");
    }

    @Override
    public void onError() {
        tvStatusUploadImage.setText("Can't upload this file");
    }

    @Override
    public void onFinish() {
        tvStatusUploadImage.setText("Selected");
    }

    @Override
    public void uploadStart() {
        tvStatusUploadImage.setText("Starting...");
    }

    @Override
    public void finish() {
        setResult(RESULT_OK, new Intent());
        Log.d("DULIEU", "finish: Gui di");
        super.finish();
    }

}