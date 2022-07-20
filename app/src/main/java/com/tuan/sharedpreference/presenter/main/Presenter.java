package com.tuan.sharedpreference.presenter.main;

import static com.tuan.sharedpreference.utils.Constants.SharedConstants.ADDRESS;
import static com.tuan.sharedpreference.utils.Constants.SharedConstants.FILE_NAME;
import static com.tuan.sharedpreference.utils.Constants.SharedConstants.PASSWORD;
import static com.tuan.sharedpreference.utils.Constants.SharedConstants.PAYMENT_INFO;
import static com.tuan.sharedpreference.utils.Constants.SharedConstants.USERNAME;
import static com.tuan.sharedpreference.utils.Constants.SharedConstants.WEBSITE;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.tuan.sharedpreference.databinding.ActivityMainBinding;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class Presenter implements Contract.Presenter {
    private Contract.View mView;
    private Context mContext;

    public Presenter(Context context) {
        this.mContext = context;
    }

    public void setView(Contract.View view) {
        this.mView = view;
    }

    @Override
    public void onSaveClick(ActivityMainBinding binding) {
        if (TextUtils.isEmpty(binding.etUsername.getText())) {
            mView.onMissingInformation();
            return;
        }
        if (TextUtils.isEmpty(binding.etPassword.getText())) {
            mView.onMissingInformation();
            return;
        }
        if (TextUtils.isEmpty(binding.etWebsite.getText())) {
            mView.onMissingInformation();
            return;
        }
        if (TextUtils.isEmpty(binding.etAddress.getText())) {
            mView.onMissingInformation();
            return;
        }
        if (TextUtils.isEmpty(binding.spPaymentMethod.getSelectedItem().toString())) {
            mView.onMissingInformation();
            return;
        }

        performSave(binding);

    }

    private void performSave(ActivityMainBinding binding) {
        try {
            MasterKey masterKey = new MasterKey.Builder(mContext, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();
            SharedPreferences mPref = EncryptedSharedPreferences.create(
                    mContext,
                    FILE_NAME,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            mPref.edit()
                    .putString(USERNAME, binding.etUsername.getText().toString())
                    .putString(WEBSITE, binding.etWebsite.getText().toString())
                    .putString(PASSWORD, binding.etPassword.getText().toString())
                    .putString(PAYMENT_INFO, binding.spPaymentMethod.getSelectedItem().toString())
                    .putString(ADDRESS,binding.etAddress.getText().toString())
                    .apply();
            mView.onSaveSuccess();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            mView.onSaveFailed();
        }
    }
}
