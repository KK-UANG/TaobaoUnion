package com.example.TaobaoUnion.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.TaobaoUnion.app.base.MySupportFragment;
import com.example.TaobaoUnion.app.data.entity.SearchResult;
import com.example.TaobaoUnion.mvp.ui.activity.TicketActivity;
import com.example.TaobaoUnion.mvp.ui.adapter.SearchResultsAdapter;
import com.example.TaobaoUnion.mvp.ui.utils.SizeUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.TaobaoUnion.di.component.DaggerSearchResultComponent;
import com.example.TaobaoUnion.mvp.contract.SearchResultContract;
import com.example.TaobaoUnion.mvp.presenter.SearchResultPresenter;

import com.example.TaobaoUnion.R;

import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/24/2020 21:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SearchResultFragment extends MySupportFragment<SearchResultPresenter> implements SearchResultContract.View {

    @BindView(R.id.search_result_rv)
    RecyclerView mResultsRv;
    private SearchResultsAdapter mResultsAdapter;
    private View mLoading;
    private View mEmpty;
    private String mKey;

    public static SearchResultFragment newInstance() {
        SearchResultFragment fragment = new SearchResultFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSearchResultComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_result, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initView();
        initAdapter();
        initListener();
    }

    public void search(String key) {
        mKey = key;
        if (mPresenter != null) {
            mPresenter.getSearchResults(key);
        }
    }

    private void initView() {

        mEmpty = getLayoutInflater().inflate(R.layout.fragment_empty, null, false);
        mLoading = getLayoutInflater().inflate(R.layout.fragment_loading, null, false);
    }

    private void initAdapter() {

        mResultsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mResultsRv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = SizeUtils.dip2px(getContext(), 1.5f);
                outRect.bottom = SizeUtils.dip2px(getContext(), 1.5f);
            }
        });
        mResultsAdapter = new SearchResultsAdapter();
        mResultsRv.setAdapter(mResultsAdapter);
    }

    private void initListener() {

        mResultsAdapter.setOnLoadMoreListener(() -> {
            if (mPresenter != null) {
                mPresenter.getLoaderMore(mKey);
            }
        });

        mResultsAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<SearchResult.DataBean.TbkDgMaterialOptionalResponseBean.ResultListBean.MapDataBean> data = adapter.getData();
            Intent intent = new Intent(getActivity(), TicketActivity.class);
            intent.putExtra(TicketActivity.TICKET_TITLE, data.get(position).getTitle());
            intent.putExtra(TicketActivity.TICKET_URL, data.get(position).getUrl());
            intent.putExtra(TicketActivity.TICKET_IMAGE, data.get(position).getPict_url());
            launchActivity(intent);
        });
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {
        mResultsAdapter.setNewData(null);
        mResultsAdapter.setEmptyView(mLoading);
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
    public void setSearch(List<SearchResult.DataBean.TbkDgMaterialOptionalResponseBean.ResultListBean.MapDataBean> searchData) {
        mResultsAdapter.setNewData(searchData);
    }

    @Override
    public void setLoaderMore(List<SearchResult.DataBean.TbkDgMaterialOptionalResponseBean.ResultListBean.MapDataBean> searchData) {
        mResultsAdapter.addData(searchData);
        mResultsAdapter.loadMoreComplete();
    }

    @Override
    public void onError() {
        mResultsAdapter.loadMoreFail();
    }

    @Override
    public void onEmpty() {
        mResultsAdapter.setEmptyView(mEmpty);
    }

    @Override
    public void onLoaderMoreEmpty() {
        mResultsAdapter.loadMoreEnd();
    }
}
