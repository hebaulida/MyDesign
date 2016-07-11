package com.lee.question;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lee.bean.Quest;
import com.lee.constant.Url;
import com.lee.myapplication.MainActivity;
import com.lee.myapplication.R;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComActivity extends Activity{
	private ListView listView;
	private String url = Url.com;
	private List<Quest> mQuests;
	private QuestAdapter questAdapter;
	private final int GET_SUCCESS = 1;
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what){
				case GET_SUCCESS:
					mQuests = new ArrayList<Quest>();
					String response = (String) msg.getData().get("response");
					try {
						JSONArray jsonArray = new JSONArray(response);

						for (int i = 0; i < jsonArray.length(); i++) {
							String questString = jsonArray.getString(i);
							String info[] = questString.split("\\+");
							Log.d("++", questString);

							Quest quest = new Quest();
							quest.setId( Integer.parseInt(info[0]));
							quest.setStuNum(info[1]);
							quest.setUserName(info[2]);
							quest.setQuest_title(info[3]);
							quest.setQuest(info[4]);
							quest.setSendTime(info[5]);
							mQuests.add(quest);
						}
						questAdapter = new QuestAdapter(getApplicationContext(), mQuests);
						questAdapter.notifyDataSetChanged();
						listView.setAdapter(questAdapter);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_com);

		listView = (ListView)findViewById(R.id.list_view);
		TextView titleView = (TextView) findViewById(R.id.title_text);
		Intent intent = getIntent();
		String type = intent.getStringExtra("type");
		if (type.equals("1")){
			titleView.setText("我的提问");
			getList(1+"");
		}else if(type.equals("2")){
			titleView.setText("我的回答");
			getList(2+"");
		}


		Button back = (Button) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ComActivity.this.finish();
			}
		});
		

	}

	public void getList(final String type){
		Log.d("getlist", "upLoad");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		final String timenow = formatter.format(curDate);

		RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
		StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.d("upLoad ---", "response -> success");
						Log.d("upLoad ---", "response -> " + response);
						Message message = new Message();
						message.what = GET_SUCCESS;
						Bundle bundle = new Bundle();
						bundle.putString("response", response);
						message.setData(bundle);
						mHandler.sendMessage(message);
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
				map.put("stuNum", MainActivity.stuNum);
				map.put("type", type);
				return map;
			}

		};
		requestQueue.add(stringRequest);
	}
}
