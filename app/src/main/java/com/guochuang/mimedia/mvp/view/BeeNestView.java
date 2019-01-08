package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.DictionaryType;
import com.guochuang.mimedia.mvp.model.InfoDetail;
import com.guochuang.mimedia.mvp.model.Reply;

import java.util.List;

public interface BeeNestView {
    void setData(InfoDetail data);
    void setError(String msg);
    void addFavorite(Integer data);
    void deleteFavorite(Boolean data);
    void addReport(Integer data);
    void getReportItem(List<DictionaryType> data);
    void shareAdd(Integer data);
}
