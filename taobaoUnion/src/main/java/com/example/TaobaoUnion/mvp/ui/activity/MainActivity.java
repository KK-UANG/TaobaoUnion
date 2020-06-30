package com.example.TaobaoUnion.mvp.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.TaobaoUnion.app.base.MySupportActivity;
import com.example.TaobaoUnion.mvp.ui.fragment.HomeFragment;
import com.example.TaobaoUnion.mvp.ui.fragment.SelectedFragment;
import com.example.TaobaoUnion.mvp.ui.fragment.PreferentialFragment;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.TaobaoUnion.di.component.DaggerMainComponent;
import com.example.TaobaoUnion.mvp.contract.MainContract;
import com.example.TaobaoUnion.mvp.presenter.MainPresenter;

import com.example.TaobaoUnion.R;
import com.tbruyelle.rxpermissions2.RxPermissions;


import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/02/2020 14:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MainActivity extends MySupportActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.bottomNav)
    BottomNavigationViewEx mBottomNavigationView;
    private SupportFragment[] mFragments = new SupportFragment[3];
    private double firstTime = 0;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        requestPermissions();
        // 初始化fragment
        initFragment();
        // 底部切换
        initBottomNav();
    }

    @SuppressLint("CheckResult")
    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(MainActivity.this);
        rxPermission
                .requestEach(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                )
                .subscribe(permission -> {
                    if (permission.granted) {
                        // 用户已经同意该权限
                        Timber.e("%s is granted.", permission.name);
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                        Timber.d("%s is denied. More info should be provided.", permission.name);
                    } else {
                        // 用户拒绝了该权限，并且选中『不再询问』
                        Timber.e("%s is denied.", permission.name);
                    }
                });
    }


    private void initFragment() {
        SupportFragment firstFragment = findFragment(HomeFragment.class);
        if (firstFragment == null) {
            mFragments[0] = HomeFragment.newInstance();
            mFragments[1] = SelectedFragment.newInstance();
            mFragments[2] = PreferentialFragment.newInstance();
            loadMultipleRootFragment(R.id.frame_content, 0,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2]
            );
        } else {
            mFragments[0] = firstFragment;
            mFragments[1] = findFragment(SelectedFragment.class);
            mFragments[2] = findFragment(PreferentialFragment.class);
        }
    }

    private void initBottomNav() {
        mBottomNavigationView.setCurrentItem(0);
        mBottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.home:
                    showHideFragment(mFragments[0]);
                    break;
                case R.id.selected:
                    showHideFragment(mFragments[1]);
                    break;
                case R.id.preferential:
                    showHideFragment(mFragments[2]);
                    break;
                default:
                    break;
            }
            return true;
        });

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
        finish();
    }

    @Override
    public void onBackPressedSupport() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            ArmsUtils.snackbarText("再按一次退出程序");
            firstTime = secondTime;
        } else {
            ArmsUtils.exitApp();
        }
    }
}
