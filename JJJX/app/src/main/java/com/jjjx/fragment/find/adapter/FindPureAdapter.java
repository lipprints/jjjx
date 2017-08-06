package com.jjjx.fragment.find.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jjjx.R;
import com.jjjx.app.adapter.RvPureDataAdapter;
import com.jjjx.app.adapter.util.RvViewHolder;
import com.jjjx.data.GlideManage;
import com.jjjx.data.response.FindDataResponse;
import com.jjjx.utils.Utils;
import com.jjjx.widget.CircleImageView;
import com.jjjx.widget.SquareImageView;

import java.lang.ref.WeakReference;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public class FindPureAdapter extends RvPureDataAdapter<FindDataResponse.ParaEntity.DiscoverInfoEntity> {
    private GlideManage mGlideManage;
    private Context mContext;
    private int mImageWidth;
    private int mHeadImageWidth;
    private RoundedCornersTransformation mTransformation;

    public FindPureAdapter(GlideManage mGlideManage, Context context) {
        this.mGlideManage = mGlideManage;
        this.mContext = context;
        //计算屏幕的宽度，设置图片大小的
        int phoneWidth = Utils.getPhoneWidth(mContext);
        int marginWidth = Utils.dpToPx(mContext, 30);
        mImageWidth = phoneWidth / 2 - marginWidth;
        //计算头像的px大小
        mHeadImageWidth = Utils.dpToPx(mContext, 35);
        //图像圆角
        mTransformation = new RoundedCornersTransformation(mContext, 20, 0);
    }

    @Override
    public int getItemLayout(int viewType) {
        return R.layout.item_find_pure;
    }

    @Override
    public void onBindViewHolder(RvViewHolder holder, int position) {
        FindDataResponse.ParaEntity.DiscoverInfoEntity die = mDatas.get(position);

        SquareImageView imageView = holder.getView(R.id.ifh_iv);
        final CircleImageView headImageView = holder.getView(R.id.ifh_head);
        holder.setText(R.id.ifh_name, die.getName());
        holder.setText(R.id.ifh_number, "服务器没给点赞数");

        //大图使用处理
        final WeakReference<SquareImageView> imageViewWeakReference = new WeakReference<>(imageView);
        ImageView imageViewWeak = imageViewWeakReference.get();
        if (imageViewWeak != null) {
            String imageUrl;
            if (!TextUtils.isEmpty(die.getFirstFrame())) {
                imageUrl = die.getFirstFrame();
            } else {
                imageUrl = die.getPicture();
            }
            mGlideManage.getRequestManager().load(imageUrl).error(R.color.app_gray_color)
                    .bitmapTransform(mTransformation).crossFade()
                    .placeholder(R.color.app_gray_color).override(mImageWidth, mImageWidth)
                    .into(imageViewWeak);
        }

        //头像处理
        SimpleTarget<GlideDrawable> simpleTarget = new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                headImageView.setImageDrawable(resource);
            }
        };
        final WeakReference<SimpleTarget<GlideDrawable>> simpleWeakReference = new WeakReference<>(simpleTarget);
        SimpleTarget<GlideDrawable> simpleTargetWeak = simpleWeakReference.get();
        if (simpleTargetWeak != null) {

            mGlideManage.getRequestManager().load(die.getHead_portrait()).error(R.color.app_gray_color)
                    .crossFade()
                    .placeholder(R.color.app_gray_color).override(mHeadImageWidth, mHeadImageWidth)
                    .into(simpleTargetWeak);
        }
    }
}
