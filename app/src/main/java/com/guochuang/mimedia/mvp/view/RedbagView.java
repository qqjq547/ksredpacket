package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.HomeRegion;
import com.guochuang.mimedia.mvp.model.MySeal;
import com.guochuang.mimedia.mvp.model.NestHomeAd;
import com.guochuang.mimedia.mvp.model.PublishRedbagType;
import com.guochuang.mimedia.mvp.model.Redbag;
import com.guochuang.mimedia.mvp.model.RedbagDetail;

import java.util.List;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface RedbagView {
    void setSystemRedbag(List<Redbag> data);
    void setLocationRedbag(List<Redbag> data);
    void setRedbagDetail(RedbagDetail redbagDetail);
    void setCoinAndMoney(MySeal data);
    void setScrollbar(List<String> data);
    void setUserRole(Integer data);
    void setHomeRegion(HomeRegion data);
    void setKilometre(Integer data);
    void setHomeAd(List<NestHomeAd> data);
    void setIsQualified(Boolean data);
    void setRedbagInvalid();
    void setRedbagType(PublishRedbagType publishRedbagType);
    void setError(String msg);


}
