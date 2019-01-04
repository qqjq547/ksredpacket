package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.SetUpUser;
import com.guochuang.mimedia.mvp.model.UploadFile;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface FeedbackView {
    void getFeedback(Integer data);
    void setData(UploadFile data);
    void setError(String msg);

}
