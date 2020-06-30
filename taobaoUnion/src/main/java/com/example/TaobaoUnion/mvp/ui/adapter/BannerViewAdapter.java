package com.example.TaobaoUnion.mvp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.TaobaoUnion.R;
import com.example.TaobaoUnion.app.data.api.cache.UrlUtils;
import com.example.TaobaoUnion.app.data.entity.HomeProducts;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.zhouwei.mzbanner.holder.MZViewHolder;

public class BannerViewAdapter implements MZViewHolder<HomeProducts.DataBean> {
    private ImageView mImageView;

    @Override
    public View createView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_banner, null);
        mImageView = view.findViewById(R.id.banner_image);
        return view;
    }

    @Override
    public void onBind(Context context, int position, HomeProducts.DataBean banner) {
        String url = banner.getPict_url();
        String coverPath = UrlUtils.getCoverPath(url);
        ArmsUtils.obtainAppComponentFromContext(context)
                .imageLoader()
                .loadImage(context, ImageConfigImpl
                        .builder()
                        .imageView(mImageView)
                        .url(coverPath)
                        .fallback(R.mipmap.no_image)
                        .build());
    }
}
