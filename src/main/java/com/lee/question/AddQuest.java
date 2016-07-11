package com.lee.question;

import android.app.Activity;
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
import com.lee.bean.User;
import com.lee.constant.Url;
import com.lee.db.GDdb;
import com.lee.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddQuest extends Activity{
	private String tag = "quest_Edit";
	private EditText quest_Edit,title_Edit;
	private Button back,save;
	private String url = Url.AddQuest;
	private String stuNum,sendTime,userName;
	private GDdb mGDdb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.addquest);

		// 获取当前时间 24h
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		sendTime = formatter.format(curDate);

		mGDdb = GDdb.getInstance(this);
		List<User> list = mGDdb.loadUser();
		User user = list.get(0);
		stuNum = user.getStuNum();
		userName = user.getUserName();
//		final Intent intent = getIntent();

		back = (Button)findViewById(R.id.back);
		save = (Button)findViewById(R.id.save);
		quest_Edit = (EditText)findViewById(R.id.quest_view);
		title_Edit = (EditText)findViewById(R.id.quest_title);


		
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String title = title_Edit.getText().toString();
				String quest = quest_Edit.getText().toString();
				upLoad(title, quest);
//				intent.putExtra("stuNum", stuNum);
//				intent.putExtra("userName", userName);
//				intent.putExtra("quest_title", title);
//				intent.putExtra("quest", quest);
//				intent.putExtra("sendTime", sendTime);
//				AddQuest.this.setResult(1, intent);
				AddQuest.this.finish();
			}
		});
	}
	protected void upLoad(final String title,final String quest) {
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
				map.put("userName", userName);
				map.put("stuNum", stuNum);
				map.put("sendTime", sendTime);
				map.put("quest_title", title);
				map.put("quest", quest);
                return map;
            }

        };        
        requestQueue.add(stringRequest);
	}	
}
