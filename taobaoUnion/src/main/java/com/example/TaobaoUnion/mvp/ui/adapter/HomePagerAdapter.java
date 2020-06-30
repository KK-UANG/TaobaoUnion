package com.example.TaobaoUnion.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.TaobaoUnion.app.data.entity.Categories;
import com.example.TaobaoUnion.mvp.ui.fragment.HomePagerFragment;

import java.util.ArrayList;
import java.util.List;

public class HomePagerAdapter extends FragmentPagerAdapter {

    private List<Categories.DataBean> categoryList = new ArrayList<>();

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Categories.DataBean dataBean = categoryList.get(i);
        return HomePagerFragment.newInstance(dataBean);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return categoryList.get(position).getTitle();
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
    }

    public void setCategories(List<Categories.DataBean> title) {
        categoryList.clear();
        categoryList.addAll(title);
        notifyDataSetChanged();
    }
}
