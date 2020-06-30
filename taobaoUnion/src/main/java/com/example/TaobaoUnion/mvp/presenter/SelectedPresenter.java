package com.example.TaobaoUnion.mvp.presenter;

import android.app.Application;

import com.example.TaobaoUnion.app.data.entity.FeaturedCategories;
import com.example.TaobaoUnion.app.data.entity.FeaturedContent;
import com.example.TaobaoUnion.mvp.contract.SelectedContract;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.RxLifecycleUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;


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
@FragmentScope
public class SelectedPresenter extends BasePresenter<SelectedContract.Model, SelectedContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public SelectedPresenter(SelectedContract.Model model, SelectedContract.View rootView) {
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

    public void getFeaturedCategories() {
        mModel.getFeaturedCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<FeaturedCategories>(mErrorHandler) {
                    @Override
                    public void onNext(FeaturedCategories data) {
                        if (data == null || data.getData() == null) {
                            mRootView.onCategoriesEmpty();
                        } else {
                            mRootView.setFeaturedCategories(data.getData());
                        }
                    }
                });
    }

    public void getFeaturedContent(int id) {
        mModel.getFeaturedContent(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<FeaturedContent>(mErrorHandler) {
                    @Override
                    public void onNext(FeaturedContent data) {
                        if (data == null
                                || data.getData() == null
                                || data.getData().getTbk_uatm_favorites_item_get_response() == null
                                || data.getData().getTbk_uatm_favorites_item_get_response().getResults() == null
                                || data.getData().getTbk_uatm_favorites_item_get_response().getResults().getUatm_tbk_item() == null) {
                            mRootView.onContentEmpty();
                        } else {
                            mRootView.setFeaturedContent(data
                                    .getData()
                                    .getTbk_uatm_favorites_item_get_response()
                                    .getResults()
                                    .getUatm_tbk_item());
                        }
                    }
                });
    }
}
