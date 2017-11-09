package com.jjjx.function.my.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jjjx.network.Constants;
import com.jjjx.R;
import com.jjjx.data.response.UserProfileResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AMing on 17/9/6.
 * Company RongCloud
 */
public class ProfileClassFragment extends android.support.v4.app.Fragment {

    private ListView mListView;
    private ProfileClassAdapter adapter;

    private ArrayList<UserProfileResponse.ParaEntity.CourseReleaseEntity> courseReleaseEntityArrayList = new ArrayList<>();

    public static ProfileClassFragment newInstance(ArrayList<UserProfileResponse.ParaEntity.CourseReleaseEntity> courseReleaseEntityArrayList) {
        ProfileClassFragment classFragment = new ProfileClassFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("class", courseReleaseEntityArrayList);
        classFragment.setArguments(bundle);
        return classFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            courseReleaseEntityArrayList = bundle.getParcelableArrayList("class");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_class, container, false);
        mListView = (ListView) view.findViewById(R.id.profile_class_list);
        adapter = new ProfileClassAdapter(getActivity(), courseReleaseEntityArrayList);
        mListView.setAdapter(adapter);
        if (courseReleaseEntityArrayList != null && courseReleaseEntityArrayList.size() > 0) {
            adapter.refreshAdapter(courseReleaseEntityArrayList);
        }
        return view;
    }


    public class ProfileClassAdapter extends BaseAdapter {
        private Context context;
        private List<UserProfileResponse.ParaEntity.CourseReleaseEntity> data;

        public ProfileClassAdapter(Context context, List<UserProfileResponse.ParaEntity.CourseReleaseEntity> data) {
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
                convertView = LayoutInflater.from(context).inflate(R.layout.item_profile_class, parent, false);
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
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            UserProfileResponse.ParaEntity.CourseReleaseEntity positionData = data.get(position);
//            if (TextUtils.isEmpty(positionData.getVideo())) {
//            }
            holder.className.setText(positionData.getCourseName());
            holder.classMode.setText(positionData.getTeachingNumber());
//            holder.userHead.setImageURI(positionData.getHead_portrait());
            holder.classFirstImage.setImageURI(Constants.DOMAIN + positionData.getPicture());
//            holder.classAge.setText(positionData.getSeniority());
//            holder.userName.setText(positionData.getName());
            holder.learnYear.setText(positionData.getRightAge());
            holder.classTime.setText(positionData.getTeachingDate());
            return convertView;
        }


        public void refreshAdapter(List<UserProfileResponse.ParaEntity.CourseReleaseEntity> data) {
            this.data = data;
            notifyDataSetChanged();
        }

        public void addDataAdapter(List<UserProfileResponse.ParaEntity.CourseReleaseEntity> data) {
            this.data.addAll(data);
            notifyDataSetChanged();
        }

        private class ViewHolder {
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


        private String getFirstPicture(String pictures) {
            if (pictures.contains(",")) {
                String[] arrays = pictures.split(",");
                return arrays[0];
            }
            return pictures;
        }
    }
}
