package com.lymindev.fakepostcreator.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.lymindev.fakepostcreator.R;
import com.lymindev.fakepostcreator.databinding.ActivityMenuListBinding;
import com.lymindev.fakepostcreator.view.activities.create.FbPostActivity;
import com.lymindev.fakepostcreator.view.activities.create.IncomingPhoneCallActivity;
import com.lymindev.fakepostcreator.view.activities.create.InstaPostActivity;

public class MenuListActivity extends AppCompatActivity {

    private ActivityMenuListBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_menu_list);
        
        initClick();

    }

    private void initClick() {

        binding.lnFacebookPost.setOnClickListener(v -> startActivity(new Intent(MenuListActivity.this, FbPostActivity.class)));
        binding.lnFacebookMessengerCall.setOnClickListener(v -> Toast.makeText(getApplicationContext(),"Coming soon",Toast.LENGTH_SHORT).show());
        binding.lnInstagramPost.setOnClickListener(v ->  startActivity(new Intent(MenuListActivity.this, InstaPostActivity.class)));
        binding.lnFacebookProfile.setOnClickListener(v -> Toast.makeText(getApplicationContext(),"Coming soon",Toast.LENGTH_SHORT).show());
        binding.lnPhoneCall.setOnClickListener(v -> startActivity(new Intent(MenuListActivity.this, IncomingPhoneCallActivity.class)));
        binding.toolbar.setNavigationOnClickListener(v -> finish());

    }
}