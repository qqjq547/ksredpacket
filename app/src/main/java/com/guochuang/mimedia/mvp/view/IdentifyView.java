package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.UploadFile;

public interface IdentifyView {
    void setData(String data);

    void setError(String msg);

    void setUploadData(UploadFile data);

    void setUploadError(String msg);
}
