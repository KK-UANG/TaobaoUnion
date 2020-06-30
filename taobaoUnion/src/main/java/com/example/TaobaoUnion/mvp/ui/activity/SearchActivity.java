package com.example.TaobaoUnion.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.TaobaoUnion.app.base.MySupportActivity;
import com.example.TaobaoUnion.di.component.DaggerSearchComponent;
import com.example.TaobaoUnion.mvp.ui.fragment.SearchHistoryFragment;
import com.example.TaobaoUnion.mvp.ui.fragment.SearchResultFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.TaobaoUnion.mvp.contract.SearchContract;
import com.example.TaobaoUnion.mvp.presenter.SearchPresenter;

import com.example.TaobaoUnion.R;


import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

import static com.jess.arms.utils.Preconditions.checkNotNull;


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
public class SearchActivity extends MySupportActivity<SearchPresenter> implements SearchContract.View {

    @BindView(R.id.search_clean_btn)
    ImageView mCleanInputBtn;
    @BindView(R.id.search_input_box)
    EditText mSearchInputBox;

    @OnClick(R.id.search_btn)
    public void retry() {
        finish();
    }

    private SupportFragment[] mFragments = new SupportFragment[2];
    private SearchHistoryFragment mHistoryFragment;
    private SearchResultFragment mResultFragment;
    private InputMethodManager mImm;
    private boolean mIsResultPage = false;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSearchComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_search; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initFragment();
        initView();
        initListener();
    }

    private void initFragment() {
        SupportFragment firstFragment = findFragment(SearchHistoryFragment.class);
        if (firstFragment == null) {
            mFragments[0] = SearchHistoryFragment.newInstance();
            mFragments[1] = SearchResultFragment.newInstance();
            loadMultipleRootFragment(R.id.frame_content, 0,
                    mFragments[0],
                    mFragments[1]

            );
        } else {
            mFragments[0] = firstFragment;
            mFragments[1] = findFragment(SearchResultFragment.class);
        }
        mHistoryFragment = (SearchHistoryFragment) mFragments[0];
        mResultFragment = (SearchResultFragment) mFragments[1];
    }

    private void initView() {
        getWindow().getDecorView().postDelayed(() -> {
            mImm = (InputMethodManager) getSystemService(SearchActivity.INPUT_METHOD_SERVICE);
            mSearchInputBox.requestFocus();
            mImm.showSoftInput(mSearchInputBox, 0);
        }, 100);
    }

    private void initListener() {

        mSearchInputBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mCleanInputBtn.setVisibility(charSequence.length() > 0 ? View.VISIBLE : View.GONE);
                if (charSequence.length() <= 0) {
                    showHideFragment(mFragments[0]);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mCleanInputBtn.setOnClickListener(view -> {
            mSearchInputBox.setText(null);
            showHideFragment(mFragments[0]);
            mImm.showSoftInput(mSearchInputBox, 0);
        });

        mSearchInputBox.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                String key = textView.getText().toString().trim();
                Search(key);

            }
            return false;
        });

    }

    public void Search(String key) {
        if (!isNullorEmpty(key)) {

            mSearchInputBox.setText(key);
            mSearchInputBox.setFocusable(true);
            mSearchInputBox.requestFocus();
            mSearchInputBox.setSelection(key.length(), key.length());

            mResultFragment.search(key);
            mHistoryFragment.addHistory(key);
            showHideFragment(mFragments[1]);

        } else {
            Toast.makeText(getApplicationContext(), "请输入搜索内容", Toast.LENGTH_SHORT).show();
        }
        mImm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    private boolean isNullorEmpty(String key) {
        return key == null || "".equals(key);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


}
