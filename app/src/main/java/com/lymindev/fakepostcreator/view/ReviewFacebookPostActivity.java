package com.lymindev.fakepostcreator.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.lymindev.fakepostcreator.R;
import com.lymindev.fakepostcreator.databinding.ActivityReviewFacebookPostBinding;
import com.lymindev.fakepostcreator.view.manager.PostsManager;
import com.lymindev.fakepostcreator.view.model.PostItem;
import com.lymindev.fakepostcreator.view.utils.ImageUtils;

public class ReviewFacebookPostActivity extends BaseActivity {

    private ActivityReviewFacebookPostBinding binding;
    private int id;
    private InterstitialAd mInterstitialAd;
    private boolean isLoadAd = false;
    private String TAG = "REVIEW FB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_review_facebook_post);

        id = getIntent().getIntExtra("id",0);
        //initAdsIntersitial();
        getInfo();
        initClick();
    }

    private void initAdsIntersitial() {
        MobileAds.initialize(this, initializationStatus -> {
        });

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.ad_interstial_id));
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    private void getInfo() {
        PostItem item = new PostsManager().getPostData(id);
        Glide.with(this).load(ImageUtils.getBitmapFromByteArray(item.getProfile())).override(200,200).into(binding.imageProfile);
        binding.tvName.setText(item.getFullname());
        binding.tvDescrption.setText(item.getDescription());
        binding.tvLikes.setText(item.getLikes());
        binding.tvShares.setText(item.getShares()+" shares");
        binding.tvDate.setText(item.getDate()+" . ");
        binding.tvComments.setText(item.getComments()+" comments .");
        if (item.isImagePost()){
            Glide.with(this).load(ImageUtils.getBitmapFromByteArray(item.getPhotoPost())).into(binding.imagePost);
        } else {
            binding.imagePost.setVisibility(View.GONE);
        }
    }

    private void initClick(){
        binding.btnShare.setOnClickListener(view -> {
//                if (isLoadAd) {
                View postView = binding.layoutPost;
                Bitmap bitmap = ImageUtils.loadBitmapFromView(postView, postView.getWidth(), postView.getHeight());
                Uri uri = ImageUtils.getImageUriFromBitmap(ReviewFacebookPostActivity.this, bitmap);
                if (uri != null) {

                    String description = "WOW!!";
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, description);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    shareIntent.setType("image/*");
                    startActivity(Intent.createChooser(shareIntent, "Share Image"));
                }
//                }
//                else {
//                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
//                    mInterstitialAd.show();
//
//                    mInterstitialAd.setAdListener(new AdListener() {
//                        @Override
//                        public void onAdLoaded() {
//                            // Code to be executed when an ad finishes loading.
//                            Log.d(TAG, "onAdLoaded: ===> ");
//                        }
//
//                        @Override
//                        public void onAdFailedToLoad(LoadAdError adError) {
//                            // Code to be executed when an ad request fails.
//                            Log.d(TAG, "onAdFailedToLoad:  ===> ");
//                        }
//
//                        @Override
//                        public void onAdOpened() {
//                            // Code to be executed when the ad is displayed.
//                            Log.d(TAG, "onAdOpened:  ===> ");
//                        }
//
//                        @Override
//                        public void onAdClicked() {
//                            // Code to be executed when the user clicks on an ad.
//                            Log.d(TAG, "onAdClicked:  ===> ");
//                        }
//
//                        @Override
//                        public void onAdLeftApplication() {
//                            // Code to be executed when the user has left the app.
//                            Log.d(TAG, "onAdLeftApplication:  ===> ");
//                        }
//
//                        @Override
//                        public void onAdClosed() {
//                            // Code to be executed when the interstitial ad is closed.
//                            Log.d(TAG, "onAdClosed:  ===> ");
//                        }
//                    });
//
//                    isLoadAd = true;
//                }
        });

        binding.btnRate.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.lymindev.fakepostcreator"));
            startActivity(intent);
        });
    }
}