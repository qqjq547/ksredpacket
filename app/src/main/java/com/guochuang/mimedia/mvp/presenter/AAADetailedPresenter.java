package com.guochuang.mimedia.mvp.presenter;

import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.mvp.view.AAADetailedView;

public class AAADetailedPresenter extends BasePresenter<AAADetailedView> {
    public AAADetailedPresenter(AAADetailedView view) {
        attachView(view);
    }

}
