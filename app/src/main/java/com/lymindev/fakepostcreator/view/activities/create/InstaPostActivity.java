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
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.lymindev.fakepostcreator.R;
import com.lymindev.fakepostcreator.databinding.ActivityInstaPostBinding;
import com.lymindev.fakepostcreator.view.bottomsheet.SetValueBottomSheet;
import com.lymindev.fakepostcreator.view.manager.PostsManager;
import com.lymindev.fakepostcreator.view.model.PostItem;
import com.lymindev.fakepostcreator.view.utils.ImageUtils;
import com.lymindev.fakepostcreator.view.utils.Utils;

import java.util.Random;

import static com.lymindev.fakepostcreator.view.constance.Constance.RequsetCode.IMAGE_POST_REQUEST;
import static com.lymindev.fakepostcreator.view.constance.Constance.RequsetCode.IMAGE_PROFILE_REQUEST;

public class InstaPostActivity extends AppCompatActivity {

    private ActivityInstaPostBinding binding;
    int id = new Random().nextInt(9999);
    private Bitmap bitmapProfile,bitmapPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_insta_post);

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
        binding.btnShare.setOnClickListener(view -> {

            ProgressDialog progressDialog = new ProgressDialog(InstaPostActivity.this);
            progressDialog.setMessage("Creating your post...");
            progressDialog.show();
            new Handler().post(() -> {
                create(id);
                progressDialog.dismiss();
                // startActivity(new Intent(IncomingPhoneCallActivity.this, ReviewFacebookPostActivity.class).putExtra("id", id));
            });
        });
        binding.btnBack.setOnClickListener(v -> finish());
        binding.tvName.setOnClickListener(v -> new SetValueBottomSheet(InstaPostActivity.this, binding.tvName.getText().toString(), s -> binding.tvName.setText(s)));

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
        item.setDate(Utils.getCurrentDateTime());
        item.setFullname(binding.tvName.getText().toString());
        item.setLikes("");
        item.setLikeType("");
        item.setType("InstagramPost");
        if (bitmapProfile!=null) {
            item.setProfile(ImageUtils.getByteFromBitmap(bitmapProfile));
        }
        if (bitmapPost!=null) {
            item.setPhotoPost(ImageUtils.getByteFromBitmap(bitmapPost));
            item.setImagePost(true);
        } else {
            item.setImagePost(false);
        }
        //item.setPhotoPost(null);
        item.setComments("");
        item.setShares("");
        item.setDescription("");
        new PostsManager().addPostData(item);
        View postView = binding.layoutPost;
        Bitmap bitmap = ImageUtils.loadBitmapFromView(postView, postView.getWidth(), postView.getHeight());
        Uri uri = ImageUtils.getImageUriFromBitmap(InstaPostActivity.this, bitmap);
        if (uri != null) {
            String description = "WOW!!";
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, description);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setType("image/*");
            startActivity(Intent.createChooser(shareIntent, "Share Image"));
        }
    }
}