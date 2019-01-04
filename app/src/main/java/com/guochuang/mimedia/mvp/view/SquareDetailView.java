package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.FavoriteAndPraise;
import com.guochuang.mimedia.mvp.model.RedPacketReply;
import com.guochuang.mimedia.mvp.model.RedbagDetail;
import com.guochuang.mimedia.mvp.model.RedbagInfo;

public interface SquareDetailView {
    void setData(RedbagDetail data);
    void setPraiseAdd(Integer data);
    void setPraiseDelete(Boolean data);
    void setFavoriteAdd(Integer data);
    void setFavoriteDelete(Boolean data);
    void setCommentList(Page<RedPacketReply> data);
    void setCommentAdd(Integer data);
    void setCommentReply(Integer data);
    void setIsFavoritedAndPraised(FavoriteAndPraise data);
    void setError(String msg);
}
