package com.lee.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lee.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by li on 2016/5/8.
 */
public class GDdb {
    private String tag = "GDdb";
    public static final String DB_NAME = "GD.db";
    public static final int VERSION = 1;
    private static GDdb gdDb;
    private SQLiteDatabase db;
    private String stuNum;
    private GDdb(Context context){
        GDDatabaseHelper dbHelper = new GDDatabaseHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }
    public static GDdb getInstance(Context context){
        if (gdDb==null) {
            gdDb = new GDdb(context);
        }
        return gdDb;
    }
    // 用户登录，添加用户信息
    public void saveUser(User user) {
        if (user != null) {
            ContentValues values = new ContentValues();
            values.put("stuNum", user.getStuNum());
            values.put("userName", user.getUserName());
            values.put("class_", user.getClass_());
            values.put("sex", user.getSex());
            values.put("role", user.getRole());
            db.insert("user_table", null, values);
        }
    }
    //del User
    public void delUser(){
        db.delete("user_table", null, null);
    }
    // 加载本人的信息
    public List<User> loadUser() {
        List<User> list = new ArrayList<User>();
        Cursor cursor = db.rawQuery("select * from user_table", null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndex("id")));
                user.setStuNum(cursor.getString(cursor.getColumnIndex("stuNum")));
                user.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
                user.setRole(cursor.getString(cursor.getColumnIndex("role")));
                user.setClass_(cursor.getString(cursor.getColumnIndex("class_")));
                user.setSex(cursor.getString(cursor.getColumnIndex("sex")));
                list.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
    //获取stu_num
    public String getStuNum(){
        Cursor cursor = db.rawQuery("select * from user_table", null);
        if (cursor.moveToFirst()) {
            do {
                stuNum = cursor.getString(cursor.getColumnIndex("stuNum"));
            }while (cursor.moveToNext());
        }
        return stuNum;
    }
    // 修改sex
    public void updateSex(String sex, String stuNum) {
        Log.d(tag, sex);
        ContentValues values = new ContentValues();
        values.put("sex", sex);
        db.update("user_table", values, "stuNum = ?", new String[] { stuNum });
    }
    // 修改userName
    public void updateName(String userName, String stuNum) {
        Log.d(tag, userName);
        ContentValues values = new ContentValues();
        values.put("userName", userName);
        db.update("user_table", values, "stuNum = ?", new String[] { stuNum });
    }
    // 修改class_
    public void updateClass(String class_, String stuNum) {
        Log.d(tag, class_);
        ContentValues values = new ContentValues();
        values.put("class_", class_);
        db.update("user_table", values, "stuNum = ?", new String[] { stuNum });
    }
    // 修改role
    public void updateRole(String role, String stuNum) {
        Log.d(tag, role);
        ContentValues values = new ContentValues();
        values.put("role", role);
        db.update("user_table", values, "stuNum = ?", new String[] { stuNum });
    }



}
