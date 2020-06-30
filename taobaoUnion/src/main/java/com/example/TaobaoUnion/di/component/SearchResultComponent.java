package com.example.TaobaoUnion.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.TaobaoUnion.di.module.SearchResultModule;
import com.example.TaobaoUnion.mvp.contract.SearchResultContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.TaobaoUnion.mvp.ui.fragment.SearchResultFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/24/2020 21:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = SearchResultModule.class, dependencies = AppComponent.class)
public interface SearchResultComponent {
    void inject(SearchResultFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SearchResultComponent.Builder view(SearchResultContract.View view);

        SearchResultComponent.Builder appComponent(AppComponent appComponent);

        SearchResultComponent build();
    }
}