package com.example.TaobaoUnion.mvp.presenter;

import android.app.Application;

import com.example.TaobaoUnion.R;
import com.example.TaobaoUnion.app.data.entity.HomeProducts;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.example.TaobaoUnion.mvp.contract.HomePagerContract;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.HashMap;
import java.util.Map;


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
@FragmentScope
public class HomePagerPresenter extends BasePresenter<HomePagerContract.Model, HomePagerContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    private Map<Integer, Integer> pagesInfo = new HashMap<>();
    public static final int PAGE = 1;
    private Integer mPage;

    @Inject
    public HomePagerPresenter(HomePagerContract.Model model, HomePagerContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getHomeProducts(int tagId) {
        mPage = PAGE;
        pagesInfo.put(tagId, PAGE);
        mModel.getHomeProducts(tagId, PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                })
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<HomeProducts>(mErrorHandler) {
                    @Override
                    public void onNext(HomeProducts homeProducts) {
                        if (homeProducts == null || homeProducts.getData() == null) {
                            mRootView.showMessage(mApplication.getString(R.string.empty));
                            mRootView.onEmpty();
                        } else {
                            mRootView.setHomeProducts(homeProducts.getData());
                            mRootView.setBanner(homeProducts.getData());
                        }
                    }
                });
    }

    public void getLoaderMore(int tagId) {
        mPage = pagesInfo.get(tagId);
        mModel.getHomeProducts(tagId, mPage + 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<HomeProducts>(mErrorHandler) {
                    @Override
                    public void onNext(HomeProducts homeProducts) {
                        if (homeProducts == null || homeProducts.getData() == null) {
                            mRootView.onLoaderMoreEmpty();
                        } else {
                            mRootView.setLoaderMore(homeProducts.getData());
                            pagesInfo.put(tagId, mPage + 1);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onError();
                        pagesInfo.put(tagId, mPage);
                    }
                });
    }
}
