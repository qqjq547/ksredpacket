package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.NestAd;
import com.guochuang.mimedia.mvp.model.NestInfoLimit;
import com.guochuang.mimedia.mvp.model.UploadFile;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface EditAdView {
    void setAdData(NestAd data);
    void setData(Boolean data);
    void setIcon(UploadFile data);
    void setUploadFile(UploadFile data);
    void setUploadfail(String msg);
    void setLimit(NestInfoLimit data);
    void setTemplateCount(Integer data);
    void setError(String msg);


}
