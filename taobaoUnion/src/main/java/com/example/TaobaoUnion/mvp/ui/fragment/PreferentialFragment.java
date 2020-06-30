package com.example.TaobaoUnion.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.TaobaoUnion.app.base.MySupportFragment;
import com.example.TaobaoUnion.app.data.entity.Preferential;
import com.example.TaobaoUnion.mvp.ui.activity.TicketActivity;
import com.example.TaobaoUnion.mvp.ui.adapter.PreferentialAdapter;
import com.example.TaobaoUnion.mvp.ui.utils.SizeUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.TaobaoUnion.di.component.DaggerPreferentialComponent;
import com.example.TaobaoUnion.mvp.contract.PreferentialContract;
import com.example.TaobaoUnion.mvp.presenter.PreferentialPresenter;

import com.example.TaobaoUnion.R;

import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/02/2020 16:03
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class PreferentialFragment extends MySupportFragment<PreferentialPresenter> implements PreferentialContract.View {

    @BindView(R.id.preferential_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.preferential_sw)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private PreferentialAdapter mAdapter;
    private View mEmpty;

    public static PreferentialFragment newInstance() {
        PreferentialFragment fragment = new PreferentialFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerPreferentialComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_preferential, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initVew();
        initAdapter();
        initListener();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getData();
    }

    private void initVew() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mEmpty = getLayoutInflater().inflate(R.layout.fragment_empty, null, false);
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = SizeUtils.dip2px(getContext(), 2.5f);
                outRect.bottom = SizeUtils.dip2px(getContext(), 2.5f);
                outRect.left = SizeUtils.dip2px(getContext(), 2.5f);
                outRect.right = SizeUtils.dip2px(getContext(), 2.5f);
            }
        });
        mAdapter = new PreferentialAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
    }

    private void getData() {
        if (mPresenter != null) {
            mPresenter.getPreferential();
        }
    }

    private void initListener() {

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            if (mPresenter != null) {
                mPresenter.getPreferential();
            }
        });

        mAdapter.setOnItemClickListener((adapter, view, position) ->
                setonItemClick(position, adapter.getData()));

        mAdapter.setOnLoadMoreListener(() -> {
            if (mPresenter != null) {
                mPresenter.getLoaderMore();
            }
        });
    }

    private void setonItemClick(int position, List<Preferential.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> data) {
        Intent intent = new Intent(getActivity(), TicketActivity.class);
        intent.putExtra(TicketActivity.TICKET_TITLE, data.get(position).getTitle());
        String coupon_click_url = data.get(position).getCoupon_click_url();
        String click_url = data.get(position).getClick_url();
        intent.putExtra(TicketActivity.TICKET_URL, coupon_click_url == null ? click_url : coupon_click_url);
        intent.putExtra(TicketActivity.TICKET_IMAGE, data.get(position).getPict_url());
        launchActivity(intent);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideLoading() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
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
    public void setPreferential(List<Preferential.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> preferential) {
        mAdapter.setNewData(preferential);
    }

    @Override
    public void setLoaderMore(List<Preferential.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> preferential) {
        mAdapter.addData(preferential);
        mAdapter.loadMoreComplete();
    }

    @Override
    public void onError() {
        mAdapter.loadMoreFail();
    }

    @Override
    public void onEmpty() {
        mAdapter.setEmptyView(mEmpty);
    }

    @Override
    public void onLoaderMoreEmpty() {
        mAdapter.loadMoreEnd();
    }
}
