package com.jjjx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.jjjx.R;
import com.jjjx.function.home.adapter.HomeAdapter;
import com.jjjx.data.response.IndexDataResponse;
import com.jjjx.utils.NToast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by AMing on 17/7/27.
 * Company RongCloud
 */

public class SearchActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private EditText mSearchEdit;
    private TextView mSearchText;
    public static final int SEARCH = 301;
    private HomeAdapter adapter;
    private List<IndexDataResponse.ParaEntity.ComplaintsEntity> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle("搜索");
        mSearchEdit = (EditText) findViewById(R.id.search_edit);
        mSearchText = (TextView) findViewById(R.id.search_btn);
        mSearchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mSearchEdit.getText().toString().trim())) {
                    NToast.shortToast(mContext, "搜索内容不能为空");
                    return;
                }
                request(SEARCH);
            }
        });
        ListView searchListView = (ListView) findViewById(R.id.search_list);
        searchListView.setOnItemClickListener(this);
        adapter = new HomeAdapter(mContext, data);
        searchListView.setAdapter(adapter);
    }

    @Override
    public Object doInBackground(int requestCode) throws Exception {
        return action.search(mSearchEdit.getText().toString().trim());
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        if (result != null) {
            IndexDataResponse response = (IndexDataResponse) result;
            if ("10000".equals(response.getHead().getCode())) {
                if (response.getPara().getComplaints().size() > 0) {
                    adapter.refreshAdapter(response.getPara().getComplaints());
                } else {
                    adapter.refreshAdapter(data);
                    NToast.shortToast(mContext, "没有搜索到 " + mSearchEdit.getText().toString().trim() + " 相关数据");
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        IndexDataResponse.ParaEntity.ComplaintsEntity entity = (IndexDataResponse.ParaEntity.ComplaintsEntity) adapter.getItem(i);
        Intent intent = new Intent(mContext, IndexItemDetailsActivity.class);
        intent.putExtra("indexItemData", entity);
        startActivity(intent);
    }
}
