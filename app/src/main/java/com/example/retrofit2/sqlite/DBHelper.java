package com.example.retrofit2.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, Constant.DB_NAME, null, Constant.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_TABLE = "CREATE TABLE " +
                Constant.TB_NAME + " (" +
                Constant.ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Constant.LINK + " TEXT NOT NULL, " +
                Constant.MEDIA + " TEXT NOT NULL " + ");";

        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constant.TB_NAME);
        onCreate(db);
    }
}
