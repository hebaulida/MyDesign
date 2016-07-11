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
import com.lee.db.GDdb;
import com.lee.myapplication.R;

import java.util.HashMap;
import java.util.Map;

public class MyUserName extends Activity{
	private Button back,save;
	private EditText nameView;
	private String url = Url.Update;
	private String stuNum;
	private GDdb mGDdb;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.myusername);



		mGDdb = GDdb.getInstance(this);
		intent = getIntent();
		String userName = intent.getStringExtra("userName");
		stuNum = intent.getStringExtra("stuNum");

		back = (Button)findViewById(R.id.back);
		save = (Button)findViewById(R.id.save);
		nameView = (EditText)findViewById(R.id.nickname);
		nameView.setText(userName);

		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyUserName.this.setResult(0,intent);
				MyUserName.this.finish();
			}
		});

		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String res = nameView.getText().toString().trim();
				if (res.equals("")) {
					Toast.makeText(MyUserName.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
				}else {
					upLoad(res);
					intent.putExtra("userName", nameView.getText().toString().trim());
					MyUserName.this.setResult(1, intent);
					MyUserName.this.finish();
				}
			}
		});


	}

	protected void upLoad(final String userName) {
		Log.d("myusername", "upLoad");
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("upLoad ---", "response -> " + response);
					mGDdb.updateName(userName, stuNum);
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
                map.put("userName", userName);
                map.put("stuNum", stuNum);
                map.put("flag", "upUserName");
                return map;
            }

        };        
        requestQueue.add(stringRequest);
	}

}
