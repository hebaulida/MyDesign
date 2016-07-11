package com.lee.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by li on 2016/5/8.
 */
public class GDDatabaseHelper extends SQLiteOpenHelper{
    public GDDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //建表语句
    public static final String CREATE_USER="create table if not exists user_table("
            +"id integer primary key autoincrement,"
            +"stuNum text,"
            +"userName text,"
            +"sex text,"
            +"class_ text,"
            +"role text)";
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
