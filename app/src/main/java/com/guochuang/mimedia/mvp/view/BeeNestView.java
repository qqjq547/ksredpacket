package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.DictionaryType;
import com.guochuang.mimedia.mvp.model.InfoDetail;
import com.guochuang.mimedia.mvp.model.NestAd;
import com.guochuang.mimedia.mvp.model.Reply;

import java.util.List;

public interface BeeNestView {
    void setData(NestAd data);
    void setError(String msg);
    void addFavorite(Boolean data);
    void deleteFavorite(Boolean data);
    void addReport(Boolean data);
    void getReportItem(List<DictionaryType> data);
}
