package com.lee.myapplication;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.lee.bean.User;
import com.lee.db.GDdb;

import java.util.ArrayList;
import java.util.List;





public class MainActivity extends FragmentActivity implements View.OnClickListener,ViewPager.OnPageChangeListener{
	private Button mButton1,mButton2;
	private List<Fragment> fragments;
	FragmentManager fragmentManager;
	public static MainActivity mactivity;
	private ViewPager myViewPager; // 要使用的ViewPager
	private ImageView line_tab; // tab选项卡的下划线
	private int moveOne = 0; // 下划线移动一个选项卡
	private boolean isScrolling = false; // 手指是否在滑动
	private boolean isBackScrolling  = false; // 手指离开后的回弹
	private long startTime = 0;
	private long currentTime = 0;
	private GDdb mGDdb;
	public static String stuNum;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mactivity = this;

		mGDdb = GDdb.getInstance(this);
//		final RequestQueue mQueue = Volley.newRequestQueue(this);
		List<User> list = mGDdb.loadUser();
		User user = list.get(0);
		stuNum = user.getStuNum();

		//获得Fragment管理所需要的类的对象
		fragmentManager = getSupportFragmentManager();
		initView();
		initLineImage();
	}
	/**
	 * 重新设定line的宽度
	 */
	private void initLineImage() {
		// 获取屏幕的宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;

		// 重新设置下划线的宽度
		ViewGroup.LayoutParams lp = line_tab.getLayoutParams();
		lp.width = screenW / 2;
		line_tab.setLayoutParams(lp);

		moveOne = lp.width; // 滑动一个页面的距离
	}
	/**
	 * 初始化组件
	 */
	private void initView() {
		myViewPager = (ViewPager) findViewById(R.id.myViewPager);

		mButton1 = (Button) findViewById(R.id.button1_view);
		mButton2 = (Button) findViewById(R.id.button2_view);

		mButton1.setOnClickListener(this);
		mButton2.setOnClickListener(this);

		Fragment fg1 = new NumberOneFragment();
		Fragment fg2 = new NumberTwoFragment();
		fragments = new ArrayList<Fragment>();
		fragments.add(fg1);
		fragments.add(fg2);

		FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
		myViewPager.setAdapter(fragmentAdapter);

		myViewPager.setCurrentItem(0);
		mButton1.setSelected(true);

		myViewPager.setOnPageChangeListener(this);

		line_tab = (ImageView) findViewById(R.id.line_tab);
	}
	//tab 按钮监听事件
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.button1_view:
				myViewPager.setCurrentItem(0);
				break;
			case R.id.button2_view:
				myViewPager.setCurrentItem(1);
				break;
			default:
				break;
		}
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		currentTime = System.currentTimeMillis();
		if (isScrolling && (currentTime - startTime > 200)) {
			movePositionX(position, moveOne * positionOffset);
			startTime = currentTime;
		}

		if (isBackScrolling) {
			movePositionX(position);
		}
	}
	//滑动结束，跳到新页面
	@Override
	public void onPageSelected(int position) {
		switch (position) {
			case 0:
				mButton1.setSelected(true);
				mButton2.setSelected(false);
				movePositionX(0);
				break;
			case 1:
				mButton1.setSelected(false);
				mButton2.setSelected(true);
				movePositionX(1);
				break;

		}

	}
	/**
	 * 当页面的滑动状态改变时该方法会被触发，
	 * 页面的滑动状态有3个：
	 * “0”表示什么都不做，“1”表示开始滑动，“2”表示结束滑动
	 * */
	@Override
	public void onPageScrollStateChanged(int state) {
		switch (state) {
			case 1:
				isScrolling = true;
				isBackScrolling = false;
				break;
			case 2:
				isScrolling = false;
				isBackScrolling = true;
				break;
			default:
				isScrolling = false;
				isBackScrolling = false;
				break;
		}

	}
	/**
	 * 下划线跟随手指的滑动而移动
	 * @param toPosition
	 * @param positionOffsetPixels
	 */
	private void movePositionX(int toPosition, float positionOffsetPixels) {
		// TODO Auto-generated method stub
		float curTranslationX = line_tab.getTranslationX();
		float toPositionX = moveOne * toPosition + positionOffsetPixels;
		ObjectAnimator animator = ObjectAnimator.ofFloat(line_tab, "translationX", curTranslationX, toPositionX);
		animator.setDuration(500);
		animator.start();
	}

	/**
	 * 下划线滑动到新的选项卡中
	 * @param toPosition
	 */
	private void movePositionX(int toPosition) {
		// TODO Auto-generated method stub
		movePositionX(toPosition, 0);
	}
}


