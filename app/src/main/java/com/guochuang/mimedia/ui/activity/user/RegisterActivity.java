package com.guochuang.mimedia.ui.activity.user;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.guochuang.mimedia.mvp.model.UserLogin;
import com.guochuang.mimedia.mvp.presenter.LoginPresenter;
import com.guochuang.mimedia.mvp.view.LoginView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.GsonUtil;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.PrefUtil;
import com.guochuang.mimedia.tools.SystemUtil;
import com.guochuang.mimedia.ui.adapter.MyFragmentPagerAdapter;
import com.guochuang.mimedia.ui.fragment.RegistEmailFragment;
import com.guochuang.mimedia.ui.fragment.RegistMobileFragment;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.tools.Constant;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

public class RegisterActivity extends MvpActivity<LoginPresenter> implements LoginView {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tb_list)
    TabLayout tbList;
    @BindView(R.id.vp_content)
    ViewPager vpContent;


    Fragment[] fragments = new Fragment[2];

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(getString(R.string.title_login_register));
        fragments[0] = new RegistMobileFragment();
        fragments[1] = new RegistEmailFragment();
        String[] titles=new String[]{getString(R.string.mobile),getString(R.string.email)};
        MyFragmentPagerAdapter redbagDynamicAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles);
        vpContent.setAdapter(redbagDynamicAdapter);
        tbList.setupWithViewPager(vpContent);
        tbList.setTabIndicatorFullWidth(false);
    }

    public void login(String userName,String password){
        showLoadingDialog(null);
        mvpPresenter.getLogin(
                userName,
                password,
                Constant.CAPTCHA,
                Constant.SYSTEM_CODE,
                Constant.LOGIN_TYPE,
                SystemUtil.getAPNType(),
                SystemUtil.getDeviceId(),
                SystemUtil.getAndroidId(),
                SystemUtil.getSystemModel(),
                SystemUtil.getSystemModel(),
                SystemUtil.getDeviceBrand(),
                SystemUtil.getSystemVersion(),
                SystemUtil.getAppVersion(),
                SystemUtil.getDeviceResolution(),
                SystemUtil.getDeviceId(),
                JPushInterface.getRegistrationID(RegisterActivity.this)
        );
    }
    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

        }
    }

    @Override
    public void setLoginData(String data) {
        closeLoadingDialog();
        UserLogin userLogin = GsonUtil.GsonToBean(CommonUtil.baseDecrypt(data.split("\\.")[1]), UserLogin.class);
        getPref().setString(PrefUtil.USER_TOKEN, data);
        getPref().setString(PrefUtil.MOBILE, userLogin.getMobile());
        getPref().setString(PrefUtil.EMAIL, userLogin.getEmail());
        IntentUtils.startMainActivity(this, true);
        finish();
    }

    @Override
    public void setWxLoginData(String data) {

    }

    @Override
    public void setLoginError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
        finish();
    }

    @Override
    public void goToAplayWeixin() {

    }
}
