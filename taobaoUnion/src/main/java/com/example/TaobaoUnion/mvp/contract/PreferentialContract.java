package com.example.TaobaoUnion.mvp.contract;

import com.example.TaobaoUnion.app.data.entity.Preferential;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import io.reactivex.Observable;


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
public interface PreferentialContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void setPreferential(List<Preferential.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> preferential);

        void setLoaderMore(List<Preferential.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> preferential);

        void onError();

        void onEmpty();

        void onLoaderMoreEmpty();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<Preferential> getPreferential(int page);
    }
}
