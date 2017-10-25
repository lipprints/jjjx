package com.jjjx.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jjjx.Constants;
import com.jjjx.R;
import com.jjjx.data.response.AttentionInfoListResponse;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by AMing on 17/9/8.
 * Company RongCloud
 */
public class MyFollowListActivity extends BaseActivity {

    private static final int GET_FOLLOW_LIST = 601;
    private ListView mListView;
    private FollowListAdapter adapter;
    private List<AttentionInfoListResponse.ParaEntity.ComplaintsEntity> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_list);
        setTitle("我关注的用户");
        mListView = (ListView) findViewById(R.id.follow_list_view);
        adapter = new FollowListAdapter(this, data);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AttentionInfoListResponse.ParaEntity.ComplaintsEntity entity = (AttentionInfoListResponse.ParaEntity.ComplaintsEntity) adapter.getItem(i);
                Intent intent = new Intent(MyFollowListActivity.this, UserProfileActivity.class);
                intent.putExtra("userId", String.valueOf(entity.getUser_id()));
                startActivity(intent);
            }
        });
        request(GET_FOLLOW_LIST);
    }

    @Override
    public Object doInBackground(int requestCode) throws Exception {
        return action.getMyAttentionInfoList();
    }


    @Override
    public void onSuccess(int requestCode, Object result) {
        if (requestCode == GET_FOLLOW_LIST && result != null) {
            AttentionInfoListResponse response = (AttentionInfoListResponse) result;
            if ("10000".equals(response.getHead().getCode())) {
                if (response.getPara().getComplaints().size() > 0) {
                    adapter.refreshAdapter(response.getPara().getComplaints());
                } else {
                    //TODO 无数据
                }
            }
        }
    }

    public class FollowListAdapter extends BaseAdapter {

        private Context context;
        private List<AttentionInfoListResponse.ParaEntity.ComplaintsEntity> data;

        public FollowListAdapter(Context context, List<AttentionInfoListResponse.ParaEntity.ComplaintsEntity> data) {
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
                convertView = LayoutInflater.from(context).inflate(R.layout.item_follow, parent, false);
                holder.head = (ImageView) convertView.findViewById(R.id.follow_head);
                holder.name = (TextView) convertView.findViewById(R.id.follow_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            AttentionInfoListResponse.ParaEntity.ComplaintsEntity positionData = data.get(position);
            Glide.with(context).load(Constants.DOMAIN + positionData.getHead_portrait()).into(holder.head);
            holder.name.setText(positionData.getName());
            return convertView;
        }


        public void refreshAdapter(List<AttentionInfoListResponse.ParaEntity.ComplaintsEntity> data) {
            this.data = data;
            notifyDataSetChanged();
        }


        private class ViewHolder {
            ImageView head;
            TextView name;

        }
    }
}
