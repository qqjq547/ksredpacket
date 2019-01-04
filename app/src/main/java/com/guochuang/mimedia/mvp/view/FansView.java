package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.RecommedUser;

public interface FansView {
    void setData(Page<RecommedUser> data);
    void setError(String msg);
}
