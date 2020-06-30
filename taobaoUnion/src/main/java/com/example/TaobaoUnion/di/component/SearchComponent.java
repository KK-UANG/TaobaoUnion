package com.example.TaobaoUnion.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.example.TaobaoUnion.mvp.contract.SearchContract;
import com.example.TaobaoUnion.mvp.ui.activity.SearchActivity;
import com.jess.arms.di.component.AppComponent;

import com.example.TaobaoUnion.di.module.SearchModule;

import com.jess.arms.di.scope.ActivityScope;


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
@ActivityScope
@Component(modules = SearchModule.class, dependencies = AppComponent.class)
public interface SearchComponent {
    void inject(SearchActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SearchComponent.Builder view(SearchContract.View view);

        SearchComponent.Builder appComponent(AppComponent appComponent);

        SearchComponent build();
    }
}