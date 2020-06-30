package com.example.TaobaoUnion.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.TaobaoUnion.app.base.MySupportFragment;
import com.example.TaobaoUnion.app.data.entity.Categories;
import com.example.TaobaoUnion.mvp.presenter.HomePresenter;
import com.example.TaobaoUnion.mvp.ui.activity.SearchActivity;
import com.example.TaobaoUnion.mvp.ui.activity.TicketActivity;
import com.example.TaobaoUnion.mvp.ui.adapter.HomePagerAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.TaobaoUnion.di.component.DaggerHomeComponent;
import com.example.TaobaoUnion.mvp.contract.HomeContract;

import com.example.TaobaoUnion.R;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;


import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/02/2020 15:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HomeFragment extends MySupportFragment<HomePresenter> implements HomeContract.View {

    @BindView(R.id.tab_home)
    TabLayout mTabLayout;
    @BindView(R.id.vp_home)
    ViewPager mViewPager;
    private int REQUEST_CODE_SCAN = 1000;

    @OnClick(R.id.scan_icon)
    public void retry() {
        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    @OnClick(R.id.home_search_input_box)
    public void search() {
        Objects.requireNonNull(getActivity()).startActivity(new Intent(getActivity(), SearchActivity.class));
    }

    private HomePagerAdapter mAdapter;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomeComponent  //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                if (content.contains("https://uland.taobao.com")) {
                    Intent intent = new Intent(getActivity(), TicketActivity.class);
                    if (content.indexOf("https:https://uland.taobao.com") != 1) {
                        intent.putExtra(TicketActivity.TICKET_URL, content.substring(6));
                    } else {
                        intent.putExtra(TicketActivity.TICKET_URL, content);
                    }
                    intent.putExtra(TicketActivity.TICKET_TITLE, "");
                    launchActivity(intent);
                } else {
                    Toast.makeText(getContext(), "扫描结果" + content, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initAdapter();
        if (mPresenter != null) {
            mPresenter.initTab();
        }
    }

    private void initAdapter() {
        mTabLayout.setupWithViewPager(mViewPager);
        mAdapter = new HomePagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);
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
    public void setCategories(List<Categories.DataBean> title) {
        mAdapter.setCategories(title);
    }

    @Override
    public void onEmpty() {

    }

}
