package com.example.TaobaoUnion.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.TaobaoUnion.di.module.TicketModule;
import com.example.TaobaoUnion.mvp.contract.TicketContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.TaobaoUnion.mvp.ui.activity.TicketActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/05/2020 20:15
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = TicketModule.class, dependencies = AppComponent.class)
public interface TicketComponent {
    void inject(TicketActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TicketComponent.Builder view(TicketContract.View view);

        TicketComponent.Builder appComponent(AppComponent appComponent);

        TicketComponent build();
    }
}