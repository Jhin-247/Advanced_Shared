package com.tuan.sharedpreference.view.main;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.tuan.sharedpreference.content_provider.MyContentProvider;
import com.tuan.sharedpreference.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        ContentResolver contentResolver = getContentResolver();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentResolver.call(MyContentProvider.AUTHORITY,"123123",null,null);
        }
    }
}