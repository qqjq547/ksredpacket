package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.CardList;

import java.util.List;


public interface AddAddressView {
    void setData(Boolean data);
    void setDelData(Boolean data);
    void setUpdateData(Boolean data);
    void setError(String msg);


}
