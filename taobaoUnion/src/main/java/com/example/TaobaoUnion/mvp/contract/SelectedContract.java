package com.example.TaobaoUnion.mvp.contract;

import com.example.TaobaoUnion.app.data.entity.FeaturedCategories;
import com.example.TaobaoUnion.app.data.entity.FeaturedContent;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import io.reactivex.Observable;


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
public interface SelectedContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void setFeaturedCategories(List<FeaturedCategories.DataBean> categories);

        void setFeaturedContent(List<FeaturedContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean> content);

        void onCategoriesEmpty();

        void onContentEmpty();
    }


    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<FeaturedCategories> getFeaturedCategories();

        Observable<FeaturedContent> getFeaturedContent(int id);
    }
}
