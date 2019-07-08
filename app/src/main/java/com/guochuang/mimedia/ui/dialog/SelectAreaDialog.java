package com.guochuang.mimedia.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.mvp.model.LocalArea;
import com.guochuang.mimedia.tools.GsonUtil;
import com.guochuang.mimedia.ui.adapter.TextAdapter;
import com.sz.gcyh.KSHongBao.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectAreaDialog extends Dialog {
    @BindView(R.id.tb_title)
    TabLayout tbTitle;
    @BindView(R.id.rv_item)
    RecyclerView rvItem;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;

    List<LocalArea> localAreaArr=new ArrayList<>();
    List<String> provinceArr=new ArrayList<>();
    List<String> cityArr=new ArrayList<>();
    List<String> areaArr=new ArrayList<>();
    TextAdapter textAdapter;
    int provincePos=0;
    int cityPos=0;
    int areaPos=0;
    TabLayout.Tab tabItem1,tabItem2,tabItem3;
    OnSelectListener onSelectListener;
    public interface OnSelectListener{
        void onResult(String province,String city,String area);
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public SelectAreaDialog(@NonNull final Context context) {
        super(context, R.style.dialog_bottom_full);

        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        View view = View.inflate(context, R.layout.dialog_select_area, null);
        ButterKnife.bind(this, view);
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        tabItem1=tbTitle.newTab().setText(R.string.pls_select);
        tabItem2=tbTitle.newTab().setText(R.string.pls_select);
        tabItem3=tbTitle.newTab().setText(R.string.pls_select);
        rvItem.setLayoutManager(new LinearLayoutManager(context,OrientationHelper.VERTICAL,false));
        textAdapter=new TextAdapter(null);
        textAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (tbTitle.getSelectedTabPosition()==0){
                    tabItem1.setText(textAdapter.getData().get(position));
                    setCity(position);
                }else if(tbTitle.getSelectedTabPosition()==1){
                    tabItem2.setText(textAdapter.getData().get(position));
                    setArea(position);
                }else {
                    tabItem3.setText(textAdapter.getData().get(position));
                    areaPos=position;
                    textAdapter.setSelectPos(areaPos);
                    if (onSelectListener!=null){
                        onSelectListener.onResult(tabItem1.getText().toString(),tabItem2.getText().toString(),tabItem3.getText().toString());
                    }
                }
            }
        });
        rvItem.setAdapter(textAdapter);
        tbTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        textAdapter.setNewData(provinceArr);
                        textAdapter.setSelectPos(provincePos);
                        break;
                    case 1:
                        textAdapter.setNewData(cityArr);
                        textAdapter.setSelectPos(cityPos);
                        break;
                    case 2:
                        textAdapter.setNewData(areaArr);
                        textAdapter.setSelectPos(areaPos);
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str=getFromAssets("area.json");
                localAreaArr=GsonUtil.jsonToList(str,LocalArea.class);
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pbLoading.setVisibility(View.GONE);
                        rvItem.setVisibility(View.VISIBLE);
                        setProvince();
                    }
                });
            }
        }).start();

    }

    public String getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(getContext().getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public void setProvince(){
        provinceArr.clear();
        if (tbTitle.getTabCount()>2){
            tabItem3.setText(R.string.pls_select);
        }
        if (tbTitle.getTabCount()>1){
            tabItem2.setText(R.string.pls_select);
        }
        if (tbTitle.getTabCount()<1){
            tbTitle.addTab(tabItem1);
        }
        tabItem1.setText(R.string.pls_select);
        tabItem1.select();
        for (int i=0;i<localAreaArr.size();i++){
            provinceArr.add(localAreaArr.get(i).getName());
        }
        textAdapter.setNewData(provinceArr);

    }
    public void setCity(int provincePos){
        this.provincePos=provincePos;
        if (tbTitle.getTabCount()>2){
            tabItem3.setText(R.string.pls_select);
        }
        if (tbTitle.getTabCount()<2){
            tbTitle.addTab(tabItem2);
        }
        tabItem2.setText(R.string.pls_select);
        tabItem2.select();
        cityArr.clear();
        List<LocalArea.CityBean> cityList=localAreaArr.get(provincePos).getCity();
        for (int i=0;i<cityList.size();i++){
            cityArr.add(cityList.get(i).getName());
        }
        textAdapter.setNewData(cityArr);
    }
    public void setArea(int cityPos){
        this.cityPos=cityPos;
        if (tbTitle.getTabCount()<3){
            tbTitle.addTab(tabItem3);
        }
        tabItem3.setText(R.string.pls_select);
        tabItem3.select();
        areaArr.clear();
        List<String> areaList=localAreaArr.get(provincePos).getCity().get(cityPos).getArea();
        for (int i=0;i<areaList.size();i++){
            areaArr.add(areaList.get(i));
        }
        textAdapter.setNewData(areaArr);
    }
}
