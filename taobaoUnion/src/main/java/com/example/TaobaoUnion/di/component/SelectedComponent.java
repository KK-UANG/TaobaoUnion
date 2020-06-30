package com.example.TaobaoUnion.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.example.TaobaoUnion.mvp.ui.fragment.SelectedFragment;
import com.jess.arms.di.component.AppComponent;

import com.example.TaobaoUnion.di.module.SelectedModule;
import com.example.TaobaoUnion.mvp.contract.SelectedContract;

import com.jess.arms.di.scope.FragmentScope;


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
@Component(modules = SelectedModule.class, dependencies = AppComponent.class)
public interface SelectedComponent {
    void inject(SelectedFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SelectedComponent.Builder view(SelectedContract.View view);

        SelectedComponent.Builder appComponent(AppComponent appComponent);

        SelectedComponent build();
    }
}