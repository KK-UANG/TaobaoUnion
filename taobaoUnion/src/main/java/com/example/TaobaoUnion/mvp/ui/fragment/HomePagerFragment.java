package com.example.TaobaoUnion.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.TaobaoUnion.app.base.MySupportFragment;
import com.example.TaobaoUnion.app.data.entity.Categories;
import com.example.TaobaoUnion.app.data.entity.HomeProducts;
import com.example.TaobaoUnion.mvp.ui.activity.TicketActivity;
import com.example.TaobaoUnion.mvp.ui.adapter.BannerViewAdapter;
import com.example.TaobaoUnion.mvp.ui.adapter.HomeProductsAdapter;
import com.example.TaobaoUnion.mvp.ui.utils.SizeUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.TaobaoUnion.di.component.DaggerHomePagerComponent;
import com.example.TaobaoUnion.mvp.contract.HomePagerContract;
import com.example.TaobaoUnion.mvp.presenter.HomePagerPresenter;

import com.example.TaobaoUnion.R;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/02/2020 17:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HomePagerFragment extends MySupportFragment<HomePagerPresenter> implements HomePagerContract.View {

    @BindView(R.id.rv_home_pager)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private Integer mTagId;
    private HomeProductsAdapter mAdapter;
    private View mBannerView;
    private MZBannerView mBanner;
    private View mEmpty;
    private Integer mHomePagerFragment;
    private View mView;

    public static HomePagerFragment newInstance(Categories.DataBean category) {
        HomePagerFragment fragment = new HomePagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key_home_pager_title", category.getTitle());
        bundle.putInt("key_home_pager_material_id", category.getId());
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomePagerComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_home_pager, container, false);
        }
        return mView;
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
        if (getArguments() != null) {
            mTagId = getArguments().getInt("key_home_pager_material_id");
            mHomePagerFragment = getArguments().getInt("1111");
            if (mTagId != null && mPresenter != null) {
                mPresenter.getHomeProducts(mTagId);
            }
        }
    }


    private void initVew() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        mBannerView = getLayoutInflater().inflate(R.layout.frament_banner, null, false);
        mBanner = mBannerView.findViewById(R.id.banner);
        mEmpty = getLayoutInflater().inflate(R.layout.fragment_empty, null, false);
    }

    private void initAdapter() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = SizeUtils.dip2px(getContext(), 1.5f);
                outRect.bottom = SizeUtils.dip2px(getContext(), 1.5f);
            }
        });
        mAdapter = new HomeProductsAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.addHeaderView(mBannerView);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);

        mBanner.setIndicatorVisible(true);
        mBanner.setDelayedTime(3000);
        mBanner.setDuration(1500);

    }


    private void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            if (mPresenter != null) {
                mPresenter.getHomeProducts(mTagId);
            }
        });

        mAdapter.setOnLoadMoreListener(() -> {
            if (mPresenter != null) {
                mPresenter.getLoaderMore(mTagId);
            }
        });

        mAdapter.setOnItemClickListener((adapter, view, position) ->
                setonItemClick(position, adapter.getData()));
    }

    private void setonItemClick(int position, List<HomeProducts.DataBean> ticket) {
        Intent intent = new Intent(getActivity(), TicketActivity.class);
        intent.putExtra(TicketActivity.TICKET_TITLE, ticket.get(position).getTitle());
        String coupon_click_url = ticket.get(position).getCoupon_click_url();
        String click_url = ticket.get(position).getClick_url();
        intent.putExtra(TicketActivity.TICKET_URL, coupon_click_url == null ? click_url : coupon_click_url);
        intent.putExtra(TicketActivity.TICKET_IMAGE, ticket.get(position).getPict_url());
        launchActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        mBanner.start();//开始轮播
    }

    @Override
    public void onPause() {
        super.onPause();
        mBanner.pause();//暂停轮播
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
    public void setHomeProducts(List<HomeProducts.DataBean> products) {
        mAdapter.setNewData(products);
    }

    @Override
    public void setBanner(List<HomeProducts.DataBean> banner) {
        List<HomeProducts.DataBean> bannerList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            bannerList.add(banner.get(i));
        }
        // 设置Banner点击事件
        mBanner.setBannerPageClickListener((view, position) ->
                setonItemClick(position, bannerList));
        // 设置Banner数据
        mBanner.setPages(bannerList, (MZHolderCreator<BannerViewAdapter>) () ->
                new BannerViewAdapter());
        mBanner.start();
    }

    @Override
    public void setLoaderMore(List<HomeProducts.DataBean> products) {
        mAdapter.addData(products);
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
