package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.MallBidRecord;
import com.guochuang.mimedia.mvp.model.MallNodeResult;
import com.guochuang.mimedia.mvp.model.MallRegionDetail;
import com.guochuang.mimedia.mvp.model.MyMallRecord;
import com.guochuang.mimedia.mvp.model.MyMallStat;
import com.guochuang.mimedia.mvp.model.NestLocation;

import java.util.List;

public interface MyMallView {
    void setMyStat(MyMallStat data);
    void setList(Page<MyMallRecord> data);
    void setMallNodeResult(MallNodeResult data);
    void setMallNodeOrderst(List<MallBidRecord> data);
    void setMallRegionDetail(MallRegionDetail data);
    void setError(String msg);
}
