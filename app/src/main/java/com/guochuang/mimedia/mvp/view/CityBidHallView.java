package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.Area;
import com.guochuang.mimedia.mvp.model.CityBidHall;

import java.util.List;

public interface CityBidHallView {
    void setArea(List<Area> data);
    void setError(String msg);

    void setCityData(Page<CityBidHall> data);
    void setCityError(String msg);
}
