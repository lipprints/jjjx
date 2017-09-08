package com.jjjx.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jjjx.R;
import com.jjjx.data.response.UserProfileResponse;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by AMing on 17/9/6.
 * Company RongCloud
 */
public class ProfileFindFragment extends android.support.v4.app.Fragment {

    private ListView mListView;
    private ProfileFindAdapter adapter;

    private ArrayList<UserProfileResponse.ParaEntity.DiscoverInfoEntity> discoverInfoEntityList;

    public static ProfileFindFragment newInstance(ArrayList<UserProfileResponse.ParaEntity.DiscoverInfoEntity> discoverInfoEntityList) {
        ProfileFindFragment findFragment = new ProfileFindFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("find", discoverInfoEntityList);
        findFragment.setArguments(bundle);
        return findFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            discoverInfoEntityList = bundle.getParcelableArrayList("find");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_find, container, false);
        mListView = (ListView) view.findViewById(R.id.profile_find_list);
        adapter = new ProfileFindAdapter(getActivity(), discoverInfoEntityList);
        mListView.setAdapter(adapter);
        if (discoverInfoEntityList != null && discoverInfoEntityList.size() > 0) {
            adapter.refreshAdapter(discoverInfoEntityList);
        }
        return view;
    }


    public class ProfileFindAdapter extends BaseAdapter {
        private Context context;
        private List<UserProfileResponse.ParaEntity.DiscoverInfoEntity> data;

        public ProfileFindAdapter(Context context, List<UserProfileResponse.ParaEntity.DiscoverInfoEntity> data) {
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
                convertView = LayoutInflater.from(context).inflate(R.layout.item_profile_find, parent, false);
                holder.classFirstImage = (SimpleDraweeView) convertView.findViewById(R.id.index_class_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.classFirstImage.setImageURI(data.get(position).getPicture());
            return convertView;
        }


        public void refreshAdapter(List<UserProfileResponse.ParaEntity.DiscoverInfoEntity> data) {
            this.data = data;
            notifyDataSetChanged();
        }

        public void addDataAdapter(List<UserProfileResponse.ParaEntity.DiscoverInfoEntity> data) {
            this.data.addAll(data);
            notifyDataSetChanged();
        }

        private class ViewHolder {
            /**
             * 课程图片第一张
             */
            SimpleDraweeView classFirstImage;


        }

    }
}
