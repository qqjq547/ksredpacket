package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.ShareBenefit;

import java.util.List;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface ShareBenefitDetailView {
    void setData(List<ShareBenefit> data);
    void setError(String msg);
}
