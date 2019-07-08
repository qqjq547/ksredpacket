package com.guochuang.mimedia.mvp.view;
import com.guochuang.mimedia.mvp.model.MyQC;
import com.guochuang.mimedia.mvp.model.MySeal;
import com.guochuang.mimedia.mvp.model.NestAuctionMsg;
import com.guochuang.mimedia.mvp.model.RecommendData;
import com.guochuang.mimedia.mvp.model.RegionCore;

public interface MyView {
    void setRecommendData(RecommendData data);
    void setRegionCoreData(RegionCore data);
    void setAuctionMsg(NestAuctionMsg data);
    void setMySeal(MySeal data);
    void setMyQC(MyQC data);
    void setError(String msg);



}
