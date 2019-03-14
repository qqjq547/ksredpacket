package com.guochuang.mimedia.ui.activity.beenest;

import android.Manifest;
import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.NestAd;
import com.guochuang.mimedia.mvp.model.NestInfoLimit;
import com.guochuang.mimedia.mvp.model.NestTemplate;
import com.guochuang.mimedia.mvp.model.UploadFile;
import com.guochuang.mimedia.mvp.presenter.EditAdPresenter;
import com.guochuang.mimedia.mvp.view.EditAdView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.guochuang.mimedia.ui.activity.common.MapPickActivity;
import com.guochuang.mimedia.ui.adapter.PickImageAdapter;
import com.guochuang.mimedia.ui.dialog.BeeTempDialog;
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

public class EditAdActivity extends MvpActivity<EditAdPresenter> implements EditAdView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.et_shortmsg)
    EditText etShortmsg;
    @BindView(R.id.iv_picture)
    ImageView ivPicture;
    @BindView(R.id.lin_picture)
    LinearLayout linPicture;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.rv_picture)
    RecyclerView rvPicture;
    @BindView(R.id.et_ad_title)
    EditText etAdTitle;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.tv_link)
    TextView tvLink;
    @BindView(R.id.et_link_name)
    EditText etLinkName;
    @BindView(R.id.et_link_url)
    EditText etLinkUrl;
    @BindView(R.id.et_link_wechat)
    EditText etLinkWechat;
    @BindView(R.id.et_link_weibo)
    EditText etLinkWeibo;
    @BindView(R.id.lin_link)
    LinearLayout linLink;
    @BindView(R.id.cb_save_temp)
    CheckBox cbSaveTemp;
    @BindView(R.id.iv_temp_tip)
    ImageView ivTempTip;
    @BindView(R.id.cb_obey_rule)
    CheckBox cbObeyRule;
    @BindView(R.id.tv_rule)
    TextView tvRule;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.nsv_content)
    NestedScrollView nsvContent;

    long nestInfoId;
    long nestLocationId;
    long nestSuccessId;
    String shortMsg;
    String iconUrl;
    String content;
    String picture;
    String adTitle;
    String adMobile;
    String adArea;
    String adAddress;
    String latitude;
    String longitude;

    String url;
    String urlName;
    String wechat;
    String microblog;
    int isSaveTemplate=0;

    int maxPicture=Constant.MAX_PICK_PICTURE;
    int maxTemplate=5;
    int currentTemplateCount=0;
    List<String> waitUpload=new ArrayList<>();
    List<String> picUrlArr=new ArrayList<>();
    ArrayList<String> pictureArr=new ArrayList<>();
    PickImageAdapter pictureAdapter;
    boolean isUpdate=false;

    @Override
    protected EditAdPresenter createPresenter() {
        return new EditAdPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_edit_ad;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.edit_ad);
        tvText.setText(R.string.select_temp);
        nestInfoId=getIntent().getLongExtra(Constant.NESTINFOID,0);
        nestLocationId=getIntent().getLongExtra(Constant.NESTLOCATIONID,0);
        nestSuccessId=getIntent().getLongExtra(Constant.NESTSUCCESSID,0);
        isUpdate=getIntent().getBooleanExtra(Constant.NESTUPDATE,false);
        rvPicture.setLayoutManager(new GridLayoutManager(this,3));
        rvPicture.addItemDecoration(new GridItemDecoration(3,CommonUtil.dip2px(this,10),false));
        rvPicture.setItemAnimator(new DefaultItemAnimator());
        pictureArr.add(null);
        pictureAdapter=new PickImageAdapter(pictureArr);
        pictureAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (TextUtils.isEmpty(pictureArr.get(position))){
                    RxPermissions rxPermissions=new RxPermissions(EditAdActivity.this);
                    rxPermissions.request(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean aBoolean) {
                            if (aBoolean) {
                                ImageSelector.builder()
                                        .useCamera(true)
                                        .setSingle(false)
                                        .setMaxSelectCount(Constant.MAX_PICK_PICTURE-pictureArr.size()+1)
                                        .start(EditAdActivity.this, Constant.REQUEST_PICK_PICTURE);
                            }else {
                                showShortToast(R.string.get_pic_permission);
                            }
                        }
                    });
                }else {
                    ArrayList<String> selectArr=(ArrayList<String>)pictureArr.clone();
                    selectArr.remove(null);
                    IntentUtils.startImagePreviewActivity(EditAdActivity.this,position,selectArr);
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
        mvpPresenter.nesteLimit();
        mvpPresenter.nesteTemplateCount();
        if (isUpdate) {
            showLoadingDialog(null);
            mvpPresenter.getNestAd(nestInfoId, Constant.AD_TYPE_EDIT);
        }

    }

    @OnClick({R.id.iv_back, R.id.tv_text,R.id.lin_picture, R.id.tv_area,R.id.tv_link, R.id.tv_rule,R.id.iv_temp_tip,R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_text:
                startActivityForResult(new Intent(this,BeeNestTempActivity.class),Constant.REQUEST_TEMPLATE);
                break;
            case R.id.lin_picture:
                RxPermissions rxPermissions=new RxPermissions(EditAdActivity.this);
                rxPermissions.request(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            ImageSelector.builder()
                                    .useCamera(true)
                                    .setSingle(true)
                                    .setCrop(true)
                                    .start(EditAdActivity.this, Constant.REQUEST_PICK_ICON);
                        }else {
                            showShortToast(R.string.get_pic_permission);
                        }
                    }
                });
                break;
            case R.id.tv_area:
                new RxPermissions(this).request(Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        startActivityForResult(new Intent(EditAdActivity.this, MapPickActivity.class),Constant.REQUEST_GET_LOCATION);
                    }
                });
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
            case R.id.iv_temp_tip:
                new BeeTempDialog(this).show();
                break;
            case R.id.tv_rule:
                IntentUtils.startWebActivity(this,null,Constant.URL_FENGCHAO_BANNER);
                break;
            case R.id.btn_confirm:
                if (picUrlArr.size()==0){
                    waitUpload = (ArrayList<String>) pictureArr.clone();
                    waitUpload.remove(null);
                }
                shortMsg=etShortmsg.getText().toString().trim();
                content=etContent.getText().toString().trim();
                adTitle=etAdTitle.getText().toString().trim();
                adMobile=etMobile.getText().toString().trim();
                adArea=tvArea.getText().toString().trim();
                adAddress=etAddress.getText().toString().trim();
                if (linLink.getVisibility()==View.VISIBLE) {
                    urlName=etLinkName.getText().toString().trim();
                    url = etLinkUrl.getText().toString().trim();
                    wechat=etLinkWechat.getText().toString().trim();
                    microblog=etLinkWeibo.getText().toString().trim();
                }else {
                    urlName=null;
                    url = null;
                    wechat=null;
                    microblog=null;
                }
                isSaveTemplate=cbSaveTemp.isChecked()?1:0;
                if (TextUtils.isEmpty(shortMsg)){
                    showShortToast(R.string.not_location_info);
                }else if(TextUtils.isEmpty(iconUrl)){
                    showShortToast(R.string.brand_icon_cant_empty);
                }else if(TextUtils.isEmpty(content)){
                    showShortToast(R.string.content_cant_empty);
                }else if(TextUtils.isEmpty(adTitle)){
                    showShortToast(R.string.title_cant_empty);
                }else if(!TextUtils.isEmpty(adAddress)&&TextUtils.isEmpty(adArea)){
                    showShortToast(R.string.navigation_not_empty);
                }else if(!TextUtils.isEmpty(url)&&TextUtils.isEmpty(urlName)){
                    showShortToast(R.string.link_name_cant_empty);
                }else if(!TextUtils.isEmpty(urlName)&&TextUtils.isEmpty(url)){
                    showShortToast(R.string.link_url_cant_empty);
                }else if(!TextUtils.isEmpty(url)&&!url.matches(Constant.REGEX_WEBURL)){
                    showShortToast(R.string.link_format_error);
                }else if(!cbObeyRule.isChecked()) {
                    showShortToast(R.string.agree_agreement);
                }else {
                    if (waitUpload.size()>0){
                        showLoadingDialog(R.string.upload_picture);
                        new File(Constant.COMPRESS_DIR_PATH).mkdirs();
                        uploadFile();
                    }else {
                        editNestAd();
                    }
                }
                break;
        }
    }
    public void uploadFile(){
        //如果是网络地址，则直接不上传,直接添加
        if (waitUpload.get(0).startsWith("http")){
            picUrlArr.add(waitUpload.get(0));
            waitUpload.remove(0);
            if (waitUpload.size()>0) {
                uploadFile();
            }else {
                closeLoadingDialog();
                //上传图片结束，开始提交表单
                editNestAd();
            }
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
                        mvpPresenter.fileUpload(Constant.BUSSINESSTYPE_RED_PACKET,file,false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        closeLoadingDialog();
                        showShortToast(e.getMessage());
                    }
                }).launch();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.REQUEST_PICK_PICTURE:
                    ArrayList<String> images = intent.getStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT);
                    addPicture(images);
                    break;
                case Constant.REQUEST_PICK_ICON:
                    ArrayList<String> iconArr= intent.getStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT);
                    String path = iconArr.get(0);
                    showLoadingDialog(null);
                    mvpPresenter.fileUpload(Constant.BUSSINESSTYPE_RED_AVATAR, new File(path),true);
                    break;
                case Constant.REQUEST_GET_LOCATION:
                    latitude=String.valueOf(intent.getDoubleExtra(Constant.LATITUDE,0));
                    longitude=String.valueOf(intent.getDoubleExtra(Constant.LONGITUDE,0));
                    tvArea.setText(intent.getStringExtra(Constant.NAME));
                    if (TextUtils.isEmpty(intent.getStringExtra(Constant.NAME))){
                        etAddress.setText(null);
                    }
                    break;
                case Constant.REQUEST_TEMPLATE:
                    NestTemplate temp=(NestTemplate)intent.getSerializableExtra(Constant.TEMPLATE);
                    mvpPresenter.nesteTemplateCount();
                    if (temp==null){
                        return;
                    }
                    etShortmsg.setText(temp.getShortMsg());
                    iconUrl=temp.getCoverPicture();
                    GlideImgManager.loadImage(this,temp.getCoverPicture(),ivPicture);
                    pictureArr.clear();
                    picUrlArr.clear();
                    if (temp.getPictureList()==null){
                        addPicture(new ArrayList<String >());
                    }else {
                        addPicture(temp.getPictureList());
                    }
                    etContent.setText(temp.getIntroduction());
                    etAdTitle.setText(temp.getTitle());
                    etMobile.setText(temp.getContactPhone());
                    tvArea.setText(temp.getAddress());
                    etAddress.setText(temp.getAddressDetail());
                    latitude=temp.getAddressLat();
                    longitude=temp.getAddressLng();
                    etLinkName.setText(temp.getLinkText());
                    etLinkUrl.setText(temp.getLinkUrl());
                    etLinkWechat.setText(temp.getWechat());
                    etLinkWeibo.setText(temp.getWeibo());
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
    public void editNestAd(){
        showLoadingDialog(null);
        picture=TextUtils.join(",",picUrlArr);
        mvpPresenter.editNestAd(nestInfoId,nestLocationId,nestSuccessId,shortMsg,iconUrl,content,picture,adTitle,adMobile,adArea,adAddress
        ,latitude,longitude,urlName,url,wechat,microblog,isSaveTemplate);
    }

    @Override
    public void setAdData(NestAd data) {
        closeLoadingDialog();
        if (data!=null){
            etShortmsg.setText(data.getShortMsg());
            iconUrl=data.getCoverPicture();
            GlideImgManager.loadImage(this,data.getCoverPicture(),ivPicture);
            pictureArr.clear();
            picUrlArr.clear();
            if (data.getPictureList()==null){
                addPicture(new ArrayList<String >());
            }else {
                addPicture(data.getPictureList());
            }
            etContent.setText(data.getIntroduction());
            etAdTitle.setText(data.getTitle());
            etMobile.setText(data.getContactPhone());
            tvArea.setText(data.getAddress());
            etAddress.setText(data.getAddressDetail());
            latitude=String.valueOf(data.getAddressLat());
            longitude=String.valueOf(data.getAddressLng());
            etLinkName.setText(data.getLinkText());
            etLinkUrl.setText(data.getLinkUrl());
            etLinkWechat.setText(data.getWechat());
            etLinkWeibo.setText(data.getWeibo());
        }
    }

    @Override
    public void setData(Boolean data) {
        closeLoadingDialog();
        showShortToast(R.string.publish_success);
        setResult(RESULT_OK,getIntent());
        finish();
    }

    @Override
    public void setIcon(UploadFile data) {
        closeLoadingDialog();
        iconUrl=data.getUrl();
        GlideImgManager.loadImage(this,iconUrl,ivPicture);
    }

    @Override
    public void setUploadFile(UploadFile data) {
        waitUpload.remove(0);
        picUrlArr.add(data.getUrl());
        if (waitUpload.size()>0) {
            uploadFile();
        }else {
            closeLoadingDialog();
            editNestAd();
        }
    }

    @Override
    public void setUploadfail(String msg) {
        closeLoadingDialog();
        showShortToast(R.string.upload_picture_fail);

    }

    @Override
    public void setLimit(NestInfoLimit data) {
        maxPicture=data.getPictureCount();
        maxTemplate=data.getTemplateCount();
        setTempState();
    }

    @Override
    public void setTemplateCount(Integer data) {
        currentTemplateCount=data;
        setTempState();
    }
    public void setTempState(){
        if (currentTemplateCount<maxTemplate){
            cbSaveTemp.setVisibility(View.VISIBLE);
        }else {
            cbSaveTemp.setVisibility(View.GONE);
        }
    }

    @Override
    public void setError(String msg) {
     closeLoadingDialog();
     showShortToast(msg);
    }
}
