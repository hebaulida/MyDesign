package com.lee.myinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

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

public class MyRole extends Activity{
	private Button back;
	private LinearLayout manLayout,womanLayout;
	private String url = Url.Update;
	private String stuNum;
	private GDdb mGDdb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.myrole);
		
		back = (Button)findViewById(R.id.back);
		manLayout = (LinearLayout)findViewById(R.id.man);
		womanLayout = (LinearLayout)findViewById(R.id.woman);
		
		mGDdb = GDdb.getInstance(this);
		final Intent intent = getIntent();
		String role = intent.getStringExtra("role");
		stuNum = intent.getStringExtra("stuNum");
		
		ImageView manimg = (ImageView)findViewById(R.id.manimg);
		ImageView womanimg = (ImageView)findViewById(R.id.womanimg);
		if (role.equals("学生")) {
			manimg.setVisibility(View.VISIBLE);
		} else if(role.equals("教师")){
			womanimg.setVisibility(View.VISIBLE);
		}
		
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyRole.this.setResult(0, intent);
				MyRole.this.finish();
			}
		});
		
		manLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				upLoad("学生");
				intent.putExtra("role", "学生");
				MyRole.this.setResult(1, intent);
				MyRole.this.finish();
			}
		});
		womanLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				upLoad("教师");
				intent.putExtra("role", "教师");
				MyRole.this.setResult(1, intent);
				MyRole.this.finish();
			}
		});
	}
	protected void upLoad(final String role) {
		Log.d("mysex", "upLoad");
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("upLoad ---", "response -> " + response);
    				mGDdb.updateRole(role, stuNum);
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
                map.put("role", role);
                map.put("stuNum", stuNum);
                map.put("flag", "upRole");
                return map;
            }

        };        
        requestQueue.add(stringRequest);
	}
}
