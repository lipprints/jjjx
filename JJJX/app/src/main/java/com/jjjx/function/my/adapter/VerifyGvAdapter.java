package com.jjjx.function.my.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jjjx.R;
import com.xz.xadapter.XListBaseAdapter;
import com.xz.xadapter.XListViewHolder;

import java.util.List;

/**
 * @author xz
 * @date 2017/11/8 0008
 * 认证  相关证书 图片适配器
 */

public class VerifyGvAdapter extends XListBaseAdapter<String> {
    /**
     * 默认的图片
     */
    public static final String MDEFAULTURI = "MDEFAULTURI";
    private Context mContext;

    public VerifyGvAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public void setListData(List<String> listData) {
        if (listData.size() <6) {
            listData.remove(MDEFAULTURI);
            listData.add(MDEFAULTURI);
        }else{
            listData.remove(MDEFAULTURI);
        }
        super.setListData(listData);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = getConvertView(mContext, R.layout.item_media);
        }
        SimpleDraweeView image = XListViewHolder.get(convertView, R.id.media_image);
        ImageView delete = XListViewHolder.get(convertView, R.id.badge_delete);
        if (TextUtils.equals(mListData.get(position), VerifyGvAdapter.MDEFAULTURI)) {
            delete.setVisibility(View.GONE);
            image.setImageResource(R.drawable.rc_plugin_default);
        } else {
            delete.setVisibility(View.VISIBLE);
            image.setImageURI(mListData.get(position));
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListData.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

}
