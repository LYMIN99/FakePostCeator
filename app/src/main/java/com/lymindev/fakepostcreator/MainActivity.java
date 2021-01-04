package com.lymindev.fakepostcreator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.lymindev.fakepostcreator.databinding.ActivityMainBinding;
import com.lymindev.fakepostcreator.view.BaseActivity;
import com.lymindev.fakepostcreator.view.FacebookPostActivity;
import com.lymindev.fakepostcreator.view.activities.MenuListActivity;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private String TAG = "ASS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        binding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MenuListActivity.class));
            }
        });

        binding.btnRateUs.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.lymindev.fakepostcreator"));
            startActivity(intent);
        });

        binding.btnMoreApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/developer?id=LY+MIN"));
                startActivity(intent);
            }
        });
    }
}