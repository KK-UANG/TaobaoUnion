package com.example.TaobaoUnion.mvp.presenter;

import android.app.Application;

import com.example.TaobaoUnion.app.data.entity.TicketResult;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.example.TaobaoUnion.mvp.contract.TicketContract;
import com.jess.arms.utils.RxLifecycleUtils;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/05/2020 20:15
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class TicketPresenter extends BasePresenter<TicketContract.Model, TicketContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public TicketPresenter(TicketContract.Model model, TicketContract.View rootView) {
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

    public void getTicket(String url, String title) {
        mModel.getTicket(url, title)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<TicketResult>(mErrorHandler) {
                    @Override
                    public void onNext(TicketResult data) {
                        if (data == null
                                || data.getData() == null
                                || data.getData().getTbk_tpwd_create_response() == null
                                || data.getData().getTbk_tpwd_create_response().getData() == null) {
                            mRootView.onEmpty();
                        }
                        mRootView.setTicket(data
                                .getData()
                                .getTbk_tpwd_create_response()
                                .getData());
                    }
                });
    }
}
