package com.example.baseproject;

import static com.example.baseproject.Constants.CONTENT_URI_GET_ALL;
import static com.example.baseproject.Constants.CONTENT_URI_INSERT;
import static com.example.baseproject.Constants.SharedConstants.ADDRESS;
import static com.example.baseproject.Constants.SharedConstants.PASSWORD;
import static com.example.baseproject.Constants.SharedConstants.PAYMENT_INFO;
import static com.example.baseproject.Constants.SharedConstants.USERNAME;
import static com.example.baseproject.Constants.SharedConstants.WEBSITE;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.baseproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "MainActivity11111";
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

//        mBinding.tvChangePass.setOnClickListener(v -> {
//            ContentValues values = new ContentValues();
//            values.put(PASSWORD, "Awsome1");
//            contentResolver.insert(CONTENT_URI_INSERT, values);
//            LoaderManager.getInstance(this).restartLoader(1, null, this);
//        });

        mBinding.btnMod.setOnClickListener(v -> {
            ContentResolver contentResolver = getContentResolver();
            contentResolver.insert(CONTENT_URI_INSERT, getValues());
            LoaderManager.getInstance(this).restartLoader(1, null, this);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        LoaderManager.getInstance(this).restartLoader(1, null, this);
    }

    private ContentValues getValues() {
        ContentValues values = new ContentValues();
        if (!TextUtils.isEmpty(mBinding.username.getText().toString())) {
            String username = mBinding.username.getText().toString();
            String finalUsername = username.substring(username.indexOf(":") + 2);
            values.put(USERNAME, finalUsername);
        }
        if (!TextUtils.isEmpty(mBinding.password.getText().toString())) {
            String password = mBinding.password.getText().toString();
            String finalPassword = password.substring(password.indexOf(":") + 2);
            values.put(PASSWORD, finalPassword);
        }
        if (!TextUtils.isEmpty(mBinding.website.getText().toString())) {
            String website = mBinding.website.getText().toString();
            String finalWebsite = website.substring(website.indexOf(":") + 2);
            values.put(WEBSITE, finalWebsite);
        }
        if (!TextUtils.isEmpty(mBinding.address.getText().toString())) {
            String address = mBinding.address.getText().toString();
            String finalAddress = address.substring(address.indexOf(":") + 2);
            values.put(ADDRESS, finalAddress);
        }
        if (!TextUtils.isEmpty(mBinding.paymentInfo.getText().toString())) {
            String payment = mBinding.paymentInfo.getText().toString();
            String finalPayment = payment.substring(payment.indexOf(":") + 2);
            values.put(PAYMENT_INFO, finalPayment);
        }

        return values;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, CONTENT_URI_GET_ALL, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (data != null && data.getCount() > 0) {
            while (data.moveToNext()) {
                Log.i(TAG, "onLoadFinished: " + data.getString(data.getColumnIndexOrThrow(PASSWORD)));

                mBinding.username.setText(getResources().getString(R.string.username, data.getString(data.getColumnIndexOrThrow(USERNAME))));
                mBinding.password.setText(getResources().getString(R.string.password, data.getString(data.getColumnIndexOrThrow(PASSWORD))));
                mBinding.website.setText(getResources().getString(R.string.website, data.getString(data.getColumnIndexOrThrow(WEBSITE))));
                mBinding.address.setText(getResources().getString(R.string.address, data.getString(data.getColumnIndexOrThrow(ADDRESS))));
                mBinding.paymentInfo.setText(getResources().getString(R.string.payment_info, data.getString(data.getColumnIndexOrThrow(PAYMENT_INFO))));

            }
        } else {
            mBinding.website.setText(getResources().getString(R.string.empty_data));
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}