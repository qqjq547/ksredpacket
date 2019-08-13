package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.MallRegionAll;
import com.guochuang.mimedia.mvp.model.MallRegionInfo;
import com.guochuang.mimedia.mvp.model.NestLocation;

import java.util.List;

public interface CityBuyPreView {
    void setRegionAll(List<MallRegionAll> data);
    void setRegionInfo(MallRegionInfo data);
    void setError(String msg);
}
