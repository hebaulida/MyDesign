package com.lee.question;

import android.content.Context;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lee.bean.Answer;
import com.lee.myapplication.R;

import java.util.List;

/**
 * Created by li on 2016/5/13.
 */
public class AnswerAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater inflater;
	private List<Answer> answers;
	private String tag = "answer adapter";

	public AnswerAdapter(Context mContext, List<Answer> answers) {
		this.mContext = mContext;
		this.answers = answers;
		inflater = LayoutInflater.from(mContext);
	}
	@Override
	public int getCount() {
		return answers.size();
	}

	@Override
	public Object getItem(int position) {
		return answers.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewholder = null;
		final Answer answer = answers.get(position);
		if (convertView == null) {
			viewholder = new ViewHolder();
			convertView = inflater.inflate(R.layout.comment_item, null);
			viewholder.userName=(TextView)convertView.findViewById(R.id.username);
			TextPaint tp = viewholder.userName.getPaint();
			tp.setFakeBoldText(true);
			viewholder.answerView = (TextView)convertView.findViewById(R.id.title);
			viewholder.sendTime = (TextView)convertView.findViewById(R.id.sendtime);
			convertView.setTag(viewholder);
		}else {
			viewholder = (ViewHolder) convertView.getTag();
		}
		viewholder.userName.setText(answer.getUserName());
		viewholder.answerView.setText(answer.getAnswer_());
		viewholder.sendTime.setText(answer.getAnswerTime());
		return convertView;
	}
	class ViewHolder {
		TextView userName;
		TextView sendTime;
		TextView answerView;
	}
}
