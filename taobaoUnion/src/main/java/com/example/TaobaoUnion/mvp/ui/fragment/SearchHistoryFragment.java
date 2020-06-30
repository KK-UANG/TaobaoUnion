package com.example.TaobaoUnion.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.TaobaoUnion.app.base.MySupportFragment;
import com.example.TaobaoUnion.app.data.entity.SearchRecommend;
import com.example.TaobaoUnion.mvp.ui.activity.SearchActivity;
import com.example.TaobaoUnion.mvp.ui.adapter.SearchHistoryAdapter;
import com.example.TaobaoUnion.mvp.ui.adapter.SearchRecommendAdapter;
import com.example.TaobaoUnion.mvp.ui.utils.RvScrollTopUtils;
import com.example.TaobaoUnion.mvp.ui.utils.SPUtils;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.TaobaoUnion.di.component.DaggerSearchHistoryComponent;
import com.example.TaobaoUnion.mvp.contract.SearchHistoryContract;
import com.example.TaobaoUnion.mvp.presenter.SearchHistoryPresenter;

import com.example.TaobaoUnion.R;
import com.lxj.xpopup.XPopup;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/24/2020 20:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SearchHistoryFragment extends MySupportFragment<SearchHistoryPresenter> implements SearchHistoryContract.View {

    @BindView(R.id.rv_hot)
    RecyclerView rv_hot;
    @BindView(R.id.ll_history)
    LinearLayout ll_history;
    @BindView(R.id.rv_history)
    RecyclerView rv_history;

    @OnClick(R.id.tv_clean)
    public void retry() {
        new XPopup.Builder(getContext())
                .asConfirm(null, "确定要清除搜索历史？",
                () -> {
                    mHistoryAdapter.setNewData(null);
                    SPUtils.getInstance(getContext()).save(mHistoryAdapter.getData());
                    changeHistoryVisible();
                })
                .show();
    }

    private SearchRecommendAdapter mRecommendAdapter;
    private SearchHistoryAdapter mHistoryAdapter;

    public static SearchHistoryFragment newInstance() {
        SearchHistoryFragment fragment = new SearchHistoryFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSearchHistoryComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_history, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initAdapter();
        initHistory();
        if (mPresenter != null) {
            mPresenter.getSearchRecommend();
        }
    }

    private void initHistory() {
        List<String> data = SPUtils.getInstance(getContext()).getHistoryList();
        mHistoryAdapter.setNewData(data);
        changeHistoryVisible();
    }

    public void addHistory(String key) {
        List<String> datas = mHistoryAdapter.getData();
        int index = datas.indexOf(key);
        if (index == 0) {
            return;
        }
        if (index > 0) {
            mHistoryAdapter.remove(index);
        }
        mHistoryAdapter.addData(0, key);
        if (mHistoryAdapter.getData().size() > 10) {
            mHistoryAdapter.remove(10);
        }
        SPUtils.getInstance(getContext()).save(mHistoryAdapter.getData());
        RvScrollTopUtils.smoothScrollTop(rv_history);
        changeHistoryVisible();
    }

    private void changeHistoryVisible() {
        if (mHistoryAdapter == null) {
            ll_history.setVisibility(View.GONE);
        } else {
            if (mHistoryAdapter.getData().isEmpty()) {
                ll_history.setVisibility(View.GONE);
            } else {
                ll_history.setVisibility(View.VISIBLE);
            }
        }
    }

    private void initAdapter() {
        rv_hot.setNestedScrollingEnabled(false);
        rv_hot.setHasFixedSize(true);
        rv_hot.setLayoutManager(new FlexboxLayoutManager(getContext()));
        mRecommendAdapter = new SearchRecommendAdapter();
        rv_hot.setAdapter(mRecommendAdapter);

        mRecommendAdapter.setOnItemClickListener((adapter, view, position) -> {
            SearchRecommend.DataBean item = mRecommendAdapter.getItem(position);
            if (item != null) {
                search(item.getKeyword());
            }
        });

        rv_history.setLayoutManager(new FlexboxLayoutManager(getContext()));
        mHistoryAdapter = new SearchHistoryAdapter();
        rv_history.setAdapter(mHistoryAdapter);

        mHistoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            String item = mHistoryAdapter.getItem(position);
            if (item != null) {
                search(item);
            }
        });

        mHistoryAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            View IvRemove = view.findViewById(R.id.iv_remove);
            IvRemove.setVisibility(View.VISIBLE);
            return true;
        });

        mHistoryAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.iv_remove) {
                mHistoryAdapter.remove(position);
                SPUtils.getInstance(getContext()).save(mHistoryAdapter.getData());
                changeHistoryVisible();
            }
        });
    }

    private void search(String key) {
        if (getActivity() instanceof SearchActivity) {
            SearchActivity activity = (SearchActivity) getActivity();
            activity.Search(key);
        }
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    @Override
    public void setSearchRecommend(List<SearchRecommend.DataBean> data) {
        if (data.size() > 0) {
            mRecommendAdapter.setNewData(data);
        }
    }
}
