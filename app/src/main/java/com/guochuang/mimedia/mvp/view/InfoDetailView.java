package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.DictionaryType;
import com.guochuang.mimedia.mvp.model.Reply;
import com.guochuang.mimedia.mvp.model.InfoDetail;
import java.util.List;

public interface InfoDetailView {
    void setData(InfoDetail data);
    void setPageError(String msg);
    void setError(String msg);
    void setReadEnd(String data);
    void addComment(Integer data);
    void getComment(Page<Reply> data);
    void addFavorite(Integer data);
    void deleteFavorite(Boolean data);
    void addPraise(Integer data);
    void deletePraise(Boolean data);
    void addReport(Integer data);
    void getReportItem(List<DictionaryType> data);
    void readingGenerate(String data);
    void shareAdd(Integer data);
}
