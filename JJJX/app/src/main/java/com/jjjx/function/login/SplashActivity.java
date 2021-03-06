package com.jjjx.function.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.jjjx.R;
import com.jjjx.function.base.BaseActivity;
import com.jjjx.data.response.IndexDataResponse;
import com.jjjx.function.main.MainActivity;
import com.jjjx.utils.CacheTask;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;


/**
 * Created by AMing on 17/5/11.
 * Company RongCloud
 * 启动页
 */

public class SplashActivity extends BaseActivity {


    private static final int GET_INDEX_DATA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) == Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) {
            super.onCreate(savedInstanceState);
            finish();
            return;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setHeadVisibility(View.GONE);

//        request(GET_INDEX_DATA);

        ImageView imageView = (ImageView) findViewById(R.id.image_logo);
        // 渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(0.1f, 1.0f);
        aa.setDuration(2000);
        imageView.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                if (RongIM.getInstance().getCurrentConnectionStatus() != RongIMClient.ConnectionStatusListener.ConnectionStatus.DISCONNECTED && !TextUtils.isEmpty(CacheTask.getInstance().getToken())) {
                    RongIM.connect(CacheTask.getInstance().getToken(), null);
                }
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                intent.putParcelableArrayListExtra("indexData", arrayList);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationStart(Animation animation) {

            }

        });
    }

    @Override
    public Object doInBackground(int requestCode) throws Exception {
        if (requestCode == GET_INDEX_DATA) {
//            return action.requestIndexData();
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        super.onSuccess(requestCode, result);
        if (requestCode == GET_INDEX_DATA && result != null) {
            IndexDataResponse response = (IndexDataResponse) result;
            if ("10000".equals(response.getHead().getCode())) {
//                List<IndexDataResponse.ParaEntity.ComplaintsEntity> indexData = response.getPara().getComplaints();
//                ArrayList<IndexDataResponse.ParaEntity.ComplaintsEntity> arrayList = (ArrayList<IndexDataResponse.ParaEntity.ComplaintsEntity>) indexData;
//                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                intent.putParcelableArrayListExtra("indexData", arrayList);
//                startActivity(intent);
//                finish();
            }
        }
    }
}
