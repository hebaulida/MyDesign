package com.lee.myinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lee.bean.User;
import com.lee.constant.Url;
import com.lee.db.GDdb;
import com.lee.myapplication.R;

import java.util.List;

public class MyInfo extends Activity implements View.OnClickListener{
	private RelativeLayout nameLayout,roleLayout,sexLayout,classLayout,passLayout;
	private TextView stuNumView,nameView,sexView,roleView,class_View;
	private GDdb mGDdb;
	private String stuNum,userName,sex,role,class_;
	private final int RESULT_NAME = 1;
	private final int RESULT_SEX = 2;
	private final int RESULT_ROLE = 3;
	private final int RESULT_CLASS = 4;
	private final int RESULT_PASS = 5;


	private String HOST = Url.Update;
	private String tag = "MyInfo";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myinfo);
		mGDdb = GDdb.getInstance(this);
//		final RequestQueue mQueue = Volley.newRequestQueue(this);
		List<User> list = mGDdb.loadUser();
		User user = list.get(0);
		stuNum = user.getStuNum();
		role = user.getRole();
		userName = user.getUserName();
		sex = user.getSex();
		class_ = user.getClass_();

		stuNumView = (TextView) findViewById(R.id.stuNum);
		nameLayout = (RelativeLayout)findViewById(R.id.name_view);
		roleLayout = (RelativeLayout) findViewById(R.id.role_view);
		sexLayout  = (RelativeLayout)findViewById(R.id.sex_view);
		classLayout  = (RelativeLayout)findViewById(R.id.class_view);
		passLayout  = (RelativeLayout)findViewById(R.id.pass_view);

		nameView = (TextView)findViewById(R.id.username);
		sexView  = (TextView)findViewById(R.id.sex);
		roleView = (TextView) findViewById(R.id.role);
		class_View = (TextView) findViewById(R.id.class_);

		stuNumView.setText(stuNum);
		nameView.setText(userName);
		roleView.setText(role);
		sexView.setText(sex);
		class_View.setText(class_);

		nameLayout.setOnClickListener(this);
		roleLayout.setOnClickListener(this);
		sexLayout.setOnClickListener(this);
		classLayout.setOnClickListener(this);
		passLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.name_view:
				userName = nameView.getText().toString().trim();
				Intent intent1 = new Intent(MyInfo.this, MyUserName.class);
				intent1.putExtra("userName", userName);
				intent1.putExtra("stuNum", stuNum);
				MyInfo.this.startActivityForResult(intent1, RESULT_NAME);
				break;
			case R.id.sex_view:
				sex = sexView.getText().toString().trim();
				Intent intent2 = new Intent(MyInfo.this,MySex.class);
				intent2.putExtra("sex", sex);
				intent2.putExtra("stuNum", stuNum);
				MyInfo.this.startActivityForResult(intent2, RESULT_SEX);
				break;
			case R.id.role_view:
				role = roleView.getText().toString().trim();
				Intent intent3 = new Intent(MyInfo.this,MyRole.class);
				intent3.putExtra("role", role);
				intent3.putExtra("stuNum", stuNum);
				MyInfo.this.startActivityForResult(intent3, RESULT_ROLE);
				break;
			case R.id.class_view:
				class_ = class_View.getText().toString().trim();
				Intent intent4 = new Intent(MyInfo.this,MyClass.class);
				intent4.putExtra("class_", class_);
				intent4.putExtra("stuNum", stuNum);
				MyInfo.this.startActivityForResult(intent4, RESULT_CLASS);
				break;
			case R.id.pass_view:
				Intent intent5 = new Intent(MyInfo.this, MyPass.class);
				intent5.putExtra("stuNum", stuNum);
				MyInfo.this.startActivity(intent5);
//				MyInfo.this.startActivityForResult(intent5, RESULT_PASS);
				break;

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode){
			case RESULT_SEX:
				switch (resultCode){
					case 1:
						String sexString = data.getStringExtra("sex");
						sexView.setText(sexString);
						break;
				}
				break;
			case RESULT_NAME:
				switch (resultCode){
					case 1:
						String userName = data.getStringExtra("userName");
						nameView.setText(userName);
						break;
				}
				break;
			case RESULT_ROLE:
				switch (resultCode){
					case 1:
						String roleString = data.getStringExtra("role");
						roleView.setText(roleString);
						break;
				}
				break;
			case RESULT_CLASS:
				switch (resultCode){
					case 1:
						String class_ = data.getStringExtra("class_");
						class_View.setText(class_);
						break;
				}
				break;
//			case RESULT_PASS:
//				switch (resultCode){
//					case 1:
//						Toast.makeText(getApplicationContext(),"密码修改成功",Toast.LENGTH_SHORT).show();
//						break;
//				}
//				break;

		}
	}




}

