package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.LuckyConfig;
import com.guochuang.mimedia.mvp.model.RedbagTemp;
import com.guochuang.mimedia.mvp.model.UploadFile;

import java.util.List;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface EditAdView {
    void setData(String data);
    void setIcon(UploadFile data);
    void setUploadFile(UploadFile data);
    void setUploadfail(String msg);
    void setTempData(List<RedbagTemp> data);
    void setError(String msg);


}
