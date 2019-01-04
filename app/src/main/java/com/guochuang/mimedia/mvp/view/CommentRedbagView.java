package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.CommentRedbag;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface CommentRedbagView {
    void setData(Page<CommentRedbag> data);
    void setError(String msg);
    void setCommentDelete(Boolean data);

}
