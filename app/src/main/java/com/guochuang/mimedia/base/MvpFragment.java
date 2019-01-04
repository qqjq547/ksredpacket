package com.guochuang.mimedia.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;


public abstract class MvpFragment<P extends BasePresenter> extends BaseFragment {
    protected P mvpPresenter;
    private View view;
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mvpPresenter = createPresenter();
        if (view == null) {
            view = inflater.inflate(getLayout(), container, false);
            ButterKnife.bind(this, view);
        }
        initViewAndData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }
    protected abstract P createPresenter();
}
