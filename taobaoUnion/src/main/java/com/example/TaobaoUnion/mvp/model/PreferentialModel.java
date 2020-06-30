package com.example.TaobaoUnion.mvp.model;

import android.app.Application;

import com.example.TaobaoUnion.app.data.api.cache.AppApi;
import com.example.TaobaoUnion.app.data.api.cache.UrlUtils;
import com.example.TaobaoUnion.app.data.entity.Preferential;
import com.example.TaobaoUnion.mvp.contract.PreferentialContract;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import io.reactivex.Observable;


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
public class PreferentialModel extends BaseModel implements PreferentialContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public PreferentialModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<Preferential> getPreferential(int page) {
        String url = UrlUtils.getPreferentialUrl(page);
        return mRepositoryManager.obtainRetrofitService(AppApi.class)
                .getPreferential(url);

    }
}