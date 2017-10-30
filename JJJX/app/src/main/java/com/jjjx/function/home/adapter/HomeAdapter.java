package com.jjjx.function.home.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jjjx.data.response.IndexDataResponse.ParaEntity.ComplaintsEntity;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jjjx.R;

import java.util.List;


/**
 * Created by AMing on 17/7/11.
 * Company RongCloud
 * 首页 适配器
 */

public class HomeAdapter extends BaseAdapter {

    private Context context;
    private List<ComplaintsEntity> data;

    public HomeAdapter(Context context, List<ComplaintsEntity> data) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_index, parent, false);
            holder.classAge = convertView.findViewById(R.id.index_class_age);
            holder.classTime = convertView.findViewById(R.id.index_class_time);
            holder.className = convertView.findViewById(R.id.index_class_name);
            holder.learnYear = convertView.findViewById(R.id.index_learn_year);
            holder.className = convertView.findViewById(R.id.index_class_name);
            holder.classMode = convertView.findViewById(R.id.index_class_mode);
            holder.userName = convertView.findViewById(R.id.index_user_name);
            holder.userHead = convertView.findViewById(R.id.index_user_head);
            holder.distance = convertView.findViewById(R.id.index_distance);
            holder.classFirstImage = convertView.findViewById(R.id.index_class_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ComplaintsEntity positionData = data.get(position);
        if (TextUtils.isEmpty(positionData.getVideo())) {
        }
        holder.className.setText(positionData.getCourseName());
        holder.classMode.setText(positionData.getTeachingNumber());
        holder.userHead.setImageURI(positionData.getHead_portrait());
        holder.classFirstImage.setImageURI(TextUtils.isEmpty(positionData.getFirstFrame()) ? positionData.getPicture() : getFirstPicture(positionData.getFirstFrame()));
        holder.classAge.setText(positionData.getSeniority());
        holder.userName.setText(positionData.getName());
        holder.learnYear.setText(positionData.getRightAge());
        holder.classTime.setText(positionData.getTeachingDate());
        holder.distance.setText(positionData.getDistance());
        return convertView;
    }


    public void refreshAdapter(List<ComplaintsEntity> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addDataAdapter(List<ComplaintsEntity> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
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

        TextView distance;

    }


    private String getFirstPicture(String pictures) {
        if (pictures.contains(",")) {
            String[] arrays = pictures.split(",");
            return arrays[0];
        }
        return pictures;
    }
}
