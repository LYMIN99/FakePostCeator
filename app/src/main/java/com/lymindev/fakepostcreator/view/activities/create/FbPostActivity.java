package com.lymindev.fakepostcreator.view.activities.create;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.lymindev.fakepostcreator.R;
import com.lymindev.fakepostcreator.databinding.ActivityFacebookPostBinding;
import com.lymindev.fakepostcreator.databinding.ActivityFbPostBinding;
import com.lymindev.fakepostcreator.view.FacebookPostActivity;
import com.lymindev.fakepostcreator.view.ReviewFacebookPostActivity;
import com.lymindev.fakepostcreator.view.manager.PostsManager;
import com.lymindev.fakepostcreator.view.model.PostItem;
import com.lymindev.fakepostcreator.view.utils.ImageUtils;

import java.util.Random;

import static com.lymindev.fakepostcreator.view.constance.Constance.RequsetCode.IMAGE_GALLERY_REQUEST;
import static com.lymindev.fakepostcreator.view.constance.Constance.RequsetCode.IMAGE_POST_REQUEST;
import static com.lymindev.fakepostcreator.view.constance.Constance.RequsetCode.IMAGE_PROFILE_REQUEST;

public class FbPostActivity extends AppCompatActivity {

    private ActivityFbPostBinding binding;
    private Bitmap bitmapProfile,bitmapPost;
    private String likeType = "Single";
    int id = new Random().nextInt(9999);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_fb_post);

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        MobileAds.initialize(this, initializationStatus -> {
        });
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        initClick();
    }
    private void initClick() {
        binding.imageProfile.setOnClickListener(view -> checkPermissionOpenCamera(IMAGE_PROFILE_REQUEST));
        binding.imagePost.setOnClickListener(view -> checkPermissionOpenCamera(IMAGE_POST_REQUEST));
        binding.btnCreate.setOnClickListener(view -> {
            if (isValidate()){
                ProgressDialog progressDialog = new ProgressDialog(FbPostActivity.this);
                progressDialog.setMessage("Creating your post...");
                progressDialog.show();
                new Handler().post(() -> {
                    create(id);
                    progressDialog.dismiss();
                    startActivity(new Intent(FbPostActivity.this, ReviewFacebookPostActivity.class).putExtra("id",id));
                });

            }
        });
        binding.toolbar.setNavigationOnClickListener(v -> finish());

    }
    private void checkPermissionOpenCamera(int reqCode) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    new Random().nextInt(999));
        } else {
            openGallery(reqCode);
        }
    }

    private void openGallery(int reqCode){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select image"),  reqCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PROFILE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null){

            Uri uri = data.getData();
            try {
                bitmapProfile = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                binding.imageProfile.setImageBitmap(bitmapProfile);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        if (requestCode == IMAGE_POST_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null){

            Uri uri = data.getData();
            try {
                bitmapPost = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                binding.imagePost.setImageBitmap(bitmapPost);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
    private void create(int id){
        PostItem item = new PostItem();
        item.setId(id);
        item.setDate(binding.tvDate.getText().toString());
        item.setFullname(binding.edName.getText().toString());
        item.setLikes(binding.edLikes.getText().toString());
        item.setLikeType(likeType);
        item.setType("Facebook");
        item.setProfile(ImageUtils.getByteFromBitmap(bitmapProfile));
        if (bitmapPost!=null){
            item.setImagePost(true);
            item.setPhotoPost(ImageUtils.getByteFromBitmap(bitmapPost));
        } else {
            item.setImagePost(false);
            item.setPhotoPost(null);
        }
        item.setComments(binding.edCmt.getText().toString());
        item.setShares(binding.edShare.getText().toString());
        item.setDescription(binding.edDescrption.getText().toString());

        new PostsManager().addPostData(item);
    }
    private boolean isValidate(){
        boolean b = false;
        if (TextUtils.isEmpty(binding.edName.getText().toString())){
            binding.edName.setError("Field is required");
        } else if (bitmapProfile==null){
            Toast.makeText(getApplicationContext(),"Profile is empty",Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(binding.edLikes.getText().toString())){
            binding.edLikes.setError("Field is required");
        }else {
            b = true;
        }
        return b;
    }
}