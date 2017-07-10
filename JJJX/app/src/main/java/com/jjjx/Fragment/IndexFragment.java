package com.jjjx.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.jjjx.App;
import com.jjjx.OnBDLocationListener;
import com.jjjx.R;
import com.jjjx.utils.NToast;
import com.jjjx.widget.banner.GlideImageLoader;
import com.jjjx.widget.popwinpicker.PopupAdapter;
import com.jjjx.widget.popwinpicker.PopupButton;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by AMing on 17/5/8.
 * Company RongCloud
 */
public class IndexFragment extends Fragment implements OnBDLocationListener, View.OnClickListener {
    private List<String> images;
    private TextView locationTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_index, container, false);
        Banner banner = (Banner) v.findViewById(R.id.banner);
        initPopwinPicker(v);

        locationTextView = (TextView) v.findViewById(R.id.titleBar_city_name);
        locationTextView.setText("定位中...");
        locationTextView.setOnClickListener(this);
        images = new ArrayList<>();
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1499136189&di=cbbd537c7eb6ed7f907d005b19031b98&imgtype=jpg&er=1&src=http%3A%2F%2Fpic2.ooopic.com%2F13%2F58%2F87%2F63bOOOPICb3_1024.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1499136210&di=c0d88cb26a88a6b0cf78a8f7beb49653&imgtype=jpg&er=1&src=http%3A%2F%2Fpic74.nipic.com%2Ffile%2F20150805%2F12033066_235333091000_2.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498541508985&di=decefe3014ec2eba9a1df114460b04c4&imgtype=0&src=http%3A%2F%2Fpic2.ooopic.com%2F11%2F72%2F21%2F20bOOOPIC2c_1024.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498541537797&di=f03bc68d54916ed39d027a7567181661&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F018f1b56f2057232f875a9448ab29f.jpg%40900w_1l_2o_100sh.jpg");
        banner.setImages(images).setImageLoader(new GlideImageLoader()).start();
        banner.setDelayTime(6000);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                switch (position) {
                    case 0:
                        NToast.shortToast(getActivity(), "点击了第 1 个轮播图");
                        break;
                    case 1:
                        NToast.shortToast(getActivity(), "点击了第 2 个轮播图");
                        break;
                    case 2:
                        NToast.shortToast(getActivity(), "点击了第 3 个轮播图");
                        break;
                    case 3:
                        NToast.shortToast(getActivity(), "点击了第 4 个轮播图");
                        break;
                }
            }
        });
        App.getInstance().addOnBDLocationObserver(this);
        return v;
    }


    private BDLocation bdLocation;

    @Override
    public void onLocation(BDLocation bdLocation) {
        this.bdLocation = bdLocation;
        //TODO 切换线程
        locationTextView.setText(bdLocation.getCity());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        App.getInstance().removeOnBDLocationObserver(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titleBar_city_name:
                NToast.longToast(getActivity(), "当前位置: \r\n 经纬度:" + bdLocation.getLongitude() + "---" + bdLocation.getLatitude() +
                        "\r\n 当前城市:" + bdLocation.getCity() +
                        "\r\n 当前区县:" + bdLocation.getDistrict() +
                        "\r\n 位置语音化信息:" + bdLocation.getLocationDescribe() +
                        "\r\n 当前街道信息:" + bdLocation.getStreet() +
                        "\r\n 当前位置:" + bdLocation.getAddrStr()
                );
                break;
        }
    }

    private PopupButton mPopupButton1;
    private PopupButton mPopupButton2;
    private PopupButton mPopupButton3;

    private void initPopwinPicker(View v) {
        mPopupButton1 = (PopupButton) v.findViewById(R.id.popup_button1);
        mPopupButton2 = (PopupButton) v.findViewById(R.id.popup_button2);
        mPopupButton3 = (PopupButton) v.findViewById(R.id.popup_button3);
        final String[] array1 = new String[]{"不限", "舞蹈", "声乐", "美术", "乐器"};
        final String[] array2 = new String[]{"不限", "教龄/建校时间最长", "订单最多", "课次单价最低", "课次单价最高"};
        final String[] array3 = new String[]{"不限", "私教", "机构/学校", "大学生", "离我最近"};
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.popup, null);
        View view2 = inflater.inflate(R.layout.popup2, null);
        View view3 = inflater.inflate(R.layout.popup3, null);
        final PopupAdapter adapter1 = new PopupAdapter(getActivity(), R.layout.popup_item, array1, R.drawable.normal, R.drawable.press);
        final PopupAdapter adapter2 = new PopupAdapter(getActivity(), R.layout.popup_item, array2, R.drawable.normal, R.drawable.press);
        final PopupAdapter adapter3 = new PopupAdapter(getActivity(), R.layout.popup_item, array3, R.drawable.normal, R.drawable.press);
        ListView lv1 = (ListView) view.findViewById(R.id.lv);
        lv1.setAdapter(adapter1);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter1.setPressPostion(i);
                adapter1.notifyDataSetChanged();
                mPopupButton1.setText(array1[i]);
                mPopupButton1.hidePopup();
            }
        });
        mPopupButton1.setPopupView(view);

        ListView lv2 = (ListView) view2.findViewById(R.id.lv);
        lv2.setAdapter(adapter2);
        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter2.setPressPostion(i);
                adapter2.notifyDataSetChanged();
                mPopupButton2.setText(array2[i]);
                mPopupButton2.hidePopup();
            }
        });
        mPopupButton2.setPopupView(view2);

        ListView lv3 = (ListView) view3.findViewById(R.id.lv);
        lv3.setAdapter(adapter3);
        lv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter3.setPressPostion(i);
                adapter3.notifyDataSetChanged();
                mPopupButton3.setText(array3[i]);
                mPopupButton3.hidePopup();
            }
        });
        mPopupButton3.setPopupView(view3);

    }


}
