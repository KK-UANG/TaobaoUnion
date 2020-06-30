package com.example.TaobaoUnion.di.module;

import dagger.Binds;
import dagger.Module;

import com.example.TaobaoUnion.mvp.contract.HomePagerContract;
import com.example.TaobaoUnion.mvp.model.HomePagerModel;


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
@Module
public abstract class HomePagerModule {

    @Binds
    abstract HomePagerContract.Model bindHomePagerModel(HomePagerModel model);
}