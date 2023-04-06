package com.example.diary.Login;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDatabase extends SQLiteOpenHelper {
    public static final String CREATE_USER = "create table user("
            + "phoneNumber integer primary key,"
            + "password integer)";

    public static final String CREATE_DIARY = "create table diary ("
            + "title text primary key,"
            + "content text)";
    private Context mContext;

    public UserDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        //db.execSQL(CREATE_DIARY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
