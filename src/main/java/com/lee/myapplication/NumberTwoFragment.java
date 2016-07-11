package com.lee.myapplication;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lee.bean.User;
import com.lee.db.GDdb;
import com.lee.login.LoginActivity;
import com.lee.myinfo.MyInfo;
import com.lee.question.ComActivity;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NumberTwoFragment extends Fragment {
    private Button logout;
    private RelativeLayout userInfo;
    private TextView userNameView,roleView,myQuesView,myAnsView;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private GDdb mGDdb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_number_two, container, false);
        mGDdb = GDdb.getInstance(getActivity());
        List<User> list = mGDdb.loadUser();
        User user = list.get(0);
        String userName = user.getUserName();
        String role = user.getRole();
        userInfo = (RelativeLayout) view.findViewById(R.id.userinfo);
        userNameView = (TextView) view.findViewById(R.id.user_name);
        roleView = (TextView) view.findViewById(R.id.role);
        myQuesView = (TextView)view.findViewById(R.id.ques_bt);
        myAnsView = (TextView) view.findViewById(R.id.ans_bt);
        logout = (Button) view.findViewById(R.id.logout);

        userNameView.setText(userName);
        roleView.setText(role);

        userInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyInfo.class);
                startActivity(intent);

            }
        });
        myQuesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), ComActivity.class);
                intent1.putExtra("type","1");
                startActivity(intent1);
            }
        });
        myAnsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getActivity(), ComActivity.class);
                intent2.putExtra("type","2");
                startActivity(intent2);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                editor = pref.edit();
                editor.putBoolean("auto_login", false);
                editor.commit();
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }

}
