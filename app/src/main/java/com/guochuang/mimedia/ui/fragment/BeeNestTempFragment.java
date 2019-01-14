package com.guochuang.mimedia.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpFragment;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.ui.activity.CityDetailActivity;
import com.guochuang.mimedia.ui.activity.EditBoardActivity;
import com.guochuang.mimedia.ui.adapter.PictureAdapter;
import com.guochuang.mimedia.ui.adapter.PictureVerticalAdapter;
import com.guochuang.mimedia.view.GridItemDecoration;
import com.guochuang.mimedia.view.VerticalDecoration;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BeeNestTempFragment extends MvpFragment {
    @BindView(R.id.iv_background)
    ImageView ivBackground;
    @BindView(R.id.tv_temp_name)
    TextView tvTempName;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_url)
    TextView tvUrl;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.lin_location)
    LinearLayout linLocation;
    @BindView(R.id.tv_call)
    TextView tvCall;
    @BindView(R.id.tv_wechat)
    TextView tvWechat;
    @BindView(R.id.tv_weibo)
    TextView tvWeibo;
    @BindView(R.id.lin_info)
    LinearLayout linInfo;
    @BindView(R.id.rv_picture)
    RecyclerView rvPicture;
    PictureAdapter adapter;
    ArrayList<String> pictureArr=new ArrayList<>();
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_beenest_temp;
    }

    @Override
    public void initViewAndData() {
        if (adapter!=null){
            return;
        }
        pictureArr.add("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2774071914,778442818&fm=173&app=25&f=JPEG?w=640&h=400&s=FDB83BD10E76448043A0F550030040F3");
        pictureArr.add("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3253348826,163743007&fm=173&app=25&f=JPEG?w=405&h=300&s=3DA5CB1540027F41048008CD0300E0A1");
        pictureArr.add("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2669277608,2915862647&fm=173&app=25&f=JPEG?w=400&h=427&s=6E230FC306CA50F49994BCF803001011");
        adapter=new PictureAdapter(pictureArr);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                IntentUtils.startImagePreviewActivity(getActivity(),position,pictureArr);
            }
        });
        rvPicture.setLayoutManager(new GridLayoutManager(getActivity(),3));
        rvPicture.addItemDecoration(new GridItemDecoration(3,10,false));
        rvPicture.setAdapter(adapter);
    }

}
