package com.dunghnpd02792.assignmentandroidnetworking.fragment;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dunghnpd02792.assignmentandroidnetworking.R;
import com.dunghnpd02792.assignmentandroidnetworking.model.UserResetPass;
import com.dunghnpd02792.assignmentandroidnetworking.config.ProgressRequestBody;
import com.dunghnpd02792.assignmentandroidnetworking.api.APIService;
import com.dunghnpd02792.assignmentandroidnetworking.config.Constants;
import com.dunghnpd02792.assignmentandroidnetworking.api.RetrofitClient;
import com.dunghnpd02792.assignmentandroidnetworking.config.SharedPreferencesToken;
import com.dunghnpd02792.assignmentandroidnetworking.model.UserInfo;
import com.dunghnpd02792.assignmentandroidnetworking.ui.LoginActivity;
import com.dunghnpd02792.assignmentandroidnetworking.ui.UserManagementActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements ProgressRequestBody.UploadCallbacks, View.OnClickListener, DatePickerDialog.OnDateSetListener {
    List<UserInfo> userInfoList = new ArrayList<>();
    TextInputEditText eddateOfbirthUS;
    LinearLayout lnOptionManagementUser, lnOptionInfoUser, lnOptionTransactionHistory, lnOptionFeedback, lnOptionChangePass, lnOptionAboutUs, lnOptionLogOut;
    TextView tvProfileFullName, tvStatusUploadAvatar, tvBio;
    CircleImageView imgAvatarUser;
    ImageView imgUpdateUserImage;
    Bitmap mBitmap;
    private static final int CAMERA_REQUEST_CODE = 7500;
    private static final int CHOOSER_PERMISSIONS_REQUEST_CODE = 7459;
    private static final int DOCUMENTS_REQUEST_CODE = 7501;
    byte[] byteArray;
    private EasyImage easyImage;
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
        setUpCamera();
        return rootView;
    }

    private void initView(View rootView) {
        tvProfileFullName = rootView.findViewById(R.id.tvFullname);
        tvBio = rootView.findViewById(R.id.tvBio);
        tvStatusUploadAvatar = rootView.findViewById(R.id.tvStatusUploadAvatar);
        imgAvatarUser = rootView.findViewById(R.id.imgAvatarUser);
        imgUpdateUserImage = rootView.findViewById(R.id.imgUpdateUserImage);
        lnOptionLogOut = rootView.findViewById(R.id.lnOptionLogOut);
        lnOptionInfoUser = rootView.findViewById(R.id.lnOptionInfoUser);
        lnOptionFeedback = rootView.findViewById(R.id.lnOptionFeedback);
        lnOptionAboutUs = rootView.findViewById(R.id.lnOptionAboutUs);
        lnOptionChangePass = rootView.findViewById(R.id.lnOptionChangePass);
        lnOptionTransactionHistory = rootView.findViewById(R.id.lnOptionTransactionHistory);
        lnOptionManagementUser = rootView.findViewById(R.id.lnOptionManagementUser);
        lnOptionManagementUser.setOnClickListener(this);
        lnOptionLogOut.setOnClickListener(this);
        lnOptionChangePass.setOnClickListener(this);
        lnOptionInfoUser.setOnClickListener(this);
        imgUpdateUserImage.setOnClickListener(this);
        imgAvatarUser.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        APIService apiService = RetrofitClient.getInstance().create(APIService.class);
        switch (v.getId()) {
            case R.id.lnOptionLogOut:
                new SharedPreferencesToken(getActivity()).loginOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.imgUpdateUserImage:
                chooseCamera();
                imgUpdateUserImage.setVisibility(View.GONE);
                break;
            case R.id.lnOptionInfoUser:
                View view = getLayoutInflater().inflate(R.layout.bsdialog_update_user, null);
                BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
                dialog.setContentView(view);
                TextInputEditText edFullNameUS = view.findViewById(R.id.edFullNameUS);
                TextInputEditText ednumberPhoneUS = view.findViewById(R.id.ednumberPhoneUS);
                eddateOfbirthUS = view.findViewById(R.id.eddateOfbirthUS);
                TextInputEditText edbioUS = view.findViewById(R.id.edbioUS);
                TextInputEditText edAddressUS = view.findViewById(R.id.edAddressUS);
                edFullNameUS.setText(userInfoList.get(0).getFullName());
                ednumberPhoneUS.setText(userInfoList.get(0).getNumberPhone());
                eddateOfbirthUS.setText(userInfoList.get(0).getDateOfbirth());
                edbioUS.setText(userInfoList.get(0).getBio());
                edAddressUS.setText(userInfoList.get(0).getAddress());
                Button btnStartUpdateUser = view.findViewById(R.id.btnStartUpdateUser);
                eddateOfbirthUS.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDatePickerDialog();
                    }
                });
                btnStartUpdateUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (edFullNameUS.getText().toString().isEmpty() || ednumberPhoneUS.getText().toString().isEmpty() || eddateOfbirthUS.getText().toString().isEmpty() || edbioUS.getText().toString().isEmpty() || edAddressUS.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Please complete all fields", Toast.LENGTH_SHORT).show();
                        } else {
                            apiService.updateInfoUser(
                                    userInfoList.get(0).getId(),
                                    RequestBody.create(MediaType.parse("text/plain"), edFullNameUS.getText().toString()),
                                    RequestBody.create(MediaType.parse("text/plain"), ednumberPhoneUS.getText().toString()),
                                    RequestBody.create(MediaType.parse("text/plain"), eddateOfbirthUS.getText().toString()),
                                    RequestBody.create(MediaType.parse("text/plain"), edbioUS.getText().toString()),
                                    RequestBody.create(MediaType.parse("text/plain"), edAddressUS.getText().toString())).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                                    if (response.isSuccessful()) {
                                        FragmentTransaction tr = getFragmentManager().beginTransaction();
                                        tr.replace(R.id.container, ProfileFragment.newInstance("", ""));
                                        tr.commit();
                                        Log.d("DULIEU", "onResponse: Update User Info Success");
                                    }
                                }

                                @Override
                                public void onFailure(@NotNull Call<ResponseBody> call, Throwable t) {
                                    Log.d("DULIEU", Objects.requireNonNull(t.getMessage()));
                                }
                            });
                            dialog.dismiss();
                        }

                    }
                });
                dialog.show();
                break;
            case R.id.lnOptionChangePass:
                View view1 = getLayoutInflater().inflate(R.layout.bsdialog_changepassworduser, null);

                BottomSheetDialog dialog1 = new BottomSheetDialog(getActivity());
                dialog1.setContentView(view1);
                TextInputEditText edcurrentPassword = view1.findViewById(R.id.edcurrentPassword);
                TextInputEditText edNewChangePassword = view1.findViewById(R.id.edNewChangePassword);
                TextInputEditText edConfirmNewChangePassword = view1.findViewById(R.id.edConfirmNewChangePassword);
                Button btnStartChangePassUser = view1.findViewById(R.id.btnStartChangePassUser);
                btnStartChangePassUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (edcurrentPassword.getText().toString().isEmpty() || edNewChangePassword.getText().toString().isEmpty() || edConfirmNewChangePassword.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ các trường", Toast.LENGTH_SHORT).show();
                        } else if (!edNewChangePassword.getText().toString().equals(edConfirmNewChangePassword.getText().toString())) {
                            Toast.makeText(getActivity(), "Nhâp lại mật khẩu", Toast.LENGTH_SHORT).show();
                        } else {
                            apiService.changePassword(userInfoList.get(0).getId(), edcurrentPassword.getText().toString(), edNewChangePassword.getText().toString()).enqueue(new Callback<UserResetPass>() {
                                @Override
                                public void onResponse(Call<UserResetPass> call, Response<UserResetPass> response) {
                                    if (response.body().getStatus()) {
                                        Toast.makeText(getActivity(), "Thành công", Toast.LENGTH_SHORT).show();
                                        dialog1.dismiss();
                                    } else {
                                        Toast.makeText(getActivity(), "Nhập lại mật khẩu cũ", Toast.LENGTH_SHORT).show();
                                    }
                                    Log.d("DULIEU", "onResponse: " + response.body().getMessage());
                                }

                                @Override
                                public void onFailure(Call<UserResetPass> call, Throwable t) {
                                    Log.d("DULIEU", "onResponse: " + t.getMessage());

                                }
                            });
                        }

                    }
                });
                dialog1.show();
                break;
            case R.id.lnOptionManagementUser:
                startActivity(new Intent(getActivity(), UserManagementActivity.class));
                break;
            case R.id.imgAvatarUser:
                imgUpdateUserImage.setVisibility(View.VISIBLE);
                break;
            default:
                Toast.makeText(getActivity(), "Error Onclick", Toast.LENGTH_SHORT).show();
        }
    }

    private void getSharedPreferencesToken(String checkLogin) {
        APIService apiService = RetrofitClient.getInstance().create(APIService.class);
        apiService.getTokenAPI(checkLogin).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (response.body() != null) {
                    userInfoList.add(new UserInfo(response.body().getId(),
                            response.body().getFullName(),
                            response.body().getEmail(),
                            response.body().getHashedPassword(),
                            response.body().getNumberPhone(),
                            response.body().getDateOfbirth(),
                            response.body().getBio(),
                            response.body().getAddress(),
                            response.body().getAvatar()));
                    if (response.body().getPermission()) {
                        lnOptionManagementUser.setVisibility(View.VISIBLE);
                    }
                    tvProfileFullName.setText(response.body().getFullName());
                    tvBio.setText(response.body().getBio());
                    Glide
                            .with(getActivity())
                            .load(Constants.ROOT_URL + "/uploads/" + response.body().getAvatar())
                            .error(R.drawable.placeholder_avatar)
                            .centerCrop()
                            .into(imgAvatarUser);
                    Log.d("DULIEU", "onResponse: " + Constants.ROOT_URL + "/uploads/" + response.body().getAvatar());
//                    }
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.d("DULIEU", "onFailure MainActivity: " + t.getMessage().toString());
            }
        });
//        Log.d("DULIEU", "getSharedPreferencesToken: " + checkLogin);
    }

    private void setUpCamera() {
        easyImage = new EasyImage.Builder(getActivity())
                .setChooserTitle("Pick media")
                .setCopyImagesToPublicGalleryFolder(false)
                .setChooserType(ChooserType.CAMERA_AND_GALLERY)
                .setFolderName("FoodAssigment")
                .allowMultiple(true)
                .build();
    }

    private void UpdateAvatarUser(APIService apiService) {
        if (byteArray != null) {
            try {
                File filesDir = getActivity().getFilesDir();
                File file = new File(filesDir, "IMG_" + Calendar.getInstance().getTimeInMillis() + ".png");
                FileOutputStream fos = null;
                fos = new FileOutputStream(file);
                fos.write(byteArray);
                fos.flush();
                fos.close();
                ProgressRequestBody fileBody = new ProgressRequestBody(file, this);
                apiService.updateAvatarUser(
                        userInfoList.get(0).getId(),
                        MultipartBody.Part.createFormData("upload", file.getName(), fileBody),
                        RequestBody.create(MediaType.parse("text/plain"), file.getName())).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            FragmentTransaction tr = getFragmentManager().beginTransaction();
                            tr.replace(R.id.container, ProfileFragment.newInstance("", ""));
                            tr.commit();
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
            Toast.makeText(getActivity(), "Please attach picture before add data!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CHOOSER_PERMISSIONS_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            easyImage.openChooser(getActivity());
        } else if (requestCode == CAMERA_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            easyImage.openCameraForImage(getActivity());
        } else if (requestCode == DOCUMENTS_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            easyImage.openDocuments(getActivity());
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        easyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onMediaFilesPicked(MediaFile[] imageFiles, MediaSource source) {
                if (resultCode == Activity.RESULT_OK) {
                    String filePath = imageFiles[0].getFile().getPath();
//                    nameAvatar = imageFiles[0].getFile().getName();

                    if (filePath != null) {
                        mBitmap = BitmapFactory.decodeFile(filePath);
                        Log.d("DULIEU", "onMediaFilesPicked: filePath:" + filePath + "-----" + "mBitmap: " + mBitmap);
                        imgAvatarUser.setImageBitmap(mBitmap);
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
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvStatusUploadAvatar.setText("Selected");
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                tvStatusUploadAvatar.setVisibility(View.VISIBLE);
                String[] necessaryPermissions = new String[]{Manifest.permission.CAMERA};
                if (arePermissionsGranted(necessaryPermissions)) {
                    easyImage.openCameraForImage(getActivity());
                } else {
                    requestPermissionsCompat(necessaryPermissions, CAMERA_REQUEST_CODE);
                }
            }
        });
        builder.setNegativeButton("DOCUMENTS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tvStatusUploadAvatar.setVisibility(View.VISIBLE);
                String[] necessaryPermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
                if (arePermissionsGranted(necessaryPermissions)) {
                    easyImage.openDocuments(getActivity());
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
            if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    private void requestPermissionsCompat(String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(getActivity(), permissions, requestCode);
    }

    @Override
    public void onProgressUpdate(int percentage) {
        tvStatusUploadAvatar.setText("Waiting " + percentage + "%");
    }

    @Override
    public void onError() {
        tvStatusUploadAvatar.setText("Can't upload this file");
    }

    @Override
    public void onFinish() {
        tvStatusUploadAvatar.setText("Adding success");
        tvStatusUploadAvatar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void uploadStart() {
        tvStatusUploadAvatar.setText("Starting...");
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
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