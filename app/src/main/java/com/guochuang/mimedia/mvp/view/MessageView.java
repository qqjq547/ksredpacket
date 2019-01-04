package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.Message;
import com.guochuang.mimedia.mvp.model.RedPacketReply;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface MessageView {
    void setData(Page<Message> data);
    void setCommentAdd(Integer data);
    void setCommentReply(Integer data);
    void setError(String msg);

}
