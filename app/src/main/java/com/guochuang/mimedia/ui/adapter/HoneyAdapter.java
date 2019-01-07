package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class HoneyAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public HoneyAdapter(@Nullable List<String> data) {
        super(R.layout.item_honey,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
       helper.setText(R.id.tv_name,item);
        GlideImgManager.loadCornerImage(mContext,"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2338693105,1140444294&fm=58&bpow=512&bpoh=512",(ImageView) helper.getView(R.id.iv_icon),3,true);
    }
}
