package com.jjjx.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jjjx.R;
import com.jjjx.adapter.IndexAdapter;
import com.jjjx.data.response.IndexDataResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AMing on 17/8/31.
 * Company RongCloud
 */
public class MyCollectionsActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private static final int GET_MY_COLLECTIONS = 108;
    private ListView mListView;
    private IndexAdapter adapter;
    private List<IndexDataResponse.ParaEntity.ComplaintsEntity> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);
        setTitle("我的收藏");
        mListView = (ListView) findViewById(R.id.my_collections_list);
        adapter = new IndexAdapter(this, data);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
        request(GET_MY_COLLECTIONS);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public Object doInBackground(int requestCode) throws Exception {
        return action.getMyCollections();
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        IndexDataResponse response = (IndexDataResponse) result;
        if ("10000".equals(response.getHead().getCode())) {
            List<IndexDataResponse.ParaEntity.ComplaintsEntity> complaintsEntities = response.getPara().getComplaints();
            if (complaintsEntities.size() > 0) {
                adapter.refreshAdapter(complaintsEntities);
            } else {
                //TODO 无收藏
            }
        }
    }
}
