package com.jjjx.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jjjx.R;


/**
 * Created by weiqinxiao on 2017/2/7.
 */

public class ListItemTextView extends LinearLayout {

    private TextView detailTextView;
    private TextView titleTextView;
    private ImageView arrowImageView;

    public ListItemTextView(Context context) {
        super(context);
        initView(null);
    }

    public ListItemTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public ImageView getArrowImageView() {
        return arrowImageView;
    }

    private void initView(AttributeSet attrs) {
        inflate(getContext(), R.layout.rce_text_list_item, this);
        arrowImageView = findViewById(R.id.iv_arrow);
        titleTextView =  findViewById(R.id.tv_title);
        detailTextView =  findViewById(R.id.tv_detail);
        if (attrs != null) {
            TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.rce_tv_list_item);
            boolean enableArrow = attributes.getBoolean(R.styleable.rce_tv_list_item_tv_list_item_allow_arrow, false);
            arrowImageView.setVisibility(enableArrow ? VISIBLE : GONE);
            if (!enableArrow) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) detailTextView.getLayoutParams();
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.addRule(RelativeLayout.ALIGN_PARENT_END);
                detailTextView.setLayoutParams(params);
            }
            CharSequence ltext = attributes.getText(R.styleable.rce_tv_list_item_tv_list_item_title);
            titleTextView.setText(ltext);
            int lcolor = attributes.getColor(R.styleable.rce_tv_list_item_tv_list_item_title_color, getResources().getColor(R.color.color_normal_text));
            titleTextView.setTextColor(lcolor);
            CharSequence rtext = attributes.getText(R.styleable.rce_tv_list_item_tv_list_item_detail);
            detailTextView.setText(rtext);
            int rcolor = attributes.getColor(R.styleable.rce_tv_list_item_tv_list_item_detail_color, getResources().getColor(R.color.color_gray_text));
            detailTextView.setTextColor(rcolor);
            Drawable drawable = attributes.getDrawable(R.styleable.rce_tv_list_item_tv_list_item_title_icon);
            if (drawable != null) {
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                titleTextView.setCompoundDrawables(drawable, null, null, null);
            }
            attributes.recycle();
        }
        setBackgroundResource(R.drawable.rce_selector_item_hover);
    }

    public void setDetail(String text) {
        detailTextView.setText(text);
    }

    public void setTitle(String text) {
        titleTextView.setText(text);
    }

    public String getTitle() {
        return titleTextView.getText().toString();
    }

    public void setArrowVisibility(int visibility) {
        arrowImageView.setVisibility(visibility);
        if (visibility == View.GONE) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) detailTextView.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.ALIGN_PARENT_END);
            detailTextView.setLayoutParams(params);
        }
    }
}
