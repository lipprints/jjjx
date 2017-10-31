package com.jjjx.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jjjx.R;
import com.jjjx.data.response.InformationResponse;
import com.jjjx.utils.CacheTask;
import com.jjjx.utils.DialogWithYesOrNoUtils;
import com.jjjx.utils.NToast;
import com.jjjx.widget.CircleImageView;
import com.jjjx.widget.ListItemTextView;
import com.jjjx.widget.WheelView;

import java.util.Arrays;
import java.util.List;


/**
 * Created by AMing on 17/7/9.
 * Company RongCloud
 */
public class ProfileSettingActivity extends BaseActivity implements View.OnClickListener {

    String[] sex = new String[]{"男", "女"};
    String[] learn = new String[]{"1年", "2年", "3年", "4年", "5年", "6年", "7年", "8年", "9年", "10年以上"};
    String[] mainClass = new String[]{"舞蹈", "声乐", "美术", "乐器", "其他"};
    String[] teacherAmount = new String[]{"0~9人", "10~50人", "50~99人", "100~199人", "200人以上"};


    LinearLayout teacherLinearLayout;
    LinearLayout organizationLinearLayout;
    LinearLayout personLinearLayout;

    ListItemTextView personNameTextView;
    ListItemTextView personSexTextView;

    ListItemTextView teacherNameTextView;
    ListItemTextView teacherSexTextView;
    ListItemTextView teacherLearnYearTextView;
    ListItemTextView teacherJobTextView;
    ListItemTextView teacherMainClassTextView;

    ListItemTextView organNameTextView;
    ListItemTextView organLearnYearTextView;
    ListItemTextView organTeacherAmountTextView;
    ListItemTextView organAverageAgeTextView;
    ListItemTextView organMainClassTextView;

    String personName;
    String personSex;

    String teacherName;
    String teacherSex;
    String teacherLearnYear;
    String teacherJob;
    String teacherMainClass;

    String organName;
    String organLearnYear;
    String organTeacherAmount;
    String organAverageAge;
    String organMainClass;
    CircleImageView imageView;


    public static final int SAVE_INFO_PERSON = 601;
    public static final int SAVE_INFO_TEACHER = 602;
    public static final int SAVE_INFO_ORGANIZATION = 603;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);
        setTitle("信息设置");
        btn_right.setVisibility(View.VISIBLE);
        btn_right.setText("保存修改");
        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (Integer.parseInt(CacheTask.getInstance().getUserRole())) {
                    case 1:
                    case 4:
                        request(SAVE_INFO_PERSON);
                        break;
                    case 2:
                        request(SAVE_INFO_TEACHER);
                        break;
                    case 3:
                        request(SAVE_INFO_ORGANIZATION);
                        break;
                }
            }
        });

        imageView = (CircleImageView) findViewById(R.id.fm_head);
        if (!TextUtils.isEmpty(CacheTask.getInstance().getPortrait())) {
            Glide.with(mContext).load(CacheTask.getInstance().getPortrait()).into(new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    imageView.setImageDrawable(resource);
                }
            });
        }
        switch (Integer.parseInt(CacheTask.getInstance().getUserRole())) {
            case 1://个人
            case 4:
                personLinearLayout = (LinearLayout) findViewById(R.id.profile_person);
                personLinearLayout.setVisibility(View.VISIBLE);
                personNameTextView = (ListItemTextView) findViewById(R.id.profile_name_p);
                personSexTextView = (ListItemTextView) findViewById(R.id.profile_sex_p);
                personNameTextView.setOnClickListener(this);
                personSexTextView.setOnClickListener(this);
                personNameTextView.setDetail(CacheTask.getInstance().getName());
                personSexTextView.setDetail(CacheTask.getInstance().getSex());
                personName = CacheTask.getInstance().getName();
                personSex = CacheTask.getInstance().getSex();

                break;
            case 2://教师
                teacherLinearLayout = (LinearLayout) findViewById(R.id.profile_teacher);
                teacherLinearLayout.setVisibility(View.VISIBLE);
                teacherNameTextView = (ListItemTextView) findViewById(R.id.profile_name_t);
                teacherSexTextView = (ListItemTextView) findViewById(R.id.profile_sex_t);
                teacherLearnYearTextView = (ListItemTextView) findViewById(R.id.profile_learn_year_t);
                teacherJobTextView = (ListItemTextView) findViewById(R.id.profile_job_t);
                teacherMainClassTextView = (ListItemTextView) findViewById(R.id.profile_main_class_t);
                teacherNameTextView.setOnClickListener(this);
                teacherSexTextView.setOnClickListener(this);
                teacherLearnYearTextView.setOnClickListener(this);
                teacherJobTextView.setOnClickListener(this);
                teacherMainClassTextView.setOnClickListener(this);
                teacherNameTextView.setDetail(CacheTask.getInstance().getName());
                teacherSexTextView.setDetail(CacheTask.getInstance().getSex());
                teacherLearnYearTextView.setDetail(CacheTask.getInstance().getLearnYear());
                teacherMainClassTextView.setDetail(CacheTask.getInstance().getMainClass());
                teacherLearnYear = CacheTask.getInstance().getLearnYear();
                teacherName = CacheTask.getInstance().getName();
                teacherSex = CacheTask.getInstance().getSex();
                teacherMainClass = CacheTask.getInstance().getMainClass();

                break;
            case 3://机构
                organizationLinearLayout = (LinearLayout) findViewById(R.id.profile_organization);
                organizationLinearLayout.setVisibility(View.VISIBLE);
                organNameTextView = (ListItemTextView) findViewById(R.id.profile_name_o);
                organLearnYearTextView = (ListItemTextView) findViewById(R.id.profile_learn_year_o);
                organTeacherAmountTextView = (ListItemTextView) findViewById(R.id.profile_teacher_amount_o);
                organAverageAgeTextView = (ListItemTextView) findViewById(R.id.profile_average_age_o);
                organMainClassTextView = (ListItemTextView) findViewById(R.id.profile_main_class_o);
                organNameTextView.setOnClickListener(this);
                organLearnYearTextView.setOnClickListener(this);
                organTeacherAmountTextView.setOnClickListener(this);
                organAverageAgeTextView.setOnClickListener(this);
                organMainClassTextView.setOnClickListener(this);
                organNameTextView.setDetail(CacheTask.getInstance().getName());
                organLearnYearTextView.setDetail(CacheTask.getInstance().getLearnYear());
                organAverageAgeTextView.setDetail(CacheTask.getInstance().getAverageAge());
                organTeacherAmountTextView.setDetail(CacheTask.getInstance().getAeacherAmount());
                organMainClassTextView.setDetail(CacheTask.getInstance().getMainClass());
                organName = CacheTask.getInstance().getName();
                organLearnYear = CacheTask.getInstance().getLearnYear();
                organTeacherAmount = CacheTask.getInstance().getAeacherAmount();
                organAverageAge = CacheTask.getInstance().getAverageAge();
                organMainClass = CacheTask.getInstance().getMainClass();
                break;
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_name_p:  //个人昵称
                DialogWithYesOrNoUtils.getInstance().showEditDialog(mContext, "新的昵称", "确定", new DialogWithYesOrNoUtils.DialogCallBack() {
                    @Override
                    public void executeEvent() {

                    }

                    @Override
                    public void executeEditEvent(String editText) {
                        if (!TextUtils.isEmpty(editText)) {
                            personNameTextView.setDetail(editText);
                            personName = editText;

                        }

                    }
                });
                break;
            case R.id.profile_name_t: //老师昵称
                DialogWithYesOrNoUtils.getInstance().showEditDialog(mContext, "新的昵称", "确定", new DialogWithYesOrNoUtils.DialogCallBack() {
                    @Override
                    public void executeEvent() {

                    }

                    @Override
                    public void executeEditEvent(String editText) {
                        if (!TextUtils.isEmpty(editText)) {
                            teacherNameTextView.setDetail(editText);
                            teacherName = editText;

                        }

                    }
                });
                break;
            case R.id.profile_name_o: //机构名称
                DialogWithYesOrNoUtils.getInstance().showEditDialog(mContext, "机构名称", "确定", new DialogWithYesOrNoUtils.DialogCallBack() {
                    @Override
                    public void executeEvent() {

                    }

                    @Override
                    public void executeEditEvent(String editText) {
                        if (!TextUtils.isEmpty(editText)) {
                            organNameTextView.setDetail(editText);
                            organName = editText;

                        }

                    }
                });
                break;
            case R.id.profile_sex_p: //个人性别
                List<String> sexList = Arrays.asList(sex);
                View sexView = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
                WheelView sexWheelView = (WheelView) sexView.findViewById(R.id.wheel_view_wv);
                sexWheelView.setOffset(2);//每页展示几个
                sexWheelView.setItems(sexList);
                sexWheelView.setSeletion(0);//默认展示第几个元素
                sexWheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        personSex = item;
                    }
                });

                new AlertDialog.Builder(this)
                        .setTitle("性别")
                        .setView(sexView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                personSexTextView.setDetail(TextUtils.isEmpty(personSex) ? sex[0] : personSex);
                            }
                        })
                        .show();
                break;
            case R.id.profile_sex_t: //教师性别
                List<String> tSexList = Arrays.asList(sex);
                View tSexView = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
                WheelView tSexWheelView = (WheelView) tSexView.findViewById(R.id.wheel_view_wv);
                tSexWheelView.setOffset(2);//每页展示几个
                tSexWheelView.setItems(tSexList);
                tSexWheelView.setSeletion(0);//默认展示第几个元素
                tSexWheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        teacherSex = item;
                    }
                });

                new AlertDialog.Builder(this)
                        .setTitle("性别")
                        .setView(tSexView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                teacherSexTextView.setDetail(TextUtils.isEmpty(teacherSex) ? sex[0] : teacherSex);
                            }
                        })
                        .show();
                break;
            case R.id.profile_learn_year_t: //老师教龄
                List<String> learnList = Arrays.asList(learn);
                View learnView = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
                WheelView learnWheelView = (WheelView) learnView.findViewById(R.id.wheel_view_wv);
                learnWheelView.setOffset(2);
                learnWheelView.setItems(learnList);
                learnWheelView.setSeletion(0);
                learnWheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        teacherLearnYear = item;
                    }
                });

                new AlertDialog.Builder(this)
                        .setTitle("教龄")
                        .setView(learnView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                teacherLearnYearTextView.setDetail(TextUtils.isEmpty(teacherLearnYear) ? learn[0] : teacherLearnYear);
                            }
                        })
                        .show();
                break;
            case R.id.profile_learn_year_o: //机构办学时长
                List<String> oLearnList = Arrays.asList(learn);
                View oLearnView = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
                WheelView oLearnWheelView = (WheelView) oLearnView.findViewById(R.id.wheel_view_wv);
                oLearnWheelView.setOffset(2);
                oLearnWheelView.setItems(oLearnList);
                oLearnWheelView.setSeletion(0);
                oLearnWheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        organLearnYear = item;
                    }
                });

                new AlertDialog.Builder(this)
                        .setTitle("机构办学时长")
                        .setView(oLearnView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                organLearnYearTextView.setDetail(TextUtils.isEmpty(organLearnYear) ? learn[0] : organLearnYear);
                            }
                        })
                        .show();
                break;
            case R.id.profile_main_class_t: //老师主要课程
                List<String> mainClassList = Arrays.asList(mainClass);
                final View mainClassView = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
                WheelView mainWheelView = (WheelView) mainClassView.findViewById(R.id.wheel_view_wv);
                mainWheelView.setOffset(2);
                mainWheelView.setItems(mainClassList);
                mainWheelView.setSeletion(0);
                mainWheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        teacherMainClass = item;
                    }
                });

                new AlertDialog.Builder(this)
                        .setTitle("主要课程")
                        .setView(mainClassView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                teacherMainClassTextView.setDetail(TextUtils.isEmpty(teacherMainClass) ? mainClass[0] : teacherMainClass);
                            }
                        })
                        .show();
                break;
            case R.id.profile_main_class_o: //机构主要课程
                List<String> oMainClassList = Arrays.asList(mainClass);
                final View oMainClassView = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
                WheelView oMainWheelView = (WheelView) oMainClassView.findViewById(R.id.wheel_view_wv);
                oMainWheelView.setOffset(2);
                oMainWheelView.setItems(oMainClassList);
                oMainWheelView.setSeletion(0);
                oMainWheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        organMainClass = item;
                    }
                });

                new AlertDialog.Builder(this)
                        .setTitle("主要课程")
                        .setView(oMainClassView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                organMainClassTextView.setDetail(TextUtils.isEmpty(organMainClass) ? mainClass[0] : organMainClass);
                            }
                        })
                        .show();
                break;
            case R.id.profile_job_t: // 老师主要职业
                break;
            case R.id.profile_average_age_o: //机构老师平均教龄
                List<String> oAverageLearnList = Arrays.asList(learn);
                View oAverageLearnView = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
                WheelView oAverageLearnWheelView = (WheelView) oAverageLearnView.findViewById(R.id.wheel_view_wv);
                oAverageLearnWheelView.setOffset(2);
                oAverageLearnWheelView.setItems(oAverageLearnList);
                oAverageLearnWheelView.setSeletion(0);
                oAverageLearnWheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        organAverageAge = item;
                    }
                });

                new AlertDialog.Builder(this)
                        .setTitle("机构老师平均教龄")
                        .setView(oAverageLearnView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                organAverageAgeTextView.setDetail(TextUtils.isEmpty(organAverageAge) ? learn[0] : organAverageAge);
                            }
                        })
                        .show();
                break;
            case R.id.profile_teacher_amount_o: //机构教师人数
                List<String> teacherAmounts = Arrays.asList(teacherAmount);
                View teacherAmountView = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
                WheelView teacherAmountWheelView = (WheelView) teacherAmountView.findViewById(R.id.wheel_view_wv);
                teacherAmountWheelView.setOffset(2);
                teacherAmountWheelView.setItems(teacherAmounts);
                teacherAmountWheelView.setSeletion(0);
                teacherAmountWheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        organTeacherAmount = item;
                    }
                });

                new AlertDialog.Builder(this)
                        .setTitle("教师人数")
                        .setView(teacherAmountView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                organTeacherAmountTextView.setDetail(TextUtils.isEmpty(organTeacherAmount) ? learn[0] : organTeacherAmount);
                            }
                        })
                        .show();
                break;
        }
    }


    @Override
    public Object doInBackground(int requestCode) throws Exception {
        switch (requestCode) {
            case SAVE_INFO_PERSON:
                return action.setUserInfo(CacheTask.getInstance().getUserId(), personName, personSex);
            case SAVE_INFO_TEACHER:
                return action.setTeacherInfo(CacheTask.getInstance().getUserId(), teacherName, teacherSex, "教师", teacherLearnYear, teacherMainClass);
            case SAVE_INFO_ORGANIZATION:
                return action.setOrganizationInfo(CacheTask.getInstance().getUserId(), organName, organLearnYear, organMainClass, organTeacherAmount, organAverageAge);
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        switch (requestCode) {
            case SAVE_INFO_PERSON:
                InformationResponse personResponse = (InformationResponse) result;
                if ("10000".equals(personResponse.getHead().getCode())) {
                    CacheTask.getInstance().cacheName(personName);
                    CacheTask.getInstance().cacheSex(personSex);
                    if (mProfileChangeListener != null) {
                        mProfileChangeListener.change();
                    }
                    NToast.shortToast(mContext, "更新成功");
                    finish();
                }
                break;
            case SAVE_INFO_TEACHER:
                InformationResponse teacherResponse = (InformationResponse) result;
                if ("10000".equals(teacherResponse.getHead().getCode())) {
                    CacheTask.getInstance().cacheName(teacherName);
                    CacheTask.getInstance().cacheSex(teacherSex);
                    CacheTask.getInstance().cacheLearnYear(teacherLearnYear);
                    CacheTask.getInstance().cacheMainClass(teacherMainClass);
                    if (mProfileChangeListener != null) {
                        mProfileChangeListener.change();
                    }
                    NToast.shortToast(mContext, "更新成功");
                    finish();
                }
                break;
            case SAVE_INFO_ORGANIZATION:
                InformationResponse organResponse = (InformationResponse) result;
                if ("10000".equals(organResponse.getHead().getCode())) {
                    CacheTask.getInstance().cacheName(organName);
                    CacheTask.getInstance().cacheLearnYear(organLearnYear);//办学时长
                    CacheTask.getInstance().cacheMainClass(organMainClass);
                    CacheTask.getInstance().cacheTeacherAmount(organTeacherAmount);
                    CacheTask.getInstance().cacheAverageAge(organAverageAge);
                    if (mProfileChangeListener != null) {
                        mProfileChangeListener.change();
                    }
                    NToast.shortToast(mContext, "更新成功");
                    finish();
                } else if ("10001".equals(organResponse.getHead().getCode())) {
                    NToast.shortToast(mContext, "更新失败");
                }
                break;
        }
    }

    public static ProfileChangeListener mProfileChangeListener;

    public interface ProfileChangeListener {
        void change();
    }

    public static void setProfileChangeListener(ProfileChangeListener profileChangeListener) {
        mProfileChangeListener = profileChangeListener;
    }
}
