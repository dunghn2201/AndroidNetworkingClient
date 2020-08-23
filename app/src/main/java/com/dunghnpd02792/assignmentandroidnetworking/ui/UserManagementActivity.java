package com.dunghnpd02792.assignmentandroidnetworking.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import com.dunghnpd02792.assignmentandroidnetworking.adapter.UserAdapter;
import com.dunghnpd02792.assignmentandroidnetworking.api.APIService;
import com.dunghnpd02792.assignmentandroidnetworking.api.RetrofitClient;
import com.dunghnpd02792.assignmentandroidnetworking.config.Constants;
import com.dunghnpd02792.assignmentandroidnetworking.config.ProgressRequestBody;
import com.dunghnpd02792.assignmentandroidnetworking.config.RecyclerItemClickListener;
import com.dunghnpd02792.assignmentandroidnetworking.config.SharedPreferencesToken;
import com.dunghnpd02792.assignmentandroidnetworking.config.SwipeToDeleteCallback;
import com.dunghnpd02792.assignmentandroidnetworking.model.UserData;
import com.dunghnpd02792.assignmentandroidnetworking.model.UserInfo;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
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

public class UserManagementActivity extends AppCompatActivity implements View.OnClickListener, ProgressRequestBody.UploadCallbacks, DatePickerDialog.OnDateSetListener {
    private static final int CAMERA_REQUEST_CODE = 7500;
    private static final int CHOOSER_PERMISSIONS_REQUEST_CODE = 7459;
    private static final int DOCUMENTS_REQUEST_CODE = 7501;
    byte[] byteArray;
    private EasyImage easyImage;
    Bitmap mBitmap;
    RecyclerView rvUser;
    UserAdapter userAdapter;
    ImageView imgBackMU;
    List<UserData> userDataList = new ArrayList<>();
    ConstraintLayout constraintLayout;
    View view1;
    TextView tvStatusUploadAvatar2;
    ImageView imgUpdateUserImage2;
    CircleImageView imgAvatarUpdateUser;
    TextInputEditText edFullNameUS, ednumberPhoneUS, eddateOfbirthUS, edbioUS, edAddressUS;
    Button btnStartUpdateUser;
    BottomSheetDialog dialog;
    String positionClicked = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // In Activity's onCreate() for instance
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_user_management);
        initView();
        getSharedPreferencesToken(new SharedPreferencesToken(getApplicationContext()).isCheckLogin());
        Log.d("DULIEU", "onCreate: " + new SharedPreferencesToken(getApplicationContext()).isCheckLogin());
        enableSwipeToDeleteAndUndo();
        setUpCamera();
    }

    private void initView() {
        rvUser = findViewById(R.id.rvUser);
        imgBackMU = findViewById(R.id.imgBackMU);
        constraintLayout = findViewById(R.id.constraintLayout);
        imgBackMU.setOnClickListener(this);
        rvUser.addOnItemTouchListener(new RecyclerItemClickListener(UserManagementActivity.this, rvUser, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                positionClicked = String.valueOf(position);
                view1 = getLayoutInflater().inflate(R.layout.bsdialog_update_user, null);
                dialog = new BottomSheetDialog(UserManagementActivity.this);
                dialog.setContentView(view1);
                imgAvatarUpdateUser = view1.findViewById(R.id.imgAvatarUpdateUser);
                imgUpdateUserImage2 = view1.findViewById(R.id.imgUpdateUserImage2);
                tvStatusUploadAvatar2 = view1.findViewById(R.id.tvStatusUploadAvatar2);
                imgAvatarUpdateUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgUpdateUserImage2.setVisibility(View.VISIBLE);
                    }
                });
                imgUpdateUserImage2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chooseCamera();
                        imgUpdateUserImage2.setVisibility(View.GONE);
                    }
                });

                imgAvatarUpdateUser.setVisibility(View.VISIBLE);
                edFullNameUS = view1.findViewById(R.id.edFullNameUS);
                ednumberPhoneUS = view1.findViewById(R.id.ednumberPhoneUS);
                eddateOfbirthUS = view1.findViewById(R.id.eddateOfbirthUS);
                eddateOfbirthUS.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDatePickerDialog();
                    }
                });
                edbioUS = view1.findViewById(R.id.edbioUS);
                edAddressUS = view1.findViewById(R.id.edAddressUS);
                Glide
                        .with(getApplicationContext())
                        .load(Constants.ROOT_URL + "/uploads/" + userDataList.get(position).getAvatar())
                        .error(R.drawable.placeholder_avatar)
                        .centerCrop()
                        .into(imgAvatarUpdateUser);
                edFullNameUS.setText(userDataList.get(position).getFullName());
                ednumberPhoneUS.setText(userDataList.get(position).getNumberPhone());
                eddateOfbirthUS.setText(userDataList.get(position).getDateOfbirth());
                edbioUS.setText(userDataList.get(position).getBio());
                edAddressUS.setText(userDataList.get(position).getAddress());
                btnStartUpdateUser = view1.findViewById(R.id.btnStartUpdateUser);
                btnStartUpdateUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (edFullNameUS.getText().toString().isEmpty() || ednumberPhoneUS.getText().toString().isEmpty() || eddateOfbirthUS.getText().toString().isEmpty() || edbioUS.getText().toString().isEmpty() || edAddressUS.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Please complete all fields", Toast.LENGTH_SHORT).show();
                        } else {
                            APIService apiService = RetrofitClient.getInstance().create(APIService.class);
                            apiService.updateInfoUser(
                                    userDataList.get(position).getId(),
                                    RequestBody.create(MediaType.parse("text/plain"), edFullNameUS.getText().toString()),
                                    RequestBody.create(MediaType.parse("text/plain"), ednumberPhoneUS.getText().toString()),
                                    RequestBody.create(MediaType.parse("text/plain"), eddateOfbirthUS.getText().toString()),
                                    RequestBody.create(MediaType.parse("text/plain"), edbioUS.getText().toString()),
                                    RequestBody.create(MediaType.parse("text/plain"), edAddressUS.getText().toString())).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                                    if (response.isSuccessful()) {
                                        finish();
                                        startActivity(getIntent());
                                        dialog.dismiss();
                                        Log.d("DULIEU", "onResponse: Update User Info Success");
                                    }
                                }

                                @Override
                                public void onFailure(@NotNull Call<ResponseBody> call, Throwable t) {
                                    Log.d("DULIEU", Objects.requireNonNull(t.getMessage()));
                                    dialog.dismiss();
                                }
                            });

                        }

                    }
                });
                dialog.show();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final UserData item = userAdapter.getData().get(position);

                userAdapter.removeItem(position);


                Snackbar snackbar = Snackbar.make(constraintLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        userAdapter.restoreItem(item, position);
                        rvUser.scrollToPosition(position);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(rvUser);
    }


    private void getSharedPreferencesToken(String checkLogin) {
        APIService apiService = RetrofitClient.getInstance().create(APIService.class);
        apiService.getTokenAPI(checkLogin).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (response.body() != null) {
                    if (response.body().getPermission()) {
                        for (int i = 0; i < response.body().getUserData().size(); i++) {
                            userDataList.add(new UserData(response.body().getUserData().get(i).getId(),
                                    response.body().getUserData().get(i).getFullName(),
                                    response.body().getUserData().get(i).getEmail(),
                                    response.body().getUserData().get(i).getHashedPassword(),
                                    response.body().getUserData().get(i).getNumberPhone(),
                                    response.body().getUserData().get(i).getDateOfbirth(),
                                    response.body().getUserData().get(i).getBio(),
                                    response.body().getUserData().get(i).getAddress(),
                                    response.body().getUserData().get(i).getAvatar(),
                                    response.body().getUserData().get(i).getPermission()));
                        }
                        userAdapter = new UserAdapter(UserManagementActivity.this, userDataList);
                        rvUser.setAdapter(userAdapter);
                        rvUser.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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

    private void UpdateAvatarUser(APIService apiService) {
        Log.d("DULIEU", "UpdateAvatarUser: " + userDataList.get(Integer.parseInt(positionClicked)).getId());
        if (byteArray != null) {
            try {
                File filesDir = getFilesDir();
                File file = new File(filesDir, "IMG_" + Calendar.getInstance().getTimeInMillis() + ".png");
                FileOutputStream fos = null;
                fos = new FileOutputStream(file);
                fos.write(byteArray);
                fos.flush();
                fos.close();
                ProgressRequestBody fileBody = new ProgressRequestBody(file, this);
                apiService.updateAvatarUser(
                        userDataList.get(Integer.parseInt(positionClicked)).getId(),
                        MultipartBody.Part.createFormData("upload", file.getName(), fileBody),
                        RequestBody.create(MediaType.parse("text/plain"), file.getName())).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Log.d("DULIEU", "Update avatar user successfully: " + file.getName() + "---fileBody: " + fileBody);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<ResponseBody> call, Throwable t) {
                        Log.d("DULIEU", Objects.requireNonNull(t.getMessage()));
                    }
                });

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(getApplicationContext(), "Please attach picture before add data!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBackMU:
                finish();
                break;

        }
    }

    private void setUpCamera() {
        easyImage = new EasyImage.Builder(this)
                .setChooserTitle("Pick media")
                .setCopyImagesToPublicGalleryFolder(false)
                .setChooserType(ChooserType.CAMERA_AND_GALLERY)
                .setFolderName("FoodAssigment")
                .allowMultiple(true)
                .build();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CHOOSER_PERMISSIONS_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            easyImage.openChooser(this);
        } else if (requestCode == CAMERA_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            easyImage.openCameraForImage(this);
        } else if (requestCode == DOCUMENTS_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            easyImage.openDocuments(this);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        easyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onMediaFilesPicked(MediaFile[] imageFiles, MediaSource source) {
                if (resultCode == Activity.RESULT_OK) {
                    String filePath = imageFiles[0].getFile().getPath();
//                    nameAvatar = imageFiles[0].getFile().getName();

                    if (filePath != null) {
                        mBitmap = BitmapFactory.decodeFile(filePath);
                        Log.d("DULIEU", "onMediaFilesPicked: filePath:" + filePath + "-----" + "mBitmap: " + mBitmap);
                        imgAvatarUpdateUser.setImageBitmap(mBitmap);
                        getByteArrayInBackground();
                    }
                }
            }


            private void getByteArrayInBackground() {
                APIService apiService = RetrofitClient.getInstance().create(APIService.class);
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        mBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
                        byteArray = bos.toByteArray();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvStatusUploadAvatar2.setText("Selected");
                                UpdateAvatarUser(apiService);

                            }
                        });
                    }
                };
                thread.start();
            }

            @Override
            public void onImagePickerError(@NonNull Throwable error, @NonNull MediaSource source) {
                error.printStackTrace();
                Log.d("DULIEU", "onImagePickerError: " + error.toString());
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
                tvStatusUploadAvatar2.setVisibility(View.VISIBLE);
                String[] necessaryPermissions = new String[]{Manifest.permission.CAMERA};
                if (arePermissionsGranted(necessaryPermissions)) {
                    easyImage.openCameraForImage(UserManagementActivity.this);
                } else {
                    requestPermissionsCompat(necessaryPermissions, CAMERA_REQUEST_CODE);
                }
            }
        });
        builder.setNegativeButton("DOCUMENTS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tvStatusUploadAvatar2.setVisibility(View.VISIBLE);
                String[] necessaryPermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
                if (arePermissionsGranted(necessaryPermissions)) {
                    easyImage.openDocuments(UserManagementActivity.this);
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
        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }

    @Override
    public void onProgressUpdate(int percentage) {
        tvStatusUploadAvatar2.setText("Waiting " + percentage + "%");
    }

    @Override
    public void onError() {
        tvStatusUploadAvatar2.setText("Can't upload this file");
    }

    @Override
    public void onFinish() {
        tvStatusUploadAvatar2.setText("Adding success");
        tvStatusUploadAvatar2.setVisibility(View.INVISIBLE);
        finish();
        startActivity(getIntent());
    }

    @Override
    public void uploadStart() {
        tvStatusUploadAvatar2.setText("Starting...");
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "/" + month + "/" + year;
        eddateOfbirthUS.setText(date);
    }
}