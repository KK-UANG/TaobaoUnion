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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.TaobaoUnion.app.base.MySupportFragment;
import com.example.TaobaoUnion.app.data.entity.FeaturedCategories;
import com.example.TaobaoUnion.app.data.entity.FeaturedContent;
import com.example.TaobaoUnion.mvp.ui.activity.TicketActivity;
import com.example.TaobaoUnion.mvp.ui.adapter.CategoriesAdapter;
import com.example.TaobaoUnion.mvp.ui.adapter.FeaturedContentAdapter;
import com.example.TaobaoUnion.mvp.ui.utils.SizeUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.TaobaoUnion.di.component.DaggerSelectedComponent;
import com.example.TaobaoUnion.mvp.contract.SelectedContract;
import com.example.TaobaoUnion.mvp.presenter.SelectedPresenter;

import com.example.TaobaoUnion.R;

import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/02/2020 16:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SelectedFragment extends MySupportFragment<SelectedPresenter> implements SelectedContract.View {

    @BindView(R.id.categories_rv)
    RecyclerView mCategoriesRv;
    @BindView(R.id.content_rv)
    RecyclerView mContentRv;
    private CategoriesAdapter mCategoriesAdapter;
    private FeaturedContentAdapter mContentAdapter;
    private View mEmpty;


    public static SelectedFragment newInstance() {
        SelectedFragment fragment = new SelectedFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSelectedComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_selected, container, false);
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
        mEmpty = getLayoutInflater().inflate(R.layout.fragment_empty, null, false);
    }

    private void initAdapter() {
        mCategoriesRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mCategoriesAdapter = new CategoriesAdapter();
        mCategoriesRv.setAdapter(mCategoriesAdapter);

        mContentRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mContentRv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int topAndBottom = SizeUtils.dip2px(getContext(), 4);
                int leftAndRight = SizeUtils.dip2px(getContext(), 6);
                outRect.left = leftAndRight;
                outRect.right = leftAndRight;
                outRect.top = topAndBottom;
                outRect.bottom = topAndBottom;
            }
        });
        mContentAdapter = new FeaturedContentAdapter();
        mContentRv.setAdapter(mContentAdapter);
        mContentAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
    }

    private void getData() {
        if (mPresenter != null) {
            mPresenter.getFeaturedCategories();
        }
    }

    private void initListener() {
        mCategoriesAdapter.setOnItemClickListener((adapter, view, position) -> {
            mCategoriesAdapter.idx = position;
            mCategoriesAdapter.notifyDataSetChanged();
            List<FeaturedCategories.DataBean> data = mCategoriesAdapter.getData();
            if (mPresenter != null) {
                mPresenter.getFeaturedContent(data.get(position).getFavorites_id());
            }
        });

        mContentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                    if (view.getId() == R.id.selected_buy_btn) {
                        setonItemClick(position, adapter.getData());
                    }
                }
        );
    }

    private void setonItemClick(int position, List<FeaturedContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean> ticket) {
        Intent intent = new Intent(getActivity(), TicketActivity.class);
        intent.putExtra(TicketActivity.TICKET_TITLE, ticket.get(position).getTitle());
        String coupon_click_url = ticket.get(position).getCoupon_click_url();
        String click_url = ticket.get(position).getClick_url();
        intent.putExtra(TicketActivity.TICKET_URL, coupon_click_url == null ? click_url : coupon_click_url);
        intent.putExtra(TicketActivity.TICKET_IMAGE, ticket.get(position).getPict_url());
        launchActivity(intent);
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
    public void setFeaturedCategories(List<FeaturedCategories.DataBean> classification) {
        mCategoriesAdapter.setNewData(classification);
        int id = classification.get(0).getFavorites_id();
        if (mPresenter != null) {
            mPresenter.getFeaturedContent(id);
        }
    }

    @Override
    public void setFeaturedContent(List<FeaturedContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean> content) {
        mContentAdapter.setNewData(content);
        mContentRv.scrollToPosition(0);
    }

    @Override
    public void onCategoriesEmpty() {
        mCategoriesAdapter.setEmptyView(mEmpty);
        mContentAdapter.setEmptyView(mEmpty);
    }

    @Override
    public void onContentEmpty() {
        mContentAdapter.setEmptyView(mEmpty);
    }
}
