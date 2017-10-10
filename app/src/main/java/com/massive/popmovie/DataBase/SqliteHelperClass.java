package com.massive.popmovie.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.massive.popmovie.Utlis.Constant;


public class SqliteHelperClass extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DbMovies";
    private static final int DATABASE_VERSION = 1;

    public SqliteHelperClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String creatTable = "CREATE TABLE " + Constant.Entry.DATABASE_TABLE + " ("
                + Constant.Entry._ID + " INTEGER AUTO_INCREMENT PRIMARY KEY,"
                + Constant.Entry.ID + " INTEGER,"
                + Constant.Entry.NAME + " varchar(100),"
                + Constant.Entry.POSTER_URL + " varchar(255),"
                + Constant.Entry.DATE + " varchar(100),"
                + Constant.Entry.VOTE + " varchar(20),"
                + Constant.Entry.OVERVIEW + " varchar(255)"
                + " )";
        sqLiteDatabase.execSQL(creatTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constant.Entry.DATABASE_TABLE);
        onCreate(sqLiteDatabase);
    }


}
