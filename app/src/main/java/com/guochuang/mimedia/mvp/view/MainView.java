package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.NameAuthAndSafety;
import com.guochuang.mimedia.mvp.model.RainMsg;
import com.guochuang.mimedia.mvp.model.Remind;
import com.guochuang.mimedia.mvp.model.UserInfo;
import com.guochuang.mimedia.mvp.model.VersionMsg;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface MainView {
    void setUserInfo(UserInfo data);
    void setRain(RainMsg data);
    void setRainTip(Boolean data);
    void setVersion(VersionMsg data);
    void setNameAuthSafefy(NameAuthAndSafety data);
    void setMessageIsNews(Boolean data);
    void setRemind(Remind data);
    void setMarketSwitch(Integer data);
    void setError(String msg);

}
