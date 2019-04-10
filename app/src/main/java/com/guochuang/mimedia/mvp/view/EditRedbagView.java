package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.EditRedbagConfig;
import com.guochuang.mimedia.mvp.model.LookSurevyResult;
import com.guochuang.mimedia.mvp.model.LookVideoResult;
import com.guochuang.mimedia.mvp.model.LuckyConfig;
import com.guochuang.mimedia.mvp.model.RedBagConfig;
import com.guochuang.mimedia.mvp.model.RedbagTemp;
import com.guochuang.mimedia.mvp.model.UploadFile;

import java.util.List;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface EditRedbagView {
    void setData(String data);
    void setUploadFile(UploadFile data);
    void setUploadfail(String msg);
    void setTempData(List<RedbagTemp> data);
    void setLuckyConfig(LuckyConfig data);
    void setConfig(EditRedbagConfig data);
    void uploadVideoSuccess(UploadFile data);
    void setProblems(LookVideoResult data);
    void checkConfigSuccess(RedBagConfig data);
    void setError(String msg);



}
