package com.example.TaobaoUnion.mvp.ui.adapter;

import android.support.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.TaobaoUnion.R;
import com.example.TaobaoUnion.app.data.entity.SearchRecommend;

public class SearchRecommendAdapter extends BaseQuickAdapter<SearchRecommend.DataBean, BaseViewHolder> {

    public SearchRecommendAdapter() {
        super(R.layout.rv_item_search_hot);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SearchRecommend.DataBean item) {
        helper.setText(R.id.tv_name, item.getKeyword());
    }
}
