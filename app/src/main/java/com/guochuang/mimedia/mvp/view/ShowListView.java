package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.BoardDetail;
import com.guochuang.mimedia.mvp.model.SnatchShow;
import com.guochuang.mimedia.mvp.model.UploadFile;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface ShowListView {
    void setUploadFile(UploadFile data);
    void setShowList(Boolean data);
    void getShowList(SnatchShow detail);
    void setError(String msg);

}
