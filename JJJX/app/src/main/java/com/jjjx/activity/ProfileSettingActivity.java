package com.jjjx.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.jjjx.R;
import com.jjjx.widget.ListItemTextView;
import com.jjjx.widget.WheelView;

import java.util.Arrays;
import java.util.List;


/**
 * Created by AMing on 17/7/9.
 * Company RongCloud
 */
public class ProfileSettingActivity extends BaseActivity implements View.OnClickListener {
    ListItemTextView nameTextView;
    ListItemTextView sexTextView;
    ListItemTextView learnYearTextView;
    ListItemTextView jobTextView;
    ListItemTextView mainClassTextView;
    String[] sex = new String[]{"男", "女"};
    String[] learn = new String[]{"1年", "2年", "3年", "4年", "5年", "6年", "7年", "8年", "9年", "10年以上"};
    String[] mainClass = new String[]{"舞蹈", "声乐", "美术", "乐器", "其他"};
    String string1 = null;
    String string2 = null;
    String string3 = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);
        setTitle("信息设置");
        nameTextView = (ListItemTextView) findViewById(R.id.profile_name);
        sexTextView = (ListItemTextView) findViewById(R.id.profile_sex);
        learnYearTextView = (ListItemTextView) findViewById(R.id.profile_learn_year);
        jobTextView = (ListItemTextView) findViewById(R.id.profile_job);
        mainClassTextView = (ListItemTextView) findViewById(R.id.profile_main_class);
        nameTextView.setOnClickListener(this);
        sexTextView.setOnClickListener(this);
        learnYearTextView.setOnClickListener(this);
        jobTextView.setOnClickListener(this);
        mainClassTextView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_name:
                break;
            case R.id.profile_sex:

                List<String> sexList = Arrays.asList(sex);
                View sexView = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
                WheelView sexWheelView = (WheelView) sexView.findViewById(R.id.wheel_view_wv);
                sexWheelView.setOffset(2);//每页展示几个
                sexWheelView.setItems(sexList);
                sexWheelView.setSeletion(0);//默认展示第几个元素
                sexWheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        string1 = item;
                    }
                });

                new AlertDialog.Builder(this)
                        .setTitle("性别")
                        .setView(sexView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sexTextView.setDetail(TextUtils.isEmpty(string1) ? sex[0] : string1);
                            }
                        })
                        .show();
                break;
            case R.id.profile_learn_year:
                List<String> learnList = Arrays.asList(learn);
                View learnView = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
                WheelView learnWheelView = (WheelView) learnView.findViewById(R.id.wheel_view_wv);
                learnWheelView.setOffset(2);
                learnWheelView.setItems(learnList);
                learnWheelView.setSeletion(0);
                learnWheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        string2 = item;
                    }
                });

                new AlertDialog.Builder(this)
                        .setTitle("教龄")
                        .setView(learnView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                learnYearTextView.setDetail(TextUtils.isEmpty(string2) ? learn[0] : string2);
                            }
                        })
                        .show();
                break;
            case R.id.profile_job:
                break;
            case R.id.profile_main_class:
                List<String> mainClassList = Arrays.asList(mainClass);
                final View mainClassView = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
                WheelView mainWheelView = (WheelView) mainClassView.findViewById(R.id.wheel_view_wv);
                mainWheelView.setOffset(2);
                mainWheelView.setItems(mainClassList);
                mainWheelView.setSeletion(0);
                mainWheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        string3 = item;
                    }
                });

                new AlertDialog.Builder(this)
                        .setTitle("主要课程")
                        .setView(mainClassView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mainClassTextView.setDetail(TextUtils.isEmpty(string3) ? mainClass[0] : string3);
                            }
                        })
                        .show();
                break;
        }
    }


}
