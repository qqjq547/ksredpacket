package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.BenefitType;
import com.guochuang.mimedia.mvp.model.RedbagBenefit;

import java.util.List;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface RedbagBenefitView {
    void setData(List<BenefitType> data);
    void setPageData(Page<RedbagBenefit> data);
    void setError(String msg);
}
