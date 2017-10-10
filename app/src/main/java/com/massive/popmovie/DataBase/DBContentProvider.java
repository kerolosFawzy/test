package com.massive.popmovie.DataBase;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.massive.popmovie.Utlis.Constant;
import com.massive.popmovie.view.MainActivity;

import java.util.regex.Matcher;

/**
 * Created by minafaw on 10/10/2017.
 */

public class DBContentProvider extends ContentProvider {
    private SqliteHelperClass sqliteHelperClass;
    public static final int TASKS = 100;
    public static final int TASK_WITH_ID = 101;
    private SqliteHelperClass mDbHelper;
    public static UriMatcher matcher = uriMatcher();

    @Override
    public boolean onCreate() {
        Context context = getContext();
        sqliteHelperClass = new SqliteHelperClass(context);
        mDbHelper = new SqliteHelperClass(context);
        return true;
    }

    public static UriMatcher uriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(Constant.AUTHORTY, Constant.PATH, TASKS);
        matcher.addURI(Constant.AUTHORTY, Constant.PATH + "/#", TASK_WITH_ID);

        return matcher;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase database=mDbHelper.getReadableDatabase();

        int match=matcher.match(uri);

         Cursor returnCruser;

        switch (match){
            case TASKS:
                returnCruser=database.query(Constant.Entry.DATABASE_TABLE,projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        returnCruser.setNotificationUri(getContext().getContentResolver(),uri);
        return returnCruser;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase liteDatabase = mDbHelper.getWritableDatabase();
        int match = matcher.match(uri);
        Uri returnUri;
        switch (match) {
            case TASKS:
                long id = liteDatabase.insert(Constant.Entry.DATABASE_TABLE, null, contentValues);
                if (id > 0)
                    returnUri = ContentUris.withAppendedId(Constant.Entry.FULL_URI, id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase liteDatabase = mDbHelper.getWritableDatabase();
        int match = matcher.match(uri);
        int deleted;

        switch (match) {
            case TASK_WITH_ID:
                String id = uri.getPathSegments().get(1);
                deleted = liteDatabase.delete(Constant.Entry.DATABASE_TABLE, "ID=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (deleted != 0) {
            // A task was deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of tasks deleted
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
