package com.example.TaobaoUnion.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.TaobaoUnion.di.module.SearchHistoryModule;
import com.example.TaobaoUnion.mvp.contract.SearchHistoryContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.TaobaoUnion.mvp.ui.fragment.SearchHistoryFragment;


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
@FragmentScope
@Component(modules = SearchHistoryModule.class, dependencies = AppComponent.class)
public interface SearchHistoryComponent {
    void inject(SearchHistoryFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SearchHistoryComponent.Builder view(SearchHistoryContract.View view);

        SearchHistoryComponent.Builder appComponent(AppComponent appComponent);

        SearchHistoryComponent build();
    }
}