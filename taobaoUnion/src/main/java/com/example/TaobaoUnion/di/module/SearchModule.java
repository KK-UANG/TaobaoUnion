package com.example.TaobaoUnion.di.module;

import com.example.TaobaoUnion.mvp.contract.SearchContract;

import dagger.Binds;
import dagger.Module;

import com.example.TaobaoUnion.mvp.model.SearchModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/10/2020 12:27
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class SearchModule {

    @Binds
    abstract SearchContract.Model bindSearchModel(SearchModel model);
}