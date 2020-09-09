package com.lymindev.fakepostcreator.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.lymindev.fakepostcreator.R;
import com.lymindev.fakepostcreator.databinding.ActivityFacebookPostBinding;
import com.lymindev.fakepostcreator.view.manager.PostsManager;
import com.lymindev.fakepostcreator.view.model.PostItem;
import com.lymindev.fakepostcreator.view.utils.ImageUtils;
import com.lymindev.fakepostcreator.view.utils.Utils;

import java.util.Random;

import static com.lymindev.fakepostcreator.view.constance.Constance.RequsetCode.IMAGE_GALLERY_REQUEST;

public class FacebookPostActivity extends BaseActivity {

    private ActivityFacebookPostBinding binding;
    private  Bitmap bitmap;
    private String likeType = "Single";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_facebook_post);

        MobileAds.initialize(this, initializationStatus -> {
        });


        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        new PostsManager().clearAllData();
        intClick();
    }
    
    private void intClick(){
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidate()){
                    int id = new Random().nextInt(999);
                    create(id);
                    startActivity(new Intent(FacebookPostActivity.this,ReviewFacebookPostActivity.class).putExtra("id",id));
                }
            }
        });

        binding.imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissionOpenCamera();
            }
        });

        binding.tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"SS ",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void checkPermissionOpenCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    new Random().nextInt(999));
        } else {
            openGallery();
        }
    }

    private void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select image"),  IMAGE_GALLERY_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_GALLERY_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null){

            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                binding.imageProfile.setImageBitmap(bitmap);
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
        item.setProfile(ImageUtils.getByteFromBitmap(bitmap));
        item.setComments(binding.edCmt.getText().toString());
        item.setShares(binding.edShare.getText().toString());
        item.setDescription(binding.edDescrption.getText().toString());

         new PostsManager().addPostData(item);
    }
    private boolean isValidate(){
        boolean b = false;
        if (TextUtils.isEmpty(binding.edName.getText().toString())){
            binding.edName.setError("Field is required");
        } else if (bitmap==null){
            Toast.makeText(getApplicationContext(),"Profile is empty",Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(binding.edLikes.getText().toString())){
            binding.edLikes.setError("Field is required");
        }else {
            b = true;
        }

        return b;
    }
}