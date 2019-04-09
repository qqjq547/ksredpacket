package com.guochuang.mimedia.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.mvp.model.RedbagMenu;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.ui.adapter.RedbagTypeAdapter;
import com.guochuang.mimedia.view.GridItemDecoration;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017-06-24 0024.
 */

public class RedbagTypeDialog extends Dialog {

    @BindView(R.id.rv_menu)
    RecyclerView rvMenu;
    public List<RedbagMenu> menuArr=new ArrayList<>();
    OnItemClickListener onOpenResultListener;
    RedbagTypeAdapter adapter;

    public interface OnItemClickListener {
        void onRandom();

        void onPassword();

        void onLucky();

        void onVideo();

        void onQuestion();
    }

    public RedbagTypeDialog(@NonNull Context context, String type, OnItemClickListener onOpenResultListener) {
        super(context, R.style.dialog_bottom_full);
        this.onOpenResultListener = onOpenResultListener;
        View view = View.inflate(context, R.layout.dialog_redbag_type, null);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        ButterKnife.bind(this, view);
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        List<String> typeList = Arrays.asList(type.split(","));
        initData(typeList);
        adapter=new RedbagTypeAdapter(menuArr);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                cancel();
                if (RedbagTypeDialog.this.onOpenResultListener==null){
                    return;
                }
                String redPacketType=menuArr.get(position).getType();
                switch (redPacketType){
                    case Constant.RED_PACKET_TYPE_RANDOM:
                        RedbagTypeDialog.this.onOpenResultListener.onRandom();
                        break;
                    case Constant.RED_PACKET_TYPE_PASSWORD:
                        RedbagTypeDialog.this.onOpenResultListener.onPassword();
                        break;
                    case Constant.RED_PACKET_TYPE_LUCKY:
                        RedbagTypeDialog.this.onOpenResultListener.onLucky();
                        break;
                    case Constant.RED_PACKET_TYPE_VIDEO:
                        RedbagTypeDialog.this.onOpenResultListener.onVideo();
                        break;
                    case Constant.RED_PACKET_TYPE_SURVEY:
                        RedbagTypeDialog.this.onOpenResultListener.onQuestion();
                        break;
                }
            }
        });
        rvMenu.setLayoutManager(new GridLayoutManager(context,3));
        rvMenu.setAdapter(adapter);
    }
    public void initData(List<String> typeList){
        for (String type:typeList){
            switch (type){
                case Constant.RED_PACKET_TYPE_RANDOM:
                    menuArr.add(new RedbagMenu(R.string.random_redbag,R.drawable.ic_packet_random,type));
                    break;
                case Constant.RED_PACKET_TYPE_PASSWORD:
                    menuArr.add(new RedbagMenu(R.string.password_redbag,R.drawable.ic_packet_password,type));
                    break;
                case Constant.RED_PACKET_TYPE_LUCKY:
                    menuArr.add(new RedbagMenu(R.string.lucky_redbag,R.drawable.ic_packet_lucky,type));
                    break;
                case Constant.RED_PACKET_TYPE_VIDEO:
                    menuArr.add(new RedbagMenu(R.string.video_redbag,R.drawable.ic_packet_video,type));
                    break;
                case Constant.RED_PACKET_TYPE_SURVEY:
                    menuArr.add(new RedbagMenu(R.string.questionnaire_redbag,R.drawable.ic_packet_survey,type));
                    break;
            }
        }
    }

}
