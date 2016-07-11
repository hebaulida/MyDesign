package com.lee.question;

import android.content.Context;
import android.content.Intent;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lee.bean.Quest;
import com.lee.myapplication.MainActivity;
import com.lee.myapplication.R;

import java.util.List;

/**
 * Created by li on 2016/5/13.
 */
public class QuestAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater inflater;
	private List<Quest> quests;
	private String tag = "quest adapter";

	public QuestAdapter (Context mContext,List<Quest> quests) {
		this.mContext = mContext;
		this.quests = quests;
		inflater = LayoutInflater.from(mContext);
	}
	@Override
	public int getCount() {
		return quests.size();
	}

	@Override
	public Object getItem(int position) {
		return quests.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewholder = null;
		final Quest mQuest = quests.get(position);
		if (convertView == null) {
			viewholder = new ViewHolder();
			convertView = inflater.inflate(R.layout.fragment1_item, null);
			viewholder.questView = (RelativeLayout) convertView.findViewById(R.id.quest_view);
			viewholder.userName=(TextView)convertView.findViewById(R.id.username);
			TextPaint tp = viewholder.userName.getPaint();
			tp.setFakeBoldText(true);
			viewholder.title = (TextView)convertView.findViewById(R.id.title);
			viewholder.sendTime = (TextView)convertView.findViewById(R.id.sendtime);
			convertView.setTag(viewholder);
		}else {
			viewholder = (ViewHolder) convertView.getTag();
		}
		viewholder.userName.setText(mQuest.getUserName());
		viewholder.title.setText(mQuest.getQuest_title());
		viewholder.sendTime.setText(mQuest.getSendTime());
		viewholder.questView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(tag, "item"+mQuest.getQuest_title());
				Intent intent = new Intent(MainActivity.mactivity,QuestInfo.class);
				intent.putExtra("mQuest", mQuest);
				MainActivity.mactivity.startActivity(intent);
			}
		});
		return convertView;
	}
	class ViewHolder {
		RelativeLayout questView;
		TextView userName;
		TextView sendTime;
		TextView title;
	}
}
