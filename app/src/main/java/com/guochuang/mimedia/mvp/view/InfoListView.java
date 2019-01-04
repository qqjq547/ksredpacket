package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.InfoItem;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface InfoListView {
    void setData(Page<InfoItem> data);
    void setError(String msg);


}
