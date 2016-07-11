package com.lee.question;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lee.bean.Answer;
import com.lee.bean.Quest;
import com.lee.bean.User;
import com.lee.constant.Url;
import com.lee.db.GDdb;
import com.lee.myapplication.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestInfo extends Activity{
	private String tag = "quest_Edit";
	private TextView questView,titleView,authorView;
	private ListView commentView;
	private Button back,addAns;
	private String url = Url.getAList;
	private String stuNum,sendTime,userName;
	private GDdb mGDdb;
	private Quest mQuest;
	private String questId;
	private final int ANSWER = 1;
	private final int GET_SUCCESS = 2;
	private List<Answer> mAnswers;
	private AnswerAdapter answerAdapter;
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what){
				case GET_SUCCESS:
					mAnswers = new ArrayList<Answer>();
					String response = (String) msg.getData().get("response");
					try {
						JSONArray jsonArray = new JSONArray(response);

						for (int i = 0; i < jsonArray.length(); i++) {
							String questString = jsonArray.getString(i);
							String info[] = questString.split("\\+");
							Log.d("++", questString);

							Answer mAnswer = new Answer();
							mAnswer.setStuNum(info[1]);
							mAnswer.setUserName(info[2]);
							mAnswer.setQuestid(info[3]);
							mAnswer.setAnswer_(info[4]);
							mAnswer.setAnswerTime(info[5]);
							mAnswers.add(mAnswer);
						}
						answerAdapter = new AnswerAdapter(getApplicationContext(), mAnswers);
						answerAdapter.notifyDataSetChanged();
						commentView.setAdapter(answerAdapter);
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
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.questinfo);

		mQuest = (Quest)getIntent().getSerializableExtra("mQuest");
		int questId1 = mQuest.getId();
		questId = Integer.toString(questId1);

		mGDdb = GDdb.getInstance(this);
		List<User> list = mGDdb.loadUser();
		User user = list.get(0);
		stuNum = user.getStuNum();
		userName = user.getUserName();

		back = (Button)findViewById(R.id.back);
		addAns = (Button) findViewById(R.id.addBt);
		questView = (TextView)findViewById(R.id.quest_view);
		titleView = (TextView)findViewById(R.id.quest_title);
		authorView = (TextView) findViewById(R.id.author);
		commentView = (ListView) findViewById(R.id.comment);

		questView.setText(mQuest.getQuest());
		titleView.setText(mQuest.getQuest_title());
		authorView.setText(mQuest.getUserName());

		getComment(questId);

		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		addAns.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(QuestInfo.this, AddAnswer.class);
				intent.putExtra("questid",questId);
				intent.putExtra("stuNum",stuNum);
				intent.putExtra("userName",userName);
				startActivityForResult(intent,ANSWER);
			}
		});
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode){
			case ANSWER:
				switch (resultCode){
					case 1:
						String answer = data.getStringExtra("answer");
						String answerTime = data.getStringExtra("answerTime");
						Answer answer1 = new Answer();
						answer1.setStuNum(stuNum);
						answer1.setUserName(userName);
						answer1.setQuestid(questId);
						answer1.setAnswer_(answer);
						answer1.setAnswerTime(answerTime);

						mAnswers.add(answer1);
						answerAdapter = new AnswerAdapter(getApplicationContext(), mAnswers);
						answerAdapter.notifyDataSetChanged();
						commentView.setAdapter(answerAdapter);
						break;
				}
				break;
		}
	}

	protected void getComment(final String questid) {
		Log.d(tag, "upLoad");
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
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
				map.put("questid", questid);
                return map;
            }

        };        
        requestQueue.add(stringRequest);
	}	
}
