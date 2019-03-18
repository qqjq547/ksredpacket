package com.guochuang.mimedia.base;

import android.content.Intent;
import android.os.Bundle;

import com.guochuang.mimedia.mvp.view.MainView;
import com.guochuang.mimedia.tools.Constant;


public abstract class MvpActivity<P extends BasePresenter> extends BaseActivity {
    protected P mvpPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mvpPresenter = createPresenter();
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        initViewAndData();
    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }


}
