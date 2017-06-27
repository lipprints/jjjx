package com.jjjx.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjjx.R;
import com.jjjx.utils.NToast;
import com.jjjx.widget.banner.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AMing on 17/5/8.
 * Company RongCloud
 */
public class IndexFragment extends Fragment {
    private List<String> images;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_index, container, false);
        Banner banner = (Banner) v.findViewById(R.id.banner);
        images = new ArrayList<>();
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1499136189&di=cbbd537c7eb6ed7f907d005b19031b98&imgtype=jpg&er=1&src=http%3A%2F%2Fpic2.ooopic.com%2F13%2F58%2F87%2F63bOOOPICb3_1024.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1499136210&di=c0d88cb26a88a6b0cf78a8f7beb49653&imgtype=jpg&er=1&src=http%3A%2F%2Fpic74.nipic.com%2Ffile%2F20150805%2F12033066_235333091000_2.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498541508985&di=decefe3014ec2eba9a1df114460b04c4&imgtype=0&src=http%3A%2F%2Fpic2.ooopic.com%2F11%2F72%2F21%2F20bOOOPIC2c_1024.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498541537797&di=f03bc68d54916ed39d027a7567181661&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F018f1b56f2057232f875a9448ab29f.jpg%40900w_1l_2o_100sh.jpg");
        banner.setImages(images).setImageLoader(new GlideImageLoader()).start();
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
        return v;
    }
}
