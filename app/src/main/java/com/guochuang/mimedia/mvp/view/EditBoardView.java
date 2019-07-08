package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.BoardDetail;
import com.guochuang.mimedia.mvp.model.UploadFile;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface EditBoardView {
    void setUploadFile(UploadFile data);
    void setNotice(Boolean data);
    void getNotice(BoardDetail detail);
    void setError(String msg);

}
