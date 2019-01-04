package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.RecommedUser;
import com.guochuang.mimedia.mvp.model.RecommendDetail;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface RecommendDetailView {
    void setMarketCount(String data);
    void setRecommendUser(Page<RecommedUser> data);
    void setError(String msg);

}
