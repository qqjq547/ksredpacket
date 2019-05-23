package com.guochuang.mimedia.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.SafeCenter;
import com.guochuang.mimedia.mvp.presenter.SafeCenterPresenter;
import com.guochuang.mimedia.mvp.view.SafeCenterView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.PrefUtil;
import com.guochuang.mimedia.tools.WxLogin;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SafeCenterActivity extends MvpActivity<SafeCenterPresenter> implements SafeCenterView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_identity)
    TextView tvIdentity;
    @BindView(R.id.lin_identity)
    LinearLayout linIdentity;
    @BindView(R.id.tv_my_weixin)
    TextView tvMyWeixin;
    @BindView(R.id.lin_my_weixin)
    LinearLayout linMyWeixin;
    @BindView(R.id.tv_my_zfb)
    TextView tvMyZfb;
    @BindView(R.id.lin_my_zfb)
    LinearLayout linMyZfb;
    @BindView(R.id.tv_my_bankcard)
    TextView tvMyBankcard;
    @BindView(R.id.lin_my_bankcard)
    LinearLayout linMyBankcard;
    @BindView(R.id.lin_login_pwd)
    LinearLayout linLoginPwd;
    @BindView(R.id.lin_trade_pwd)
    LinearLayout linTradePwd;
    @BindView(R.id.cb_finger_pwd)
    CheckBox cbFingerPwd;
    @BindView(R.id.lin_finger_pwd)
    LinearLayout linFingerPwd;
    @BindView(R.id.cb_gesture_pwd)
    CheckBox cbGesturePwd;
    @BindView(R.id.lin_gesture_pwd)
    LinearLayout linGesturePwd;
    @BindView(R.id.lin_update_gesture_pwd)
    LinearLayout linUpdateGesturePwd;
    SafeCenter safeCenter;
    @BindView(R.id.tv_my_phone)
    TextView tvMyPhone;
    @BindView(R.id.lin_my_phone)
    LinearLayout linMyPhone;
    @BindView(R.id.tv_my_email)
    TextView tvMyEmail;
    @BindView(R.id.lin_my_email)
    LinearLayout linMyEmail;
    @BindView(R.id.tv_my_email_arrow)
    TextView tvMyEmailArrow;
    @BindView(R.id.tv_my_phone_arrow)
    TextView tvMyPhoneArrow;


    @Override
    protected SafeCenterPresenter createPresenter() {
        return new SafeCenterPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_safe_center;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.text_my_safe);
        showLoadingDialog(null);
        if (getPref().getInt(PrefUtil.IDENTITY, 0) == 1) {
            tvIdentity.setText(R.string.has_identify);
        } else {
            tvIdentity.setText(R.string.has_unidentify);
        }
        mvpPresenter.userInfoSecurityCenter();
    }

    @OnClick({R.id.iv_back, R.id.lin_identity, R.id.lin_my_weixin, R.id.lin_my_zfb, R.id.lin_my_bankcard, R.id.lin_login_pwd, R.id.lin_trade_pwd, R.id.lin_update_gesture_pwd, R.id.lin_my_phone, R.id.lin_my_email})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.lin_identity:
                if (safeCenter != null) {
                    if (safeCenter.getNameAuthInfo() == null) {
                        startActivityForResult(new Intent(this, IdentifyActivity.class), Constant.REFRESH);
                    } else {
                        if (safeCenter.getNameAuthInfo().getAuditStatus() == Constant.STATUS_AUDIT_FAIL) {
                            String desp = safeCenter.getNameAuthInfo().getAuditDescription();
                            startActivityForResult(new Intent(this, IdentifyActivity.class).putExtra(Constant.DESCRIPTION, desp), Constant.REFRESH);
                        } else {
                            startActivity(new Intent(this, IdentifyResultActivity.class));
                        }
                    }
                }
                break;
            case R.id.lin_my_weixin:
                if (safeCenter != null) {
                    if (safeCenter.getHasWechat() == 0) {
                        WxLogin.getInstance().login(new WxLogin.OnResultListener() {
                            @Override
                            public void onResult(String wxCode, String errMsg) {
                                if (TextUtils.isEmpty(errMsg)) {
                                    showLoadingDialog(null);
                                    mvpPresenter.userAppWechatBind(
                                            Constant.TENANTCODE,
                                            Constant.SYSTEM_CODE,
                                            wxCode
                                    );
                                } else {
                                    showShortToast(errMsg);
                                }
                            }
                        });
                    } else {
                        Intent intent = new Intent(this, MyWechatActivity.class).putExtra(Constant.WHO_OPEN_MYWECHATACTIVITY, getClass().getSimpleName());
                        startActivity(intent);
                    }
                }
                break;
            case R.id.lin_my_zfb:
                if (safeCenter != null) {
                    if (getPref().getInt(PrefUtil.IDENTITY, 0) == 0) {
                        showShortToast(R.string.pls_identity_first);
                        startActivityForResult(new Intent(this, IdentifyActivity.class), Constant.REFRESH);
                    } else {
                        startActivityForResult(new Intent(this, MyAlipayActivity.class), Constant.REFRESH);
                    }
                }
                break;
            case R.id.lin_my_bankcard:
                if (safeCenter != null) {
                    if (getPref().getInt(PrefUtil.IDENTITY, 0) == 0) {
                        showShortToast(R.string.pls_identity_first);
                        startActivityForResult(new Intent(this, IdentifyActivity.class), Constant.REFRESH);
                    } else if (getPref().getInt(PrefUtil.SAFECODE, 0) == 0) {
                        showShortToast(R.string.pls_safecode_first);
                        startActivityForResult(new Intent(this, TradePwdActivity.class), Constant.REFRESH);
                    } else {
                        startActivityForResult(new Intent(this, CardListActivity.class), Constant.REFRESH);
                    }
                }
                break;
            case R.id.lin_login_pwd:
                startActivityForResult(new Intent(this, LoginPwdActivity.class), Constant.REFRESH);
                break;
            case R.id.lin_trade_pwd:
                startActivityForResult(new Intent(this, TradePwdActivity.class), Constant.REFRESH);
                break;
            case R.id.lin_update_gesture_pwd:
                break;

            case R.id.lin_my_phone:
                //我的手机
                if (!TextUtils.isEmpty(safeCenter.getMobile()))
                    return;
                startActivityForResult(new Intent(this, BindingPhoneAcitivity.class), Constant.OPENT_BINDINGPHONEACITIVITY);
                break;
            case R.id.lin_my_email:
                if (!TextUtils.isEmpty(safeCenter.getEmail()))
                    return;
                //我的的邮箱
                startActivityForResult(new Intent(this, BindingEmailActivity.class), Constant.OPENT_BINDINGEMAILACTIVITY);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Constant.REFRESH) {
            mvpPresenter.userInfoSecurityCenter();
        }

        if(resultCode == RESULT_OK && requestCode == Constant.OPENT_BINDINGEMAILACTIVITY) {
            //设置
            String email = data.getStringExtra(Constant.EMAIL_KEY);
            tvMyEmail.setText(email);
            tvMyEmailArrow.setVisibility(View.GONE);
            if(safeCenter != null) {
                safeCenter.setEmail(email);

            }

        }

        if(resultCode == RESULT_OK && requestCode == Constant.OPENT_BINDINGPHONEACITIVITY) {
            //设置
            String phone = data.getStringExtra(Constant.PHONE_KEY);
            tvMyPhone.setText(phone);
            if(safeCenter != null) {
                safeCenter.setMobile(phone);
            }

        }
    }

    @Override
    public void setData(SafeCenter data) {
        closeLoadingDialog();
        this.safeCenter = data;
        if (data.getNameAuthInfo() == null) {
            tvIdentity.setText(R.string.has_unidentify);
            getPref().setInt(PrefUtil.IDENTITY, 0);
        } else {
            //审核中
            if (safeCenter.getNameAuthInfo().getAuditStatus() == Constant.STATUS_AUDIT_FAIL) {
                tvIdentity.setText(R.string.review_fail);
                getPref().setInt(PrefUtil.IDENTITY, 0);
            } else if (safeCenter.getNameAuthInfo().getAuditStatus() == Constant.STATUS_AUDIT_INREVIEW) {
                tvIdentity.setText(R.string.in_review);
                getPref().setInt(PrefUtil.IDENTITY, 0);
            } else if (safeCenter.getNameAuthInfo().getAuditStatus() == Constant.STATUS_AUDIT_PASS) {
                tvIdentity.setText(R.string.has_identify);
                getPref().setInt(PrefUtil.IDENTITY, 1);
            }

        }
//        if (redbagDetail.getHasWechat() > 0) {
//            tvMyWeixin.setText(R.string.has_bind);
//        } else {
//            tvMyWeixin.setText(R.string.has_unbind);
//        }
        if (data.getAlipayAccountInfo() == null || TextUtils.isEmpty(data.getAlipayAccountInfo().getAlipayRealName())) {
            tvMyZfb.setText(R.string.has_unbind);
        } else {
            tvMyZfb.setText(R.string.has_bind);
        }
        if (data.getMyBankCardCount() == 0) {
            tvMyBankcard.setText(R.string.has_unbind);
        } else {
            tvMyBankcard.setText(String.format(getResources().getString(R.string.format_bankcard), data.getMyBankCardCount()));
        }
        if (data.getHasWechat() == 0) {
            tvMyWeixin.setText(R.string.has_unbind);
        } else {
            tvMyWeixin.setText(R.string.has_bind);
        }

        tvMyPhone.setText(TextUtils.isEmpty(data.getMobile()) ? getResources().getString(R.string.has_unbind) : data.getMobile());
        tvMyEmail.setText(TextUtils.isEmpty(data.getEmail()) ? getResources().getString(R.string.has_unbind) : data.getEmail());
        tvMyEmailArrow.setVisibility(TextUtils.isEmpty(data.getEmail()) ? View.VISIBLE:View.GONE);
        tvMyPhoneArrow.setVisibility(TextUtils.isEmpty(data.getMobile()) ? View.VISIBLE:View.GONE);
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }

    @Override
    public void setBindWxData(String data) {
        closeLoadingDialog();
        showShortToast(getResources().getString(R.string.success));
        safeCenter.setHasWechat(1);
        tvMyWeixin.setText(R.string.has_bind);
//        showLoadingDialog(null);
//        mvpPresenter.userInfoSecurityCenter();
    }

    @Override
    public void setBindWxError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }


}
