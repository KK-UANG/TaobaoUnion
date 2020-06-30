package com.example.TaobaoUnion.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.TaobaoUnion.di.module.HomePagerModule;
import com.example.TaobaoUnion.mvp.contract.HomePagerContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.TaobaoUnion.mvp.ui.fragment.HomePagerFragment;


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
@FragmentScope
@Component(modules = HomePagerModule.class, dependencies = AppComponent.class)
public interface HomePagerComponent {
    void inject(HomePagerFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HomePagerComponent.Builder view(HomePagerContract.View view);

        HomePagerComponent.Builder appComponent(AppComponent appComponent);

        HomePagerComponent build();
    }
}