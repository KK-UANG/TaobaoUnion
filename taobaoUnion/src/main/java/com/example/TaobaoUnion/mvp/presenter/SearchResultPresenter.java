package com.example.TaobaoUnion.mvp.presenter;

import android.app.Application;

import com.example.TaobaoUnion.app.data.entity.SearchResult;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.example.TaobaoUnion.mvp.contract.SearchResultContract;
import com.jess.arms.utils.RxLifecycleUtils;


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
@FragmentScope
public class SearchResultPresenter extends BasePresenter<SearchResultContract.Model, SearchResultContract.View> {
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
    public SearchResultPresenter(SearchResultContract.Model model, SearchResultContract.View rootView) {
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

        public void getSearchResults(String keyword) {
        mPage = PAGE;
        mModel.getSearchResults(PAGE, keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<SearchResult>(mErrorHandler) {
                    @Override
                    public void onNext(SearchResult search) {
                        if (search == null
                                || search.getData() == null
                                || search.getData().getTbk_dg_material_optional_response() == null
                                || search.getData().getTbk_dg_material_optional_response().getResult_list() == null
                                || search.getData().getTbk_dg_material_optional_response().getResult_list().getMap_data() == null) {
                            mRootView.onEmpty();
                        } else {
                            mRootView.setSearch(search
                                    .getData()
                                    .getTbk_dg_material_optional_response()
                                    .getResult_list()
                                    .getMap_data());
                        }
                    }
                });
    }

    public void getLoaderMore(String keyword) {
        mModel.getSearchResults(mPage + 1, keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<SearchResult>(mErrorHandler) {
                    @Override
                    public void onNext(SearchResult search) {
                        if (search == null
                                || search.getData() == null
                                || search.getData().getTbk_dg_material_optional_response() == null
                                || search.getData().getTbk_dg_material_optional_response().getResult_list() == null
                                || search.getData().getTbk_dg_material_optional_response().getResult_list().getMap_data() == null) {
                            mRootView.onLoaderMoreEmpty();
                        } else {
                            mRootView.setLoaderMore(search
                                    .getData()
                                    .getTbk_dg_material_optional_response()
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
