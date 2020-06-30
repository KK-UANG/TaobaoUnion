package com.example.TaobaoUnion.mvp.model;

import android.app.Application;

import com.example.TaobaoUnion.app.data.api.cache.AppApi;
import com.example.TaobaoUnion.app.data.api.cache.UrlUtils;
import com.example.TaobaoUnion.app.data.entity.FeaturedCategories;
import com.example.TaobaoUnion.app.data.entity.FeaturedContent;
import com.example.TaobaoUnion.mvp.contract.SelectedContract;
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
 * Created by MVPArmsTemplate on 05/02/2020 16:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class SelectedModel extends BaseModel implements SelectedContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SelectedModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<FeaturedCategories> getFeaturedCategories() {
        return mRepositoryManager.obtainRetrofitService(AppApi.class)
                .getFeaturedCategories();
    }

    @Override
    public Observable<FeaturedContent> getFeaturedContent(int id) {
        String url = UrlUtils.getFeaturedContent(id);
        return mRepositoryManager.obtainRetrofitService(AppApi.class)
                .getFeaturedContent(url);
    }
}