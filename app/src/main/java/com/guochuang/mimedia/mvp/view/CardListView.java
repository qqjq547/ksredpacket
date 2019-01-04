package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.CardList;

import java.util.List;


public interface CardListView {
    void setData(List<CardList> data);

    void setError(String msg);
}
