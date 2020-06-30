package com.example.TaobaoUnion.mvp.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.TaobaoUnion.di.component.DaggerTicketComponent;
import com.example.TaobaoUnion.app.data.api.cache.UrlUtils;
import com.example.TaobaoUnion.app.data.entity.TicketResult;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;

import com.example.TaobaoUnion.mvp.contract.TicketContract;
import com.example.TaobaoUnion.mvp.presenter.TicketPresenter;

import com.example.TaobaoUnion.R;


import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


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
public class TicketActivity extends BaseActivity<TicketPresenter> implements TicketContract.View {

    public static final String TICKET_TITLE = "ticket_title";
    public static final String TICKET_URL = "ticket_url";
    public static final String TICKET_IMAGE = "ticket_image";
    @BindView(R.id.ticket_cover)
    ImageView mCover;
    @BindView(R.id.ticket_code)
    EditText mTicketCode;
    @BindView(R.id.ticket_copy_or_open_btn)
    TextView mOpenOrCopyBtn;

    @OnClick(R.id.ticket_back_press)
    public void retry() {
        finish();
    }

    private String mUrl;
    private String mTitle;
    private String mImage;
    private boolean mHasTabaoApp = false;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTicketComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_ticket; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        getData();
        setTicket();
        initListener();
    }



    private void getData() {
        if (getIntent() != null) {
            mTitle = getIntent().getStringExtra(TICKET_TITLE);
            mUrl = getIntent().getStringExtra(TICKET_URL);
            mImage = getIntent().getStringExtra(TICKET_IMAGE);
        }
        if (mPresenter != null) {
            mPresenter.getTicket(mUrl, mTitle);
        }
    }

    private void setTicket() {
        if (mImage != null) {
            String imageUrl = UrlUtils.getCoverPath(mImage);
            ArmsUtils.obtainAppComponentFromContext(this)
                    .imageLoader()
                    .loadImage(this, ImageConfigImpl
                            .builder()
                            .imageView(mCover)
                            .url(imageUrl)
                            .build());
        }
    }

    private void initListener() {
        //判断是否有安装淘宝
        // act=android.intent.action.MAIN
        // cat=[android.intent.category.LAUNCHER]
        // flg=0x10200000
        // cmp=com.taobao.taobao/com.taobao.tao.welcome.Welcome
        //包名是这个：com.taobao.taobao
        // 检查是否有安装淘宝应用
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo("com.taobao.taobao", PackageManager.MATCH_UNINSTALLED_PACKAGES);
            mHasTabaoApp = packageInfo != null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            mHasTabaoApp = false;
        }
        mOpenOrCopyBtn.setText(mHasTabaoApp ? "打开淘宝领券" : "复制淘口令");
        mOpenOrCopyBtn.setOnClickListener(v -> {
            //复制淘口令
            //拿到内容
            String ticketCode = mTicketCode.getText().toString().trim();
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            //复制到粘贴板
            ClipData clipData = ClipData.newPlainText("sob_taobao_ticket_code", ticketCode);
            cm.setPrimaryClip(clipData);
            //判断有没有淘宝
            if (mHasTabaoApp) {
                //如果有就打开淘宝
                Intent taobaoIntent = new Intent();
                //taobaoIntent.setAction("android.intent.action.MAIN");
                //taobaoIntent.addCategory("android.intent.category.LAUNCHER");
                //com.taobao.taobao/com.taobao.tao.TBMainActivity
                ComponentName componentName = new ComponentName("com.taobao.taobao", "com.taobao.tao.TBMainActivity");
                taobaoIntent.setComponent(componentName);
                startActivity(taobaoIntent);
            } else {
                //没有提示复制成功
                Toast.makeText(getApplicationContext(), "已经复制,粘贴分享,或打开淘宝", Toast.LENGTH_SHORT).show();
            }
        });
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

    @Override
    public void setTicket(TicketResult.DataBeanX.TbkTpwdCreateResponseBean.DataBean data) {
        mTicketCode.setText(data.getModel());
    }

    @Override
    public void onEmpty() {
        mTicketCode.setText("没有数据");
        mOpenOrCopyBtn.setVisibility(View.GONE);
    }
}
