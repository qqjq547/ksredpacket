package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.Address;
import com.guochuang.mimedia.mvp.model.CardList;

import java.util.List;


public interface MyAddressView {
    void setData(Page<Address> data);
    void setAddressData(Boolean data);
    void setError(String msg);
}
