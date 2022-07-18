package com.tuan.sharedpreference.content_provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MyContentProvider extends ContentProvider {
    private static final String TAG = "MyContentProvider";
    private SharedPreferences mPref;
    public static final String AUTHORITY = "com.example.retrofitassignment.content_provider";

    public static final String PATH_GET_SHARED_PASSWORD = "GET_SHARED_PASSWORD";
    public static final String PATH_GET_SHARED_ACCOUNT = "GET_SHARED_ACCOUNT";

    public static final Uri URI_CONTENT_1 = Uri.parse("content://" + AUTHORITY + "/" + PATH_GET_SHARED_ACCOUNT);
    public static final Uri URI_CONTENT_2 = Uri.parse("content://" + AUTHORITY + "/" + PATH_GET_SHARED_PASSWORD);

    public static final int GET_SHARED_ACCOUNT = 1;
    public static final int GET_SHARED_PASSWORD = 2;
    public static final String MIME_TYPE_1 = ContentResolver.ANY_CURSOR_ITEM_TYPE + "/" + "vnd.com.tuan.account";
    public static final String MIME_TYPE_2 = ContentResolver.ANY_CURSOR_ITEM_TYPE + "/" + "vnd.com.tuan.password";
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, PATH_GET_SHARED_ACCOUNT, GET_SHARED_ACCOUNT);
        MATCHER.addURI(AUTHORITY, PATH_GET_SHARED_PASSWORD, GET_SHARED_PASSWORD);
    }

    @Override
    public boolean onCreate() {
        mPref = getContext().getSharedPreferences("123123",Context.MODE_PRIVATE);
        Log.i(TAG, "onCreate: " + (mPref == null ? "null" : "not null"));
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public Bundle call(@NonNull String method, @Nullable String arg, @Nullable Bundle extras) {
        initCall();
        return super.call(method, arg, extras);
    }

    private void initCall() {
        Log.i(TAG, "initCall: " + (mPref == null ? "null" : "not null"));
        mPref.edit().putInt("123123",123).apply();
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)) {
            case GET_SHARED_ACCOUNT:
                return MIME_TYPE_1;
            case GET_SHARED_PASSWORD:
                return MIME_TYPE_2;

        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
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
