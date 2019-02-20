package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.CommentRedbag;
import com.guochuang.mimedia.mvp.model.NestAd;
import com.guochuang.mimedia.mvp.model.NestFavorite;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface CollectNestAdView {
    void setData(Page<NestFavorite> data);
    void setError(String msg);

}
