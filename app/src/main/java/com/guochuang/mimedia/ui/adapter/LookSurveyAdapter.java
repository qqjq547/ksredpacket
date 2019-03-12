package com.guochuang.mimedia.ui.adapter;

import android.content.Context;

import com.guochuang.mimedia.base.recycleview.adapter.CommonRecyclerAdapter;
import com.guochuang.mimedia.base.recycleview.adapter.MultiTypeSupport;
import com.guochuang.mimedia.base.recycleview.adapter.ViewHolder;

import java.util.List;

public class LookSurveyAdapter extends CommonRecyclerAdapter<String> {
    public LookSurveyAdapter(Context context, List<String> strings, MultiTypeSupport<String> multiTypeSupport) {
        super(context, strings, multiTypeSupport);
    }

    @Override
    protected void convert(ViewHolder holder, int position, String s) {


    }
}
