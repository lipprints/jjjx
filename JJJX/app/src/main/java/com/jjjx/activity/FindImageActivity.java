package com.jjjx.activity;

import android.os.Bundle;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jjjx.R;
import com.jjjx.data.response.FindDataResponse;

/**
 * Created by AMing on 17/8/8.
 * Company RongCloud
 */
public class FindImageActivity extends BaseActivity {

    SimpleDraweeView simpleDraweeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_image);
        setTitle("发现详情");
        FindDataResponse.ParaEntity.DiscoverInfoEntity entity = getIntent().getParcelableExtra("FindImageEntity");
        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.find_image);
        simpleDraweeView.setImageURI(entity.getPicture());
    }
}
