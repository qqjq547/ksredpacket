package com.guochuang.mimedia.ui.activity.city;

import android.Manifest;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.guochuang.mimedia.mvp.model.BoardDetail;
import com.guochuang.mimedia.mvp.presenter.EditBoardkPresenter;
import com.guochuang.mimedia.mvp.view.EditBoardView;
import com.guochuang.mimedia.tools.IntentUtils;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.UploadFile;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.ui.adapter.PickImageAdapter;
import com.guochuang.mimedia.view.GridItemDecoration;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class EditBoardActivity extends MvpActivity<EditBoardkPresenter> implements EditBoardView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.rv_picture)
    RecyclerView rvPicture;
    @BindView(R.id.lin_link)
    LinearLayout linLink;
    @BindView(R.id.tv_link)
    TextView tvLink;
    @BindView(R.id.et_link_name)
    EditText etLinkName;
    @BindView(R.id.et_link_url)
    EditText etLinkUrl;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    String picture;
    String content;
    ArrayList<String> pictureArr=new ArrayList<>();
    PickImageAdapter pictureAdapter;
    List<String> waitUpload=new ArrayList<>();
    List<String> picUrlArr=new ArrayList<>();
    String urlName;
    String url;
    long regionId;
    BoardDetail detail;
    @Override
    protected EditBoardkPresenter createPresenter() {
        return new EditBoardkPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_edit_board;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.public_board);
        regionId=getIntent().getLongExtra(Constant.REGIONID,0);
        rvPicture.setLayoutManager(new GridLayoutManager(this,3));
        rvPicture.addItemDecoration(new GridItemDecoration(3,CommonUtil.dip2px(this,10),false));
        rvPicture.setItemAnimator(new DefaultItemAnimator());
        pictureArr.add(null);
        pictureAdapter=new PickImageAdapter(pictureArr,null);
        pictureAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (TextUtils.isEmpty(pictureArr.get(position))){
                    RxPermissions rxPermissions=new RxPermissions(EditBoardActivity.this);
                    rxPermissions.request(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean aBoolean) {
                            if (aBoolean) {
                                ImageSelector.builder()
                                        .useCamera(true)
                                        .setSingle(false)
                                        .setMaxSelectCount(Constant.MAX_PICK_PICTURE-pictureArr.size()+1)
                                        .start(EditBoardActivity.this, Constant.REQUEST_PICK_PICTURE);
                            }else {
                                showShortToast(R.string.get_pic_permission);
                            }
                        }
                    });
                }else {
                    ArrayList<String> selectArr=(ArrayList<String>)pictureArr.clone();
                    selectArr.remove(null);
                    IntentUtils.startImagePreviewActivity(EditBoardActivity.this,position,selectArr);
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
        showLoadingDialog(null);
        mvpPresenter.getRegioNotice(regionId);
    }
    @OnClick({R.id.iv_back,R.id.tv_link, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_link:
                if (linLink.getVisibility()==View.GONE){
                    linLink.setVisibility(View.VISIBLE);
                    tvLink.setText(R.string.pack_up);
                }else {
                    linLink.setVisibility(View.GONE);
                    tvLink.setText(R.string.more);
                }
                break;
            case R.id.tv_confirm:
                content=etContent.getText().toString();
                if (linLink.getVisibility()==View.VISIBLE) {
                    urlName=etLinkName.getText().toString().trim();
                    url = etLinkUrl.getText().toString().trim();
                }else {
                    urlName=null;
                    url = null;
                }
                if (picUrlArr.size()==0){
                    waitUpload = (ArrayList<String>) pictureArr.clone();
                    waitUpload.remove(null);
                }
                if (TextUtils.isEmpty(content)){
                    showShortToast(R.string.pls_input_feedback_content);
                }else if(!TextUtils.isEmpty(url)&&TextUtils.isEmpty(urlName)){
                    showShortToast(R.string.link_name_cant_empty);
                }else if(!TextUtils.isEmpty(urlName)&&TextUtils.isEmpty(url)){
                    showShortToast(R.string.link_url_cant_empty);
                }else if(!TextUtils.isEmpty(url)&&!url.matches(Constant.REGEX_WEBURL)){
                    showShortToast(R.string.link_format_error);
                }else {
                    if (waitUpload.size()>0){
                        picUrlArr.clear();
                        showLoadingDialog(R.string.upload_picture);
                        new File(Constant.COMPRESS_DIR_PATH).mkdirs();
                        uploadFile();
                    }else {
                        addNotice();
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
    public void addNotice(){
        if (picUrlArr.size()>0){
            picture=TextUtils.join(",",picUrlArr);
        }else {
            picture=null;
        }
        showLoadingDialog(null);
        if (detail==null){
            mvpPresenter.addRegioNotice(regionId,content,picture,urlName,url);
        }else {
            mvpPresenter.updateRegioNotice(regionId,content,picture,urlName,url);
        }
    }
    public void uploadFile(){
        if (waitUpload.get(0).startsWith("http")){
            picUrlArr.add(waitUpload.get(0));
            waitUpload.remove(0);
            if (waitUpload.size()>0) {
                uploadFile();
            }else {
                closeLoadingDialog();
                addNotice();
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
            addNotice();
        }
    }

    @Override
    public void setNotice(Boolean data) {
        closeLoadingDialog();
        showShortToast(R.string.setting_success);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void getNotice(BoardDetail detail) {
        closeLoadingDialog();
        if (detail!=null){
            this.detail=detail;
            etContent.setText(detail.getContent());
            pictureArr.clear();
            picUrlArr.clear();
            if (detail.getPicture()==null||detail.getPicture().size()==0){
                addPicture(new ArrayList<String >());
            }else {
                addPicture(detail.getPicture());
            }
            if (!TextUtils.isEmpty(detail.getUrlName())||!TextUtils.isEmpty(detail.getUrlName())){
                linLink.setVisibility(View.VISIBLE);
                tvLink.setText(R.string.pack_up);
            }else {
                linLink.setVisibility(View.GONE);
                tvLink.setText(R.string.more);
            }
            etLinkName.setText(detail.getUrlName());
            etLinkUrl.setText(detail.getUrl());
        }
    }

    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }
}
