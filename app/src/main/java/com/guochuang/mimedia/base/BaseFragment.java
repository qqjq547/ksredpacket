package com.guochuang.mimedia.base;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.guochuang.mimedia.tools.CustomProDialog;
import com.guochuang.mimedia.tools.PrefUtil;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseFragment extends Fragment {
    private PrefUtil pref;
    private Dialog proDialog;
    //传递过来的参数Bundle，供子类使用
    protected Bundle args;

    /**
     * 创建fragment的静态方法，方便传递参数
     * @param args 传递的参数
     * @return
     */
    public static <T extends Fragment>T newInstance(Class clazz, Bundle args) {
        T mFragment=null;
        try {
            mFragment= (T) clazz.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mFragment.setArguments(args);
        return mFragment;
    }

    /**
     * 初始创建Fragment对象时调用
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = getArguments();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onUnsubscribe();
    }

    private CompositeSubscription mCompositeSubscription;

    public void onUnsubscribe() {
        //取消注册，以避免内存泄露
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    public void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    public void showLoadingDialog(int resid) {
        String msg = getResources().getString(resid);
        showLoadingDialog(msg);
    }

    public void showLoadingDialog(String title) {
        if (proDialog != null && proDialog.isShowing()) {
            proDialog.cancel();
        }
        proDialog = CustomProDialog.show(getContext(), title, false, null);
    }

    public void showLongToast(String msg) {
        if (isVisible())
        Toast.makeText(getActivity().getApplication(), msg, Toast.LENGTH_LONG).show();
    }

    public void showLongToast(int resId) {
        if (isVisible())
        Toast.makeText(getActivity().getApplication(), resId, Toast.LENGTH_LONG).show();
    }

    public void showShortToast(String msg) {
        if (isVisible())
        Toast.makeText(getActivity().getApplication(), msg, Toast.LENGTH_SHORT).show();
    }

    public void showShortToast(int resId) {
        if (isVisible())
        Toast.makeText(getActivity().getApplication(), resId, Toast.LENGTH_SHORT).show();
    }

    public void closeLoadingDialog() {
        try {
            if (proDialog != null && proDialog.isShowing()) {
                proDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PrefUtil getPref() {
        if (pref == null) {
            pref = PrefUtil.getInstance();
        }
        return pref;
    }
    public abstract int getLayout();

    public abstract void initViewAndData();

    
}
