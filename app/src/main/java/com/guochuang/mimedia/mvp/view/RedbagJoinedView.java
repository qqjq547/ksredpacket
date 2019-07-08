package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.RedbagUser;

import java.util.List;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface RedbagJoinedView {
    void setData(List<RedbagUser> data);
    void setError(String msg);
}
