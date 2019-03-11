package com.guochuang.mimedia.ui.activity.user;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.widget.ListPopupWindow;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.PhoneEntity;
import com.guochuang.mimedia.mvp.model.UserLogin;
import com.guochuang.mimedia.mvp.presenter.LoginPresenter;
import com.guochuang.mimedia.mvp.view.LoginView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.GeneralUtil;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.PrefUtil;
import com.guochuang.mimedia.tools.SoftKeyBoardListener;
import com.guochuang.mimedia.tools.SystemUtil;
import com.guochuang.mimedia.tools.WxLogin;
import com.guochuang.mimedia.tools.antishake.AntiShake;
import com.guochuang.mimedia.ui.adapter.SelectPhoneAdapter;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import io.objectbox.Box;
import io.objectbox.BoxStore;

public class LoginActivity extends MvpActivity<LoginPresenter> implements LoginView {

    @BindView(R.id.re_root)
    RelativeLayout mReRoot;
    @BindView(R.id.iv_login_header)
    ImageView ivHeader;
    @BindView(R.id.et_login_phone)
    EditText etPhone;
    @BindView(R.id.et_login_password)
    EditText etPassword;
    @BindView(R.id.tv_login_confirm)
    TextView tvConfirm;
    @BindView(R.id.tv_login_register)
    TextView tvRegister;
    @BindView(R.id.tv_login_forget)
    TextView tvForget;
    @BindView(R.id.iv_login_wx)
    ImageView ivWx;
    @BindView(R.id.bottom_line)
    View mBottomLine;
    @BindView(R.id.iv_select_phone)
    View mIvSelectPhone;


    ListPopupWindow mListPopupWindow;
    private Box<PhoneEntity> mBox;
    SelectPhoneAdapter mSelectPhoneAdapter;
    List<PhoneEntity> mPhoneList = new ArrayList<>();
    private List<PhoneEntity> phoneEntitys;

    private boolean SoftInputIsClose = true;




    SoftKeyBoardListener.OnSoftKeyBoardChangeListener listener = new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
        @Override
        public void keyBoardShow(int height) {
            SoftInputIsClose = false;
        }

        @Override
        public void keyBoardHide(int height) {
            SoftInputIsClose = true;
            swichPopo();

        }
    };
    private long mDismissTime;


    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }



    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initViewAndData() {


        SoftKeyBoardListener.setListener(this, listener);

        BoxStore boxStore = App.getInstance().getBoxStore();
        mBox = boxStore.boxFor(PhoneEntity.class);

        mListPopupWindow = new ListPopupWindow(this);

//        editTextAddTextChangLisenter(etPhone);
        phoneEntitys = mBox.getAll();
        mPhoneList.addAll(phoneEntitys);
        Collections.reverse(mPhoneList);
        if (mPhoneList.isEmpty()) {
            mIvSelectPhone.setVisibility(View.GONE);
        } else {
            mIvSelectPhone.setVisibility(View.VISIBLE);
        }

        mSelectPhoneAdapter = new SelectPhoneAdapter(this, mPhoneList);
        mListPopupWindow.setAdapter(mSelectPhoneAdapter);
        mListPopupWindow.setAnchorView(mBottomLine);


        if (phoneEntitys.size() >= 5) {
            mListPopupWindow.setHeight(CommonUtil.getScreenH(this) / 5);
        }

        mListPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mDismissTime = SystemClock.currentThreadTimeMillis();
                mIvSelectPhone.setEnabled(true);
                mIvSelectPhone.setRotation(360);
            }
        });
        mListPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String phone = mPhoneList.get(position).getPhone();
                etPhone.setText(phone);
                etPhone.setSelection(phone.length());
                mListPopupWindow.dismiss();


            }
        });


        /*
         * 获取到焦点的监听
         */
        etPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (mListPopupWindow.isShowing()) {
                    mListPopupWindow.dismiss();

                }

            }
        });

    }


    /**
     * 给phone输入框添加 监听
     *
     * @param editText
     */
    private void editTextAddTextChangLisenter(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, final int start, int before, int count) {
                mPhoneList.clear();
                mPhoneList.addAll(phoneEntitys);
                Collections.reverse(mPhoneList);
                if (mPhoneList != null) {
                    int size = mPhoneList.size();
                    for (int i = 0; i < size; i++) {
                        PhoneEntity phoneEntity = mPhoneList.get(i);
                        if (!phoneEntity.getPhone().startsWith(s.toString().trim())) {
                            mPhoneList.remove(i);
                            i--;
                            size--;
                        }
                    }
                }


                if (mPhoneList.isEmpty() && mListPopupWindow.isShowing()) {
                    mListPopupWindow.dismiss();
                } else {
                    if (!mListPopupWindow.isShowing() && !mPhoneList.isEmpty()) {
                        mListPopupWindow.show();
                    }
                }

                mSelectPhoneAdapter.notifyDataSetChanged();


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etPhone.getText().toString().trim().length() == 11) {
                    mListPopupWindow.dismiss();
                }
            }
        });
    }

    @OnClick({R.id.tv_login_confirm,
            R.id.tv_login_register,
            R.id.tv_login_forget,
            R.id.iv_login_wx,
            R.id.iv_select_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login_confirm:
                if (AntiShake.check(view.getId()))
                    return;
                if (!doCheck()) {
                    return;
                }


                showLoadingDialog(null);
                mvpPresenter.getLogin(
                        etPhone.getText().toString(),
                        etPassword.getText().toString(),
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
                        JPushInterface.getRegistrationID(LoginActivity.this)
                );
                break;
            case R.id.tv_login_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.tv_login_forget:
                startActivity(new Intent(this, ForgetActivity.class));
                break;
            case R.id.iv_select_phone:
                //判断软件盘状态
                GeneralUtil.hideSoftInputFromWindow(this);
                //dissmis 时间出、不超过多好毫秒这个contiun  记录dissmis 时间
                if(SystemClock.currentThreadTimeMillis()-mDismissTime<20) {
                    return;
                }

                new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);

                        if (SoftInputIsClose) {
                            swichPopo();
                        }

                    }
                }.sendEmptyMessageDelayed(0,200);




                break;
            case R.id.iv_login_wx:
                if (AntiShake.check(view.getId()))
                    return;
                WxLogin.getInstance().login(new WxLogin.OnResultListener() {
                    @Override
                    public void onResult(String wxCode, String errMsg) {
                        if (TextUtils.isEmpty(errMsg)) {
                            showLoadingDialog(null);
                            mvpPresenter.appWechatLogin(Constant.TENANTCODE,
                                    Constant.SYSTEM_CODE,
                                    wxCode,
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
                                    JPushInterface.getRegistrationID(LoginActivity.this));
                        } else {
                            showShortToast(errMsg);
                        }
                    }
                });
                break;
        }
    }

    private boolean doCheck() {
        if (etPhone.getText().length() < 11) {
            showShortToast(getResources().getString(R.string.input_phone_error));
            return false;
        }
        if (etPassword.getText().length() < 6) {
            showShortToast(getResources().getString(R.string.input_password_error));
            return false;
        }
        return true;
    }


    /**
     * 切换电话号码选择框
     */
    private void swichPopo() {
        if (mListPopupWindow != null) {
            if (mListPopupWindow.isShowing()) {
                mListPopupWindow.dismiss();
            } else {
                mListPopupWindow.show();
                mIvSelectPhone.setEnabled(false);
                mIvSelectPhone.setRotation(180);

            }

        }
    }

    @Override
    public void setLoginData(String data) {
        closeLoadingDialog();
        savePhone();
        UserLogin userLogin = new Gson().fromJson(CommonUtil.baseDecrypt(data.split("\\.")[1]), UserLogin.class);
        getPref().setString(PrefUtil.USER_TOKEN, data);
        if (TextUtils.isEmpty(userLogin.getMobile())) {
            Intent intent = new Intent(this, BindingPhoneAcitivity.class);
            startActivity(intent);
            finish();
        } else {
            getPref().setString(PrefUtil.MOBILE, userLogin.getMobile());
            IntentUtils.startMainActivity(this, true);
            finish();
        }
    }

    private void savePhone() {
        String phone = etPhone.getText().toString().trim();

        List<PhoneEntity> phoneEntitys = mBox.getAll();
        for (int i = 0; i < phoneEntitys.size(); i++) {
            if (phone.equals(phoneEntitys.get(i).phone)) {
                mBox.remove(phoneEntitys.get(i));
            }
        }

        PhoneEntity phoneEntity = new PhoneEntity();
        phoneEntity.setPhone(phone);
        mBox.put(phoneEntity);
    }

    @Override
    public void setLoginError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }




}



