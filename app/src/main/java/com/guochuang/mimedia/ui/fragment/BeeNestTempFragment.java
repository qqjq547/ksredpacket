package com.guochuang.mimedia.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpFragment;
import com.guochuang.mimedia.mvp.model.NestTemplate;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.guochuang.mimedia.ui.adapter.PictureAdapter;
import com.guochuang.mimedia.view.GridItemDecoration;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;

import butterknife.BindView;

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

    NestTemplate template;
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
        GlideImgManager.loadImage(getContext(),template.getCoverPicture(),ivBackground);
        tvTempName.setText(template.getShortMsg());
        tvName.setText(template.getTitle());
        tvDesc.setText(template.getIntroduction());
        if (TextUtils.isEmpty(template.getLinkText())||TextUtils.isEmpty(template.getLinkUrl())){
            tvUrl.setVisibility(View.GONE);
        }else {
            tvUrl.setVisibility(View.VISIBLE);
            tvUrl.setText(template.getLinkText());
        }
        tvAddress.setText(template.getAddress()+template.getAddressDetail());
        tvCall.setText(template.getContactPhone());
        tvWechat.setText(template.getWechat());
        tvWeibo.setText(template.getWeibo());
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
    public void setTemp(NestTemplate temp){
        this.template=temp;
        pictureArr.addAll(temp.getPictureList());
    }

}
