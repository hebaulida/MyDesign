package com.lee.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lee.bean.Quest;
import com.lee.constant.Url;
import com.lee.question.AddQuest;
import com.lee.question.QuestAdapter;
import com.lee.tool.RefreshableView;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class NumberOneFragment extends Fragment {
    private Button addDish;
    private List<Quest> mQuests;
    private QuestAdapter questAdapter;
//    private final int RESULT_ADD = 1;
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
                        questAdapter = new QuestAdapter(getActivity(), mQuests);
                        questAdapter.notifyDataSetChanged();
                        listView.setAdapter(questAdapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    private Quest AddQuest;
    private String url = Url.getQList;

    RefreshableView refreshableView;
    ListView listView;

    public NumberOneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_number_one, container, false);


        addDish = (Button) view.findViewById(R.id.add);
        addDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddQuest.class);
                startActivity(intent);
            }
        });

        refreshableView = (RefreshableView) view.findViewById(R.id.refreshable_view);
        listView = (ListView) view.findViewById(R.id.list_view);
        getList();
//        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
//        listView.setAdapter(adapter);
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep(3000);
                    Log.d("12346", "shuaxin");
                    getList();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshableView.finishRefreshing();
            }
        }, 0);


    return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("1","在这添加刷新");
        getList();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("2", "在这添加刷新");
    }

    public void getList(){
        Log.d("getlist", "upLoad");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        final String timenow = formatter.format(curDate);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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
                map.put("time", timenow);
                return map;
            }

        };
        requestQueue.add(stringRequest);
    }
}
