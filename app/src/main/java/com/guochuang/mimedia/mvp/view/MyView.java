package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.NestAuctionMsg;
import com.guochuang.mimedia.mvp.model.RecommendData;
import com.guochuang.mimedia.mvp.model.RegionCore;
import com.guochuang.mimedia.mvp.model.UserInfo;

public interface MyView {
    void setRecommendData(RecommendData data);
    void setRegionCoreData(RegionCore data);
    void setAuctionMsg(NestAuctionMsg data);
    void setError(String msg);


}
