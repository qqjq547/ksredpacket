package com.guochuang.mimedia.ui.activity.treasure;

import android.Manifest;
import android.content.Intent;
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
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.Snatch;
import com.guochuang.mimedia.mvp.model.SnatchShow;
import com.guochuang.mimedia.mvp.model.UploadFile;
import com.guochuang.mimedia.mvp.presenter.ShowListPresenter;
import com.guochuang.mimedia.mvp.view.ShowListView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.guochuang.mimedia.ui.adapter.PickImageAdapter;
import com.guochuang.mimedia.ui.adapter.PictureAdapter;
import com.guochuang.mimedia.view.GridItemDecoration;
import com.sz.gcyh.KSHongBao.R;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class ShowListActivity extends MvpActivity<ShowListPresenter> implements ShowListView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_period)
    TextView tvPeriod;
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
    ArrayList<String> waitUpload=new ArrayList<>();
    ArrayList<String> picUrlArr=new ArrayList<>();
    Snatch snatch;

    PictureAdapter adapter;
    @Override
    protected ShowListPresenter createPresenter() {
        return new ShowListPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_showlist;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.showlist);
        snatch=(Snatch) getIntent().getSerializableExtra(Constant.SNATCH);
        tvName.setText(snatch.getSnatchName());
        GlideImgManager.loadImage(this,snatch.getSnatchImg(),ivIcon);
        tvPeriod.setText(String.format(getString(R.string.format_peroid),snatch.getSnatchPeriods()));
        tvAwardNumber.setText(snatch.getLuckNum());
        tvPeopleTime.setText(String.valueOf(snatch.getPayNum()));
        tvJoinedTime.setText(snatch.getPayDate());
        if (snatch.getSnatchShowId()>0){
            initResult();
        }else {
            initEdit();
        }
    }
   public void initEdit(){
       linEdit.setVisibility(View.VISIBLE);
       tvConfirm.setVisibility(View.VISIBLE);
       linResult.setVisibility(View.GONE);
       rvPicture.setLayoutManager(new GridLayoutManager(this,3));
       rvPicture.addItemDecoration(new GridItemDecoration(3,CommonUtil.dip2px(this,5),false));
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
        tvConfirm.setVisibility(View.GONE);
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
       showLoadingDialog(null);
       mvpPresenter.getSnatchShow(snatch.getSnatchRecordId());
   }

    @OnClick({R.id.iv_back, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_confirm:
                content=etContent.getText().toString();
                if (picUrlArr.size()==0){
                    waitUpload = (ArrayList<String>) pictureArr.clone();
                    waitUpload.remove(null);
                }
                if (TextUtils.isEmpty(content)){
                    showShortToast(R.string.pls_input_feedback_content);
                }else {
                    if (waitUpload.size()>0){
                        picUrlArr.clear();
                        showLoadingDialog(R.string.upload_picture);
                        new File(Constant.COMPRESS_DIR_PATH).mkdirs();
                        uploadFile();
                    }else {
                        addShowList();
                    }
                }
                break;
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.REQUEST_PICK_PICTURE:
                    ArrayList<String> images = intent.getStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT);
                    addPicture(images);
                    break;
            }
        }
    }
    private void addPicture(List<String> filepath){
        pictureArr.remove(null);
        pictureArr.addAll(filepath);
        if (pictureArr.size()<Constant.MAX_PICK_PICTURE){
            pictureArr.add(null);
        }else {
            pictureArr.subList(0,Constant.MAX_PICK_PICTURE-1);
        }
        pictureAdapter.notifyDataSetChanged();
    }
    public void uploadFile(){
        if (waitUpload.get(0).startsWith("http")){
            picUrlArr.add(waitUpload.get(0));
            waitUpload.remove(0);
            if (waitUpload.size()>0) {
                uploadFile();
            }else {
                closeLoadingDialog();
                addShowList();
            }
            return;
        }
        if (TextUtils.isEmpty(waitUpload.get(0))){
            setUploadFile(null);
            return;
        }
        File file=new File(waitUpload.get(0));
        Luban.with(this)
                .load(file)
                .ignoreBy(300)
                .setTargetDir(Constant.COMPRESS_DIR_PATH)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        mvpPresenter.fileUpload(Constant.BUSSINESSTYPE_RED_PACKET,file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        closeLoadingDialog();
                        showShortToast(e.getMessage());
                    }
                }).launch();
    }
    public void addShowList(){
        if (picUrlArr.size()>0){
            picture=TextUtils.join(",",picUrlArr);
        }else {
            picture=null;
        }
        showLoadingDialog(null);
        mvpPresenter.addSnatchShow(snatch.getSnatchId(),content,picture);
    }
    @Override
    public void setUploadFile(UploadFile data) {
        waitUpload.remove(0);
        if (data!=null){
            picUrlArr.add(data.getUrl());
        }
        if (waitUpload.size() > 0) {
            uploadFile();
        } else {
            closeLoadingDialog();
            addShowList();
        }
    }
    @Override
    public void setShowList(Boolean data) {
        closeLoadingDialog();
        showShortToast(R.string.publish_success);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void getShowList(SnatchShow snatchShow) {
        closeLoadingDialog();
        if (snatchShow!=null){
            tvResult.setText(snatchShow.getContent());
            pictureArr.clear();
            if (snatchShow.getImgs()!=null){
                pictureArr.addAll(snatchShow.getImgs());
            }
            adapter.notifyDataSetChanged();
        }
    }
    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }
}
