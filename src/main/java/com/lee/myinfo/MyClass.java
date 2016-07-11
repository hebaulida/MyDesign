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

public class MyClass extends Activity{
	private String tag = "Myclass_";
	private EditText class_Edit;
	private Button back,save;
	private String url = Url.Update;
	private String stuNum;
	private GDdb mGDdb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.myclass);
		
		mGDdb = GDdb.getInstance(this);
		final Intent intent = getIntent(); 
		String class_ = intent.getStringExtra("class_");
		stuNum = intent.getStringExtra("stuNum");
		
		back = (Button)findViewById(R.id.back);
		save = (Button)findViewById(R.id.save);
		class_Edit = (EditText)findViewById(R.id.class_);

		class_Edit.setText(class_);

		
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyClass.this.setResult(0, intent);
	            MyClass.this.finish();
			}
		});
		
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String res = class_Edit.getText().toString();
				upLoad(res);
				intent.putExtra("class_", res);
	            MyClass.this.setResult(1, intent);
	            MyClass.this.finish();
			}
		});
	}
	protected void upLoad(final String class_) {
		Log.d(tag, "upLoad");
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("upLoad ---", "response -> " + response);
    				mGDdb.updateClass(class_, stuNum);
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
                map.put("class_", class_);
                map.put("stuNum", stuNum);
                map.put("flag", "upClass");
                return map;
            }

        };        
        requestQueue.add(stringRequest);
	}	
}
