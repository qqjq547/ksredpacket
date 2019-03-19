package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.LookVideoResult;
import com.guochuang.mimedia.mvp.model.NestLocation;
import com.guochuang.mimedia.mvp.model.RedbagDetail;

import java.util.List;

public interface AnswerView {
    void setRemain(Integer data);
    void setData(LookVideoResult data);
    void setRedbagDetail(RedbagDetail data);
    void setError(String msg);
}
