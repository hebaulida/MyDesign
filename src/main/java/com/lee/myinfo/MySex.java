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

public class MySex extends Activity{
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
		setContentView(R.layout.mysex);
		
		back = (Button)findViewById(R.id.back);
		manLayout = (LinearLayout)findViewById(R.id.man);
		womanLayout = (LinearLayout)findViewById(R.id.woman);
		
		mGDdb = GDdb.getInstance(this);
		final Intent intent = getIntent();
		String sex = intent.getStringExtra("sex");
		stuNum = intent.getStringExtra("stuNum");
		
		ImageView manimg = (ImageView)findViewById(R.id.manimg);
		ImageView womanimg = (ImageView)findViewById(R.id.womanimg);
		if (sex.equals("男")) {
			manimg.setVisibility(View.VISIBLE);
		} else if(sex.equals("女")){
			womanimg.setVisibility(View.VISIBLE);
		}
		
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MySex.this.setResult(0, intent);
				MySex.this.finish();
			}
		});
		
		manLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				upLoad("男");
				intent.putExtra("sex", "男");
				MySex.this.setResult(1, intent);
				MySex.this.finish();
			}
		});
		womanLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				upLoad("女");
				intent.putExtra("sex", "女");
				MySex.this.setResult(1, intent);
				MySex.this.finish();
			}
		});
	}
	protected void upLoad(final String sex) {
		Log.d("mysex", "upLoad");
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("upLoad ---", "response -> " + response);
    				mGDdb.updateSex(sex,stuNum);
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
                map.put("sex", sex);
                map.put("stuNum", stuNum);
                map.put("flag", "upSex");
                return map;
            }

        };        
        requestQueue.add(stringRequest);
	}
}
