package com.guochuang.mimedia.ui.activity.treasure;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.guochuang.mimedia.base.BasePresenter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.ui.activity.CityDetailActivity;
import com.guochuang.mimedia.ui.activity.EditBoardActivity;
import com.guochuang.mimedia.ui.adapter.PickImageAdapter;
import com.guochuang.mimedia.ui.adapter.PictureAdapter;
import com.guochuang.mimedia.view.GridItemDecoration;
import com.sz.gcyh.KSHongBao.R;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class ShowListActivity extends MvpActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_award)
    ImageView ivAward;
    @BindView(R.id.tv_award_number)
    TextView tvAwardNumber;
    @BindView(R.id.fl_award)
    FrameLayout flAward;
    @BindView(R.id.tv_people_time)
    TextView tvPeopleTime;
    @BindView(R.id.tv_joined_time)
    TextView tvJoinedTime;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.rv_picture)
    RecyclerView rvPicture;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.lin_edit)
    LinearLayout linEdit;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.rv_image)
    RecyclerView rvImage;
    @BindView(R.id.lin_result)
    LinearLayout linResult;

    String picture;
    String content;
    ArrayList<String> pictureArr=new ArrayList<>();
    PickImageAdapter pictureAdapter;
    List<String> waitUpload=new ArrayList<>();
    List<String> picUrlArr=new ArrayList<>();


    PictureAdapter adapter;
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_showlist;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.showlist);
        initEdit();

    }
   public void initEdit(){
       linEdit.setVisibility(View.VISIBLE);
       linResult.setVisibility(View.GONE);
       rvPicture.setLayoutManager(new GridLayoutManager(this,3));
       rvPicture.addItemDecoration(new GridItemDecoration(3,CommonUtil.dip2px(this,10),false));
       rvPicture.setItemAnimator(new DefaultItemAnimator());
       pictureArr.add(null);
       pictureAdapter=new PickImageAdapter(pictureArr);
       pictureAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
               if (TextUtils.isEmpty(pictureArr.get(position))){
                   RxPermissions rxPermissions=new RxPermissions(ShowListActivity.this);
                   rxPermissions.request(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
                       @Override
                       public void call(Boolean aBoolean) {
                           if (aBoolean) {
                               ImageSelector.builder()
                                       .useCamera(true)
                                       .setSingle(false)
                                       .setMaxSelectCount(Constant.MAX_PICK_PICTURE-pictureArr.size()+1)
                                       .start(ShowListActivity.this, Constant.REQUEST_PICK_PICTURE);
                           }else {
                               showShortToast(R.string.get_pic_permission);
                           }
                       }
                   });
               }else {
                   ArrayList<String> selectArr=(ArrayList<String>)pictureArr.clone();
                   selectArr.remove(null);
                   IntentUtils.startImagePreviewActivity(ShowListActivity.this,position,selectArr);
               }
           }
       });
       pictureAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
           @Override
           public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
               pictureArr.remove(position);
               pictureAdapter.notifyItemRemoved(position);
               if (!pictureArr.contains(null)){
                   pictureArr.add(null);
               }
               adapter.notifyDataSetChanged();
           }
       });
       rvPicture.setAdapter(pictureAdapter);
   }
   public void initResult(){
        linEdit.setVisibility(View.GONE);
        linResult.setVisibility(View.VISIBLE);
       rvImage.setLayoutManager(new GridLayoutManager(this,3));
       rvImage.addItemDecoration(new GridItemDecoration(3,CommonUtil.dip2px(this,5),true));
       adapter=new PictureAdapter(pictureArr);
       adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
               IntentUtils.startImagePreviewActivity(ShowListActivity.this, position, pictureArr);
           }
       });
       rvImage.setAdapter(adapter);
   }

    @OnClick({R.id.iv_back, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_confirm:
                break;
        }
    }
}
