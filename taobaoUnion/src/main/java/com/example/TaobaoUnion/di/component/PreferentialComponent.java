package com.example.TaobaoUnion.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.TaobaoUnion.di.module.PreferentialModule;
import com.example.TaobaoUnion.mvp.contract.PreferentialContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.TaobaoUnion.mvp.ui.fragment.PreferentialFragment;


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
@Component(modules = PreferentialModule.class, dependencies = AppComponent.class)
public interface PreferentialComponent {
    void inject(PreferentialFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PreferentialComponent.Builder view(PreferentialContract.View view);

        PreferentialComponent.Builder appComponent(AppComponent appComponent);

        PreferentialComponent build();
    }
}