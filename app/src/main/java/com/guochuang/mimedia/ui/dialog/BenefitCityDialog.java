package com.guochuang.mimedia.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.guochuang.mimedia.mvp.model.BenefitType;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.ui.adapter.CityBenefitAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017-06-24 0024.
 */

public class BenefitCityDialog extends Dialog {

    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.rv_city)
    RecyclerView rvCity;

    CityBenefitAdapter adapter;
    List<BenefitType> typeList=new ArrayList<>();
    OnCheckListener onCheckListener;
    public interface OnCheckListener{
        void onChecked(String cityName,String districtName);
    }

    public BenefitCityDialog(@NonNull Context context, List<BenefitType> typeArr, final OnCheckListener onCheckListener) {
        super(context, R.style.dialog_bottom_full);
        this.onCheckListener=onCheckListener;
        this.typeList=typeArr;
        View view = View.inflate(context, R.layout.dialog_benefit_city, null);
        ButterKnife.bind(this, view);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        rvCity.setLayoutManager(new LinearLayoutManager(context,OrientationHelper.VERTICAL,false));
        adapter=new CityBenefitAdapter(typeArr);
        adapter.setOnSelectTagListener(new CityBenefitAdapter.OnSelectTagListener() {
            @Override
            public void onSelectTag(String cityName, String districtName) {
                dismiss();
                if (onCheckListener!=null)
                    onCheckListener.onChecked(cityName,districtName);
            }
        });
        rvCity.setAdapter(adapter);
    }


    @OnClick(R.id.tv_all)
    public void onViewClicked() {
        dismiss();
        if (onCheckListener!=null)
            onCheckListener.onChecked(null,null);
    }
}
