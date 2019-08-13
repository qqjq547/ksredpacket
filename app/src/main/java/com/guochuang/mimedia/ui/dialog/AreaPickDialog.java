package com.guochuang.mimedia.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.guochuang.mimedia.mvp.model.MallRegionAll;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.LogUtil;
import com.guochuang.mimedia.view.LoopView;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017-06-24 0024.
 */

public class AreaPickDialog extends Dialog {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.loop_view)
    LoopView loopView;
    @BindView(R.id.btn_confirm)
    ImageButton btnConfirm;
    @BindView(R.id.btn_next)
    ImageButton btnNext;

    int targetLevel=0;
    int curLevel=0;
    List<MallRegionAll> mallRegionAlls=new ArrayList<>();

    int provincePos=0;
    int cityPos=0;

    public interface OnItemSelectListener{
        void onSelectItem(int position,int level,int provincePos,int cityPos);
    }

    public OnItemSelectListener onItemSelectListener;

    public void setOnItemSelectListener(OnItemSelectListener onItemSelectListener) {
        this.onItemSelectListener = onItemSelectListener;
    }

    public AreaPickDialog(@NonNull Context context, List<MallRegionAll> data,int targetLevel,int provincePos,int cityPos) {
        super(context, R.style.dialog_bottom_full);
        this.mallRegionAlls=data;
        this.targetLevel=targetLevel;
        this.provincePos=provincePos;
        this.cityPos=cityPos;
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        View view = View.inflate(context, R.layout.dialog_area_pick, null);
        ButterKnife.bind(this, view);
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        loopView.setInitPosition(2);
        loopView.setCanLoop(false);
        loopView.setLoopListener(new LoopView.LoopScrollListener() {
            @Override
            public void onItemSelect(int item) {

            }
        });
        setLevel(targetLevel);

    }
    public void setLevel(int level){
        curLevel=level;
        if (curLevel<targetLevel){
            btnConfirm.setVisibility(View.INVISIBLE);
            btnNext.setVisibility(View.VISIBLE);
        }else if(curLevel==targetLevel){
            btnConfirm.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.INVISIBLE);
        }
        List<String> dataList=new ArrayList<>();
        if (curLevel==0){
            for (int i=0;i<mallRegionAlls.size();i++){
                dataList.add(mallRegionAlls.get(i).getName());
            }
        }else if(curLevel==1){
            for (int i=0;i<mallRegionAlls.get(provincePos).getCityList().size();i++){
                dataList.add(mallRegionAlls.get(provincePos).getCityList().get(i).getName());
            }
        }else {
            for (int i=0;i<mallRegionAlls.get(provincePos).getCityList().get(cityPos).getAreaList().size();i++){
                dataList.add(mallRegionAlls.get(provincePos).getCityList().get(cityPos).getAreaList().get(i).getName());
            }
        }
        loopView.setDataList(dataList);
    }

    @OnClick({R.id.iv_back,R.id.btn_confirm,R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                setLevel(curLevel-1);
                break;
            case R.id.btn_confirm:
                if (onItemSelectListener!=null){
                    onItemSelectListener.onSelectItem(loopView.getSelectedItem(),curLevel,provincePos,cityPos);
                }
                dismiss();
                break;
            case R.id.btn_next:
                if (curLevel==0){
                    provincePos=loopView.getSelectedItem();
                }else if(curLevel==1){
                    cityPos=loopView.getSelectedItem();
                }
                setLevel(curLevel+1);
                break;
        }
    }
}
