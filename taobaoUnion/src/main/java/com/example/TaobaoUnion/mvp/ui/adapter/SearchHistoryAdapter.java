package com.example.TaobaoUnion.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.TaobaoUnion.R;

public class SearchHistoryAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SearchHistoryAdapter() {
        super(R.layout.rv_item_search_history);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.addOnClickListener(R.id.iv_remove);
        helper.setText(R.id.tv_key, item);
        View view = helper.getView(R.id.iv_remove);
        view.setVisibility(View.GONE);
//        helper.addOnClickListener(R.id.iv_remove);
    }
}
