package com.example.TaobaoUnion.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.TaobaoUnion.R;
import com.example.TaobaoUnion.app.data.entity.FeaturedContent;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;

public class FeaturedContentAdapter extends BaseQuickAdapter<FeaturedContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean, BaseViewHolder> {

    public FeaturedContentAdapter() {
        super(R.layout.item_featured_content);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FeaturedContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean item) {
        helper.setText(R.id.selected_title, item.getTitle());
        ArmsUtils.obtainAppComponentFromContext(mContext)
                .imageLoader()
                .loadImage(mContext, ImageConfigImpl
                        .builder()
                        .imageView(helper.getView(R.id.selected_cover))
                        .url(item.getPict_url())
                        .fallback(R.mipmap.no_image)
                        .build());
        TextView view = helper.getView(R.id.selected_buy_btn);
        if (TextUtils.isEmpty(item.getCoupon_click_url())) {
            helper.setText(R.id.selected_original_prise,"晚啦，没有优惠券了");
            view.setVisibility(View.GONE);
        } else {
            helper.setText(R.id.selected_original_prise, "原价：" + item.getZk_final_price());
            view.setVisibility(View.VISIBLE);
        }
        TextView view2 = helper.getView(R.id.selected_off_prise);
        helper.addOnClickListener(R.id.selected_buy_btn);
        if (TextUtils.isEmpty(item.getCoupon_info())) {
            view2.setVisibility(View.GONE);
        } else {
            view2.setVisibility(View.VISIBLE);
            view2.setText(item.getCoupon_info());
        }
    }


}
