package com.jjjx.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.baidu.location.BDLocation;
import com.jjjx.App;
import com.jjjx.OnBDLocationListener;
import com.jjjx.R;

/**
 * Created by AMing on 17/7/9.
 * Company RongCloud
 */
public class ClassModeActivity extends BaseActivity implements OnBDLocationListener {
    EditText address;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_mode);
        setTitle("上课方式");
        address = (EditText) findViewById(R.id.class_mode_address);
        intent = new Intent(ClassModeActivity.this,PublishActivity.class);
        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                intent.putExtra("classAddress",charSequence.toString());
                setResult(99,intent);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        App.getInstance().addOnBDLocationObserver(this);
    }

    @Override
    public void onLocation(BDLocation bdLocation) {
        if (bdLocation != null && !TextUtils.isEmpty(bdLocation.getLocationDescribe())) {
            address.setText(bdLocation.getLocationDescribe());

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getInstance().removeOnBDLocationObserver(this);
    }
}
