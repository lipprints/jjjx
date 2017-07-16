package com.jjjx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jjjx.R;
import com.jjjx.model.IndexItemModel;

import java.util.List;

/**
 * Created by AMing on 17/7/11.
 * Company RongCloud
 */

public class IndexAdapter extends BaseAdapter {

    private Context context;
    private List<IndexItemModel> data;

    public IndexAdapter(Context context, List<IndexItemModel> data) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_index,parent,false);
            holder.classAge = (TextView) convertView.findViewById(R.id.index_class_age);
            holder.classTime = (TextView) convertView.findViewById(R.id.index_class_time);
            holder.className = (TextView) convertView.findViewById(R.id.index_class_name);
            holder.learnYear = (TextView) convertView.findViewById(R.id.index_learn_year);
            holder.className = (TextView) convertView.findViewById(R.id.index_class_name);
            holder.classMode = (TextView) convertView.findViewById(R.id.index_class_mode);
            holder.userName = (TextView) convertView.findViewById(R.id.index_user_name);
            holder.userHead = (SimpleDraweeView) convertView.findViewById(R.id.index_user_head);
            holder.classFirstImage = (SimpleDraweeView) convertView.findViewById(R.id.index_class_image);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        //TODO show data
        return convertView;
    }

    public void refreshAdapter(List<IndexItemModel> data){
        this.data = data;
        notifyDataSetChanged();
    }

   private class ViewHolder{
        /**
         * 授课时间
         */
        TextView classTime;
        /**
         * 适学年龄
         */
        TextView classAge;
        /**
         * 课程名
         */
        TextView className;
        /**
         * 教龄
         */
        TextView learnYear;
        /**
         * 授课方式
         */
        TextView classMode;
        /**
         * 用户昵称
         */
        TextView userName;
        /**
         * 用户头像
         */
        SimpleDraweeView userHead;
        /**
         * 课程图片第一张
         */
        SimpleDraweeView classFirstImage;

    }
}
