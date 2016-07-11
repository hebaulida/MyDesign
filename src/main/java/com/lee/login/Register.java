package com.lee.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lee.constant.Url;
import com.lee.myapplication.R;

import java.util.HashMap;
import java.util.Map;


public class Register extends Activity {
    private EditText stuNumEdit,userEdit,classEdit,passEdit,repassEdit;
    private RadioGroup mRadioGroup1,mRadioGroup2;
    private RadioButton manBt,womanBt,stuBt,teacherBt;
    private String stuNum,pass,repass,userName,class_;
    private String sex = "男";
    private String role = "学生";
    private Button regBt;
    private String url = Url.Reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        init();

    }

    private void init() {
        stuNumEdit = (EditText) findViewById(R.id.stu_num);
        passEdit = (EditText) findViewById(R.id.pass);
        repassEdit = (EditText) findViewById(R.id.repass);
        userEdit = (EditText) findViewById(R.id.user_name);
        classEdit = (EditText) findViewById(R.id.class_);
        mRadioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
        manBt = (RadioButton) findViewById(R.id.manBt);
        womanBt = (RadioButton) findViewById(R.id.womanBt);
        mRadioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
        stuBt = (RadioButton) findViewById(R.id.stuBt);
        teacherBt = (RadioButton) findViewById(R.id.teacherBt);

        mRadioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==manBt.getId()){
                    sex = "男";
                }else {
                    sex = "女";
                }
            }
        });
        mRadioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==stuBt.getId()){
                    role = "学生";
                }else {
                    role = "教师";
                }
            }
        });

        regBt = (Button) findViewById(R.id.save);
        regBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stuNum = stuNumEdit.getText().toString().trim();
                userName = userEdit.getText().toString().trim();
                pass = passEdit.getText().toString().trim();
                repass = repassEdit.getText().toString().trim();
                class_ = classEdit.getText().toString().trim();
                if (stuNum.equals("")){
                    Toast.makeText(getApplicationContext(),"学号不能为空！",Toast.LENGTH_SHORT).show();
                }else if (userName.equals("")){
                    Toast.makeText(getApplicationContext(),"用户名不能为空！",Toast.LENGTH_SHORT).show();
                }else if (class_.equals("")){
                    Toast.makeText(getApplicationContext(),"班级不能为空！",Toast.LENGTH_SHORT).show();
                }else if (pass.equals("")){
                    Toast.makeText(getApplicationContext(),"密码不能为空！",Toast.LENGTH_SHORT).show();
                }else if (pass.equals(repass)){
                   Reg(stuNum,pass,userName,class_,sex,role);
                }else {
                   Toast.makeText(getApplicationContext(),"两次密码不一致，请检查！",Toast.LENGTH_SHORT).show();
               }
            }
        });


    }

    private void Reg(final String stuNum,final String pass, final String userName,final String class_,final String sex, final String role){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("upLoad ---", "response -> " + response);
                        //0注册成功，1用户名重复，其他
                        if(response.equals("0")){
                            Toast.makeText(getApplicationContext(),"注册成功！",Toast.LENGTH_SHORT).show();
                            Intent main = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(main);
                            finish();
                        }else if (response.equals("1")){
                            Toast.makeText(getApplicationContext(),"该学号已被注册！请登录",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),"注册失败，请重试！",Toast.LENGTH_SHORT).show();
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
                map.put("stuNum",stuNum);
                map.put("userName",userName);
                map.put("pass",pass);
                map.put("class_",class_);
                map.put("role",role);
                map.put("sex",sex);
                return map;
            }

        };
        requestQueue.add(stringRequest);


    }
}
