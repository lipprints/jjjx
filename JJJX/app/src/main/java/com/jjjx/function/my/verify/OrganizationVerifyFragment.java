package com.jjjx.function.my.verify;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.jjjx.R;
import com.jjjx.function.base.XBaseFragment;
import com.jjjx.function.my.adapter.VerifyGvAdapter;
import com.jjjx.utils.ToastUtil;
import com.jjjx.widget.MediaGridView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xz
 * @date 2017/11/8 0008
 * 机构认证
 */

public class OrganizationVerifyFragment extends XBaseFragment implements View.OnClickListener {

    //选择图片的返回
    public static final int TEACHER_VERIFY_SELECT_FIGURE = 11;

    private EditText mNameEt;
    private EditText mCardEt;
    private TextView mCityTv;
    private MediaGridView mImageGv;
    private VerifyGvAdapter mAdapter;

    /**
     * 省集合
     */
    private ArrayList<String> mListProvince;

    /**
     * 市集合
     */
    private ArrayList<ArrayList<String>> mListcity;
    private OptionsPickerView mPvOptions;


    @Override
    protected int getContentView() {
        return R.layout.fragment_organization_verify;
    }

    @Override
    protected void initView(View view) {
        mNameEt = find(R.id.fov_name);
        mCardEt = find(R.id.fov_card);
        LinearLayout cityLayout = find(R.id.fov_city_layout);
        mCityTv = find(R.id.fov_city);
        mImageGv = find(R.id.fov_image);

        mAdapter = new VerifyGvAdapter(getContext());
        mImageGv.setAdapter(mAdapter);
        mImageGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = (String) mAdapter.getItem(position);
                if (TextUtils.equals(str, VerifyGvAdapter.MDEFAULTURI)) {
                    Matisse.from(OrganizationVerifyFragment.this)
                            .choose(MimeType.ofImage())
                            .countable(true)
                            .maxSelectable(1)
                            .showSingleMediaType(true)
                            .theme(R.style.Matisse_Zhihu)
                            .captureStrategy(new CaptureStrategy(true, "com.zhihu.matisse.sample.fileprovider"))
                            .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                            .thumbnailScale(0.85f)
                            .imageEngine(new GlideEngine())
                            .forResult(TeacherVerifyFragment.TEACHER_VERIFY_SELECT_FIGURE);
                }
            }
        });
        cityLayout.setOnClickListener(this);

        //初始化选择器
        mPvOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                mCityTv.setText(mListProvince.get(options1) + "-" + mListcity.get(options1).get(options2));
            }
        }).setTitleText("城市选择")
                //设置滚轮文字大小
                .setContentTextSize(20)
                //设置分割线的颜色
                .setDividerColor(ContextCompat.getColor(getContext(), R.color.app_divider_color))
                //默认选中项
                .setSelectOptions(0, 0)
                .setBgColor(Color.WHITE)
                .setTitleBgColor(ContextCompat.getColor(getContext(), R.color.basecolor))
                .setTitleColor(ContextCompat.getColor(getContext(), R.color.app_txt_black_9))
                .setCancelColor(ContextCompat.getColor(getContext(), R.color.app_txt_black_9))
                .setSubmitColor(ContextCompat.getColor(getContext(), R.color.app_txt_black_0))
                .setTextColorCenter(ContextCompat.getColor(getContext(), R.color.app_txt_black_9))
                //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isCenterLabel(true)
                .setLabels("", "", "")
                //设置外部遮罩颜色
                .setBackgroundId(0x66000000)
                .build();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fov_city_layout:
                //选择 城市
                if (mPvOptions != null && mListProvince != null && mListcity != null) {
                    mPvOptions.setPicker(mListProvince, mListcity);
                    mPvOptions.show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void xLoad() {
        mAdapter.setListData(mAdapter.getListData());
    }

    /**
     * 设置省份，城市
     *
     * @param listProvince 省份
     * @param listcity     城市
     */
    public void setProvinceCity(ArrayList<String> listProvince, ArrayList<ArrayList<String>> listcity) {
        mListProvince = listProvince;
        mListcity = listcity;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TeacherVerifyFragment.TEACHER_VERIFY_SELECT_FIGURE) {
            //选择封面图片
            if (resultCode == Activity.RESULT_OK && data != null) {
                List<String> ImageStrs = Matisse.obtainPathResult(data);
                if (ImageStrs.size() > 0) {
                    List<String> listData = mAdapter.getListData();
                    listData.add("file://" + ImageStrs.get(0));
                    mAdapter.setListData(listData);
                } else {
                    ToastUtil.showToast("图片选择错误");
                }
            }
        }
    }

    @Override
    protected void closeFragment() {
        mListcity.clear();
        mListProvince.clear();
    }
}
