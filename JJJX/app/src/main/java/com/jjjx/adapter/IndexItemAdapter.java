package com.jjjx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.jjjx.R;

import java.util.List;


/**
 * Created by AMing on 17/7/24.
 * Company RongCloud
 */
public class IndexItemAdapter extends BaseAdapter {

    private Context context;
    private List<String> data;

    public IndexItemAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.index_detail_item, parent, false);
            holder.imageView = (ImageView) convertView.findViewById(R.id.iv_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(data.get(position)).into(holder.imageView);
        return convertView;
    }

    private class ViewHolder {
        ImageView imageView;
    }

    public void refreshAdapter(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
