package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.Category;
import com.guochuang.mimedia.mvp.model.SetUpUser;
import com.guochuang.mimedia.mvp.model.UploadFile;

import java.util.List;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface SettingView {
    void getSetupInfo(SetUpUser data);
    void setData(UploadFile data);
    void setUpdateAvatar(Boolean data);
    void setError(String msg);
    void getLogout(String data);
    void getLogoutError(String data);
}
