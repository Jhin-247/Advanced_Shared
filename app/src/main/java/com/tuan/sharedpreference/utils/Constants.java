package com.tuan.sharedpreference.utils;

import android.content.ContentResolver;
import android.net.Uri;

public class Constants {
    public static final String AUTHORITY = "com.tuan.sharedpreference";
    public static final String GET_ALL_SHARED_PREF = "account";
    public static final String INSERT_DATA = "insert";
    public static final String DELETE_DATA = "delete";

    public static final Uri CONTENT_URI_GET_ALL = Uri.parse("content://" + AUTHORITY + "/" + GET_ALL_SHARED_PREF);
    public static final Uri CONTENT_URI_INSERT = Uri.parse("content://" + AUTHORITY + "/" + INSERT_DATA);
    public static final Uri CONTENT_URI_DELETE = Uri.parse("content://" + AUTHORITY + "/" + DELETE_DATA);

    public static final String MIME_TYPE_1 = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.tuan." + GET_ALL_SHARED_PREF;
    public static final String MIME_TYPE_2 = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.com.tuan." + INSERT_DATA;
    public static final String MIME_TYPE_3 = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.tuan." + DELETE_DATA;

    public static class SharedConstants {
        public static final String FILE_NAME = "my_pref";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String WEBSITE = "website";
        public static final String ADDRESS = "address";
        public static final String PAYMENT_INFO = "payment_info";
    }
}
