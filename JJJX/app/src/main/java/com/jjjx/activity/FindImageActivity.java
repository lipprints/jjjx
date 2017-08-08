package com.jjjx.activity;

import android.os.Bundle;

import com.jjjx.R;
import com.jjjx.data.response.FindDataResponse;

/**
 * Created by AMing on 17/8/8.
 * Company RongCloud
 */
public class FindImageActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_image);
        setTitle("发现详情");
        FindDataResponse.ParaEntity.DiscoverInfoEntity entity = getIntent().getParcelableExtra("FindImageEntity");
    }
}
