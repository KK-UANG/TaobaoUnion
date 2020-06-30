package com.example.TaobaoUnion.di.module;

import dagger.Binds;
import dagger.Module;

import com.example.TaobaoUnion.mvp.contract.SearchHistoryContract;
import com.example.TaobaoUnion.mvp.model.SearchHistoryModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/24/2020 20:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class SearchHistoryModule {

    @Binds
    abstract SearchHistoryContract.Model bindSearchHistoryModel(SearchHistoryModel model);
}