package com.example.TaobaoUnion.mvp.ui.adapter;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.TaobaoUnion.R;
import com.example.TaobaoUnion.app.data.api.cache.UrlUtils;
import com.example.TaobaoUnion.app.data.entity.Preferential;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;

public class PreferentialAdapter extends BaseQuickAdapter<Preferential.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean, BaseViewHolder> {

    public PreferentialAdapter() {
        super(R.layout.item_preferential);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Preferential.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean data) {
        helper.setText(R.id.on_sell_content_title_tv, data.getTitle());
        String coverPath = UrlUtils.getCoverPath(data.getPict_url());
        ArmsUtils.obtainAppComponentFromContext(mContext)
                .imageLoader()
                .loadImage(mContext, ImageConfigImpl
                        .builder()
                        .imageView(helper.getView(R.id.on_sell_cover))
                        .url(coverPath)
                        .fallback(R.mipmap.no_image)
                        .build());
        String originalPrise = data.getZk_final_price();
        TextView view = helper.getView(R.id.on_sell_origin_prise_tv);
        view.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        helper.setText(R.id.on_sell_origin_prise_tv, String.format(mContext.getString(R.string.text_goods_original_prise), originalPrise));
        int couponAmount = data.getCoupon_amount();
        float originPriseFloat = Float.parseFloat(originalPrise);
        float finalPrise = originPriseFloat - couponAmount;
       helper.setText(R.id.on_sell_off_prise_tv, "券后价:" + String.format("%.2f",finalPrise));
    }
}
