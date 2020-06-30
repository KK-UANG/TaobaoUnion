package com.example.TaobaoUnion.mvp.ui.adapter;

import android.support.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.TaobaoUnion.R;
import com.example.TaobaoUnion.app.data.entity.FeaturedCategories;


public class CategoriesAdapter extends BaseQuickAdapter<FeaturedCategories.DataBean, BaseViewHolder> {

    public int idx = 0;

    public CategoriesAdapter() {
        super(R.layout.item_classification);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FeaturedCategories.DataBean item) {
        helper.setText(R.id.left_category_tv, item.getFavorites_title());
        if (helper.getAdapterPosition() == idx) {
            helper.setBackgroundColor(R.id.left_category_tv, mContext.getResources().getColor(R.color.colorPageBg));
        }else {
            helper.setBackgroundColor(R.id.left_category_tv, mContext.getResources().getColor(R.color.white));
        }
    }


}
