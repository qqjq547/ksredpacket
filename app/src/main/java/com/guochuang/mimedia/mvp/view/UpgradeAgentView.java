package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.InviterUser;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface UpgradeAgentView {
    void setData(Integer data);
    void setInviter(InviterUser data);
    void setError(String msg);

}
