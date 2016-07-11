package com.lee.question;

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
import com.lee.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddAnswer extends Activity{
	private String tag = "answer";
	private EditText class_Edit;
	private Button back,save;
	private String url = Url.AddAnswer;
	private String stuNum,sendTime,userName;
	private String questid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.answer_layout);

		// 获取当前时间 24h
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		sendTime = formatter.format(curDate);


		final Intent intent = getIntent();
		questid = intent.getStringExtra("questid");
		userName = intent.getStringExtra("userName");
		stuNum = intent.getStringExtra("stuNum");
		
		back = (Button)findViewById(R.id.back);
		save = (Button)findViewById(R.id.save);
		class_Edit = (EditText)findViewById(R.id.class_);


		
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AddAnswer.this.setResult(0, intent);
	            AddAnswer.this.finish();
			}
		});
		
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String answer = class_Edit.getText().toString();
				Log.d("数据", answer + "+" + sendTime + "+" + stuNum + "+" + questid);
				upLoad(answer);
				intent.putExtra("answer", answer);
				intent.putExtra("answerTime", sendTime);
	            AddAnswer.this.setResult(1, intent);
	            AddAnswer.this.finish();
			}
		});
	}
	protected void upLoad(final String answer) {
		Log.d(tag, "upLoad");
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("upLoad ---", "response -> " + response);
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
                map.put("answer", answer);
                map.put("stuNum", stuNum);
                map.put("userName", userName);
                map.put("sendTime", sendTime);
                map.put("questid", questid);
                return map;
            }

        };
        requestQueue.add(stringRequest);
	}
}
