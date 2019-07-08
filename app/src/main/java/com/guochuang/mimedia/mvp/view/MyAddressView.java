package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.Address;


public interface MyAddressView {
    void setData(Page<Address> data);
    void setAddressData(Boolean data);
    void setError(String msg);
}
