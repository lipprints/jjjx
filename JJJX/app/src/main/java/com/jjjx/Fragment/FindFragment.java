package com.jjjx.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjjx.R;
import com.jjjx.widget.WheelView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by AMing on 17/5/8.
 * Company RongCloud
 */
public class FindFragment extends android.support.v4.app.Fragment {
    private List<String> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_find, container, false);
        v.findViewById(R.id.picker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View outerView = LayoutInflater.from(getActivity()).inflate(R.layout.wheel_view, null);
                WheelView wv = (WheelView) outerView.findViewById(R.id.wheel_view_wv);
                wv.setOffset(2);
                wv.setItems(list);
                wv.setSeletion(3);
                wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        Log.d(TAG, "[Dialog]selectedIndex: " + selectedIndex + ", item: " + item);
                    }
                });

                new AlertDialog.Builder(getActivity())
                        .setTitle("WheelView in Dialog")
                        .setView(outerView)
                        .setPositiveButton("OK", null)
                        .show();
            }
        });
        list.add("1年");
        list.add("2年");
        list.add("3年");
        list.add("4年");
        list.add("5年");
        list.add("6年");
        list.add("7年");
        list.add("8年");
        list.add("9年");
        list.add("10年");


        return v;
    }
}
