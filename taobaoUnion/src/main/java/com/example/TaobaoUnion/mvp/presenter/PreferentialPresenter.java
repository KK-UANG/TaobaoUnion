package com.example.TaobaoUnion.mvp.presenter;

import android.app.Application;

import com.example.TaobaoUnion.app.data.entity.Preferential;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.example.TaobaoUnion.mvp.contract.PreferentialContract;
import com.jess.arms.utils.RxLifecycleUtils;


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
@FragmentScope
public class PreferentialPresenter extends BasePresenter<PreferentialContract.Model, PreferentialContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    public static final int PAGE = 1;
    private Integer mPage;

    @Inject
    public PreferentialPresenter(PreferentialContract.Model model, PreferentialContract.View rootView) {
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

    public void getPreferential() {
        mPage = PAGE;
        mModel.getPreferential(PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                })
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<Preferential>(mErrorHandler) {
                    @Override
                    public void onNext(Preferential preferential) {
                        if (preferential == null
                                || preferential.getData() == null
                                || preferential.getData().getTbk_dg_optimus_material_response() == null
                                || preferential.getData().getTbk_dg_optimus_material_response().getResult_list() == null
                                || preferential.getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data() == null) {
                            mRootView.onEmpty();
                        } else {
                            mRootView.setPreferential(preferential
                                    .getData()
                                    .getTbk_dg_optimus_material_response()
                                    .getResult_list()
                                    .getMap_data());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    public void getLoaderMore() {
        mModel.getPreferential(mPage + 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<Preferential>(mErrorHandler) {
                    @Override
                    public void onNext(Preferential preferential) {
                        if (preferential == null
                                || preferential.getData() == null
                                || preferential.getData().getTbk_dg_optimus_material_response() == null
                                || preferential.getData().getTbk_dg_optimus_material_response().getResult_list() == null
                                || preferential.getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data() == null) {
                            mRootView.onLoaderMoreEmpty();
                        } else {
                            mRootView.setLoaderMore(preferential
                                    .getData()
                                    .getTbk_dg_optimus_material_response()
                                    .getResult_list()
                                    .getMap_data());
                            mPage++;
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onError();
                    }
                });
    }
}
