package com.guochuang.mimedia.ui.adapter;

import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guochuang.mimedia.mvp.model.InfoItem;
import com.guochuang.mimedia.mvp.model.NestFavorite;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.sz.gcyh.KSHongBao.R;

import java.util.List;

public class NestFavoriteAdapter extends BaseQuickAdapter<NestFavorite,BaseViewHolder> {

    public NestFavoriteAdapter(@Nullable List<NestFavorite> data) {
        super(R.layout.item_info,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NestFavorite item) {


    }

}
