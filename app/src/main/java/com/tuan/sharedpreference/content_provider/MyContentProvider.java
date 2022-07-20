package com.tuan.sharedpreference.content_provider;

import static com.tuan.sharedpreference.utils.Constants.AUTHORITY;
import static com.tuan.sharedpreference.utils.Constants.DELETE_DATA;
import static com.tuan.sharedpreference.utils.Constants.GET_ALL_SHARED_PREF;
import static com.tuan.sharedpreference.utils.Constants.INSERT_DATA;
import static com.tuan.sharedpreference.utils.Constants.SharedConstants.ADDRESS;
import static com.tuan.sharedpreference.utils.Constants.SharedConstants.FILE_NAME;
import static com.tuan.sharedpreference.utils.Constants.SharedConstants.PASSWORD;
import static com.tuan.sharedpreference.utils.Constants.SharedConstants.PAYMENT_INFO;
import static com.tuan.sharedpreference.utils.Constants.SharedConstants.USERNAME;
import static com.tuan.sharedpreference.utils.Constants.SharedConstants.WEBSITE;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.UserHandle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class MyContentProvider extends ContentProvider {
    private static final String TAG = "MyContentProvider";

    private static final int GET_ALL = 1;
    private static final int INSERT = 2;
    private static final int DELETE = 3;

    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, GET_ALL_SHARED_PREF, GET_ALL);
        MATCHER.addURI(AUTHORITY, INSERT_DATA, INSERT);
        MATCHER.addURI(AUTHORITY, DELETE_DATA, DELETE);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if (MATCHER.match(uri) == GET_ALL) {
            return getSharedPref();
        }


        return null;
    }

    private Cursor getSharedPref() {
        String[] columns = new String[]{USERNAME, PASSWORD, WEBSITE, ADDRESS, PAYMENT_INFO};
        MatrixCursor matrixCursor = new MatrixCursor(columns);
        Context mContext = getContext();
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

            String username = mPref.getString(USERNAME, "UNKNOWN");
            String password = mPref.getString(PASSWORD, "UNKNOWN");
            String website = mPref.getString(WEBSITE, "UNKNOWN");
            String address = mPref.getString(ADDRESS, "UNKNOWN");
            String paymentInfo = mPref.getString(PAYMENT_INFO, "UNKNOWN");

            matrixCursor.addRow(new Object[]{username, password, website, address, paymentInfo});

        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();

        }
        return matrixCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (MATCHER.match(uri) == INSERT && values != null) {
            changePref(values, uri);
        }
        return null;
    }

    private void changePref(ContentValues values, @NonNull Uri uri) {
        Context mContext = getContext();
        try {
            MasterKey masterKey = new MasterKey.Builder(mContext, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();
            SharedPreferences.Editor mPrefEditor = EncryptedSharedPreferences.create(
                    mContext,
                    FILE_NAME,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            ).edit();

            if (values.containsKey(USERNAME)) {
                mPrefEditor.putString(USERNAME, values.get(USERNAME).toString());
            }

            if (values.containsKey(PASSWORD)) {
                mPrefEditor.putString(PASSWORD, values.get(PASSWORD).toString());
            }

            if (values.containsKey(ADDRESS)) {
                mPrefEditor.putString(ADDRESS, values.get(ADDRESS).toString());
            }

            if (values.containsKey(WEBSITE)) {
                mPrefEditor.putString(WEBSITE, values.get(WEBSITE).toString());
            }

            if (values.containsKey(PAYMENT_INFO)) {
                mPrefEditor.putString(PAYMENT_INFO, values.get(PAYMENT_INFO).toString());
            }

            mPrefEditor.apply();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
