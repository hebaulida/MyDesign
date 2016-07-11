package com.lee.myinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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

public class MyPass extends Activity{
	private Button back,save;
	private EditText oldEdit,newEdit,reEdit;
	private String url = Url.Update;
	private String stuNum;
	private Intent intent;
	private final int SUCCESS = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mypass);

		intent = getIntent();
		stuNum = intent.getStringExtra("stuNum");



		back = (Button)findViewById(R.id.back);
		save = (Button)findViewById(R.id.save);
		oldEdit = (EditText) findViewById(R.id.oldPass);
		newEdit = (EditText) findViewById(R.id.newPass);
		reEdit = (EditText) findViewById(R.id.rePass);

		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyPass.this.setResult(0,intent);
				MyPass.this.finish();
			}
		});

		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String oldpass = oldEdit.getText().toString().trim();
				String newpass = newEdit.getText().toString().trim();
				String repass = reEdit.getText().toString().trim();
				if (oldpass.equals("")) {
					Toast.makeText(MyPass.this, "原密码不能为空！", Toast.LENGTH_SHORT).show();
				}else if(newEdit.equals("")) {
					Toast.makeText(MyPass.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
				}else if (repass.equals("")){
					Toast.makeText(MyPass.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
				}else if(newpass.equals(repass)) {
					upLoad(stuNum,oldpass,newpass);
				}else {
					Toast.makeText(MyPass.this, "两次密码不一致，请检查！", Toast.LENGTH_SHORT).show();
				}
			}
		});


	}

	protected void upLoad(final String stuNum, final String pass, final String newpass) {
		Log.d("myusername", "upLoad");
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("upLoad ---", "response -> " + response);
					if (response.equals("1")){
						Log.d("用户名可用", "response -> " + response);
						Toast.makeText(getApplicationContext(),"密码修改成功！",Toast.LENGTH_SHORT).show();
						finish();
					}else {
						Toast.makeText(getApplicationContext(),"原密码错误！",Toast.LENGTH_SHORT).show();
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
                Map<String, String> map = new HashMap<String, String>();
                map.put("stuNum", stuNum);
                map.put("pass", pass);
                map.put("newpass", newpass);
				map.put("flag", "upPass");
                return map;
            }

        };        
        requestQueue.add(stringRequest);
	}

}
