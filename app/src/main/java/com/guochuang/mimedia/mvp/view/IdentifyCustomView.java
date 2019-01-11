package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.UploadFile;

public interface IdentifyCustomView {
    void setData(Integer data);

    void setError(String msg);

    void setUploadData(UploadFile data,boolean isFront);

    void setUploadError(String msg);
}
