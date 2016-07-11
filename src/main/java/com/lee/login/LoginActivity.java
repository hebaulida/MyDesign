package com.lee.login;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lee.bean.User;
import com.lee.constant.Url;
import com.lee.db.GDDatabaseHelper;
import com.lee.db.GDdb;
import com.lee.myapplication.MainActivity;
import com.lee.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends Activity{
    private String url = Url.Login;
    private EditText stuEdit;
    private EditText passEdit;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private GDDatabaseHelper dbHelper;
    private GDdb mGDdb;

    private String userdata;
    private String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new GDDatabaseHelper(this, "GD.db",null , 1);
        dbHelper.getWritableDatabase();
        mGDdb = GDdb.getInstance(this);

        //检测是否成功登录过，true-直接进入
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isAutoLogin = pref.getBoolean("auto_login", false);
        if (isAutoLogin) {
//            Log.d("loginActivity", "已经登录过");
            Intent intent3 = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent3);
            finish();
        }else {
//            Log.d("loginActivity", "没有登录用户");
        }

        stuEdit = (EditText) findViewById(R.id.stu_num);
        passEdit = (EditText) findViewById(R.id.password);
        Button loginBt = (Button) findViewById(R.id.login_button);
        loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stuNum = stuEdit.getText().toString().trim();
                String passWd = passEdit.getText().toString().trim();
                login(stuNum,passWd);
            }
        });
        Button reg = (Button) findViewById(R.id.reg_button);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });
    }

    private void login(final String stuNum, final String pass){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            flag = jsonObject.getString("flag");
                            userdata = jsonObject.getString("user");
                            if(flag.equals("0")){
                               Toast.makeText(getApplicationContext(), "学号密码错误", Toast.LENGTH_SHORT).show();
                            }else if(flag.equals("1")) {
                                User oldUser = new User();
                                JSONObject jb = new JSONObject(userdata);

                                oldUser.setStuNum(jb.getString("stuNum"));
                                oldUser.setUserName(jb.getString("userName"));
                                oldUser.setSex(jb.getString("sex"));
                                oldUser.setClass_(jb.getString("class_"));
                                oldUser.setRole(jb.getString("role"));
                                //清空用户表
                                mGDdb.delUser();
                                //添加用户信息
                                mGDdb.saveUser(oldUser);

                                pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                editor = pref.edit();
                                editor.putBoolean("auto_login", true);
                                editor.commit();

                                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(main);
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("upLoad error", error.getMessage(), error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                map.put("stuNum", stuNum);
                map.put("pass", pass);
                return map;
            }

        };
        requestQueue.add(stringRequest);


    }


}

