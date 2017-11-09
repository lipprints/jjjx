package com.jjjx.function.find.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jjjx.R;
import com.jjjx.data.response.CommentListResponse;

import java.util.List;


/**
 * Created by AMing on 17/9/14.
 * Company RongCloud
 */

public class CommentAdapter extends BaseAdapter {

    private List<CommentListResponse.ParaEntity.DiscoverInfoEntity> data;
    private Context context;


    public CommentAdapter(List<CommentListResponse.ParaEntity.DiscoverInfoEntity> entityList, Context context) {
        this.data = entityList;
        this.context = context;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
            holder.head = (ImageView) convertView.findViewById(R.id.comment_item_head);
            holder.name = (TextView) convertView.findViewById(R.id.comment_item_name);
            holder.time = (TextView) convertView.findViewById(R.id.comment_item_time);
            holder.content = (TextView) convertView.findViewById(R.id.comment_item_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CommentListResponse.ParaEntity.DiscoverInfoEntity discoverInfoEntity = data.get(position);
        Glide.with(context).load(discoverInfoEntity.getHead_portrait()).into(holder.head);
        holder.name.setText(discoverInfoEntity.getName());
        holder.time.setText(discoverInfoEntity.getCreatetime());
        holder.content.setText(discoverInfoEntity.getContent());
        return convertView;
    }


    public void refreshAdapter(List<CommentListResponse.ParaEntity.DiscoverInfoEntity> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addDataAdapter(List<CommentListResponse.ParaEntity.DiscoverInfoEntity> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        ImageView head;
        TextView name;
        TextView time;
        TextView content;
    }
}
