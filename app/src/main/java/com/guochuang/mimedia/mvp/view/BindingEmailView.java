package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.Email;

public interface BindingEmailView {
    void setData(String data);

    void setError(String message);

    void setEmailVerifyError(String message);

    void setApplySuccess(Email data);
}
