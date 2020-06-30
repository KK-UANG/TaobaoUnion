package com.example.TaobaoUnion.mvp.ui.adapter;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.TaobaoUnion.R;
import com.example.TaobaoUnion.app.data.api.cache.UrlUtils;
import com.example.TaobaoUnion.app.data.entity.HomeProducts;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;

public class HomeProductsAdapter extends BaseQuickAdapter<HomeProducts.DataBean, BaseViewHolder> {

    public HomeProductsAdapter() {
        super(R.layout.item_linear_goods_content);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, HomeProducts.DataBean item) {
        helper.setText(R.id.goods_title, item.getTitle());
        String url = item.getPict_url();
        String coverPath = UrlUtils.getCoverPath(url);
        ArmsUtils.obtainAppComponentFromContext(mContext)
                .imageLoader()
                .loadImage(mContext, ImageConfigImpl
                        .builder()
                        .imageView(helper.getView(R.id.goods_cover))
                        .url(coverPath)
                        .fallback(R.mipmap.no_image)
                        .build());
        String finalPrise = item.getZk_final_price();
        long couponAmount = item.getCoupon_amount();
        float resultPrise = Float.parseFloat(finalPrise) - couponAmount;
        helper.setText(R.id.goods_after_off_prise, String.format("%.2f", resultPrise));
        helper.setText(R.id.goods_off_prise, String.format(mContext.getString(R.string.text_goods_off_prise), couponAmount));
        TextView view = helper.getView(R.id.goods_original_prise);
        view.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        helper.setText(R.id.goods_original_prise, String.format(mContext.getString(R.string.text_goods_original_prise), finalPrise));
        helper.setText(R.id.goods_sell_count, String.format(mContext.getString(R.string.text_goods_sell_count), item.getVolume()));
    }
}
