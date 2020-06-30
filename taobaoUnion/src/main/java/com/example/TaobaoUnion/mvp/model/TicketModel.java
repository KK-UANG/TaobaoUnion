package com.example.TaobaoUnion.mvp.model;

import android.app.Application;
import android.util.Log;

import com.example.TaobaoUnion.app.data.api.cache.AppApi;
import com.example.TaobaoUnion.app.data.api.cache.UrlUtils;
import com.example.TaobaoUnion.app.data.entity.TicketParams;
import com.example.TaobaoUnion.app.data.entity.TicketResult;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.example.TaobaoUnion.mvp.contract.TicketContract;

import io.reactivex.Observable;


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
public class TicketModel extends BaseModel implements TicketContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public TicketModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<TicketResult> getTicket(String url, String title) {
        String ticketUrl = UrlUtils.getTicketUrl(url);
        TicketParams ticketParams = new TicketParams(ticketUrl, title);
        Log.d("请求",ticketUrl);
        return mRepositoryManager.obtainRetrofitService(AppApi.class)
                .getTicket(ticketParams);
    }
}