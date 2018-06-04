package com.practice.happy.androiddatastorage.db.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.practice.happy.androiddatastorage.db.data.MySQLiteOpenHelper;

/**
 * http://developer.android.youdaxue.com/guide/topics/providers/content-provider-creating.html#ContentURI
 * Created by Happy on 2017/10/21.
 * ContentProviders
 * 1、Good abstraction layer between data source & UI code(can add data validation,
 * can modify how data is stored and UI code is unaffected)
 * 2、work well with other android framework classes
 * 3、can easily share data with other apps
 */

public class BookProvider extends ContentProvider {

    public static final String LOG_TAG = "BookProvider";
    // Create a UriMatcher Object
    public static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private MySQLiteOpenHelper helper;

    static {
        sUriMatcher.addURI("","",1);
    }
    @Override
    public boolean onCreate() {
        helper = new MySQLiteOpenHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
