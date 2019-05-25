package com.guochuang.mimedia.ui.activity.redbag;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Parcel;
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
import com.dmcbig.mediapicker.PickerActivity;
import com.dmcbig.mediapicker.PickerConfig;
import com.dmcbig.mediapicker.entity.Media;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.mvp.model.AddReqDtoListBean;
import com.guochuang.mimedia.mvp.model.EditRedbagConfig;
import com.guochuang.mimedia.mvp.model.LookVideoResult;
import com.guochuang.mimedia.mvp.model.LuckyConfig;
import com.guochuang.mimedia.mvp.model.ProblemBean;
import com.guochuang.mimedia.mvp.model.RedBagConfig;
import com.guochuang.mimedia.tools.BitmapUtils;
import com.guochuang.mimedia.tools.LogUtil;
import com.guochuang.mimedia.tools.OssManager;
import com.guochuang.mimedia.tools.PrefUtil;
import com.guochuang.mimedia.ui.activity.common.MapPickActivity;
import com.guochuang.mimedia.ui.adapter.PickVideoAdapter;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.Order;
import com.guochuang.mimedia.mvp.model.RedbagTemp;
import com.guochuang.mimedia.mvp.model.UploadFile;
import com.guochuang.mimedia.mvp.presenter.EditRedbagPresenter;
import com.guochuang.mimedia.mvp.view.EditRedbagView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.DialogBuilder;
import com.guochuang.mimedia.tools.GsonUtil;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.pay.AliPay;
import com.guochuang.mimedia.tools.pay.WxPay;
import com.guochuang.mimedia.ui.adapter.PickImageAdapter;
import com.guochuang.mimedia.ui.dialog.PassDialog;
import com.guochuang.mimedia.ui.dialog.PaySelectDialog;
import com.guochuang.mimedia.ui.dialog.SheetDialog;
import com.guochuang.mimedia.view.GridItemDecoration;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class EditRedbagActivity extends MvpActivity<EditRedbagPresenter> implements EditRedbagView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.lin_title)
    LinearLayout linTitle;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.rv_pic)
    RecyclerView rvPic;
    @BindView(R.id.et_word)
    EditText etWord;
    @BindView(R.id.lin_word)
    LinearLayout linWord;
    @BindView(R.id.tv_password_tip)
    TextView tvPasswordTip;
    @BindView(R.id.et_amout)
    EditText etAmout;
    @BindView(R.id.lin_amount)
    LinearLayout linAmount;
    @BindView(R.id.et_count)
    EditText etCount;
    @BindView(R.id.lin_count)
    LinearLayout linCount;
    @BindView(R.id.tv_scope)
    TextView tvScope;
    @BindView(R.id.lin_scope)
    LinearLayout linScope;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.lin_location)
    LinearLayout linLocation;
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
    @BindView(R.id.tv_link)
    TextView tvLink;
    @BindView(R.id.cb_public_password)
    CheckBox cbPublicPassword;
    @BindView(R.id.cb_save_temp)
    CheckBox cbSaveTemp;
    @BindView(R.id.cb_obey_rule)
    CheckBox cbObeyRule;
    @BindView(R.id.tv_rule)
    TextView tvRule;
    @BindView(R.id.btn_add)
    Button btnAdd;

    @BindView(R.id.ll_set_problem)
    LinearLayout mLlSetProblem;
    @BindView(R.id.tv_problem_number)
    TextView mTvProblemNumber;
    ArrayList<String> pictureArr=new ArrayList<>();
    PickImageAdapter pictureAdapter;

    ArrayList<String> videoArr=new ArrayList<>();
    PickVideoAdapter videoAdapter;

    String redPacketType;
    String latitude;
    String longitude;
    String redbagLatitude;
    String redbagLongitude;
    String content;
    String picture;
    String password;
    double money=0d;
    int quantity=0;
    int areaType=0;
    int kilometer=1;
    String url;
    String urlName;
    String wechat;
    String microblog;
    int isPublicPassword=0;
    int isSaveTemplate=0;
    int payType=-1;
    List<String> scopeArr=new ArrayList<>();

    List<String> waitUpload=new ArrayList<>();
    List<String> picUrlArr=new ArrayList<>();

    String redPacketUuid;
    PassDialog passDialog;
    RedBagConfig mRedBagConfig;
    String videoPath;
    String coverUrl;
    String videoUrl;
    String amountStr;

    private ArrayList<ProblemBean> mProblemList = new ArrayList();

    @Override
    protected EditRedbagPresenter createPresenter() {
        return new EditRedbagPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_edit_redbag;
    }

    @Override
    public void initViewAndData() {
        redPacketType=getIntent().getStringExtra(Constant.RED_PACKET_TYPE);
        scopeArr=Arrays.asList(getResources().getStringArray(R.array.redbag_scope));
        tvScope.setText(scopeArr.get(0));
        mLlSetProblem.setVisibility(View.GONE);
        switch (redPacketType){
            case Constant.RED_PACKET_TYPE_RANDOM:
                tvTitle.setText(R.string.random_redbag);
                linWord.setVisibility(View.GONE);
                cbPublicPassword.setVisibility(View.GONE);
                cbSaveTemp.setText(R.string.save_random_temp);
                break;
            case Constant.RED_PACKET_TYPE_PASSWORD:
                tvTitle.setText(R.string.password_redbag);
                linWord.setVisibility(View.VISIBLE);
                cbSaveTemp.setText(R.string.save_password_temp);
                showLoadingDialog(null);
                mvpPresenter.getConfig(Constant.RED_PACKET_TYPE_PASSWORD);
                etAmout.setOnFocusChangeListener(mOnFocusChangeListener);
                etCount.setOnFocusChangeListener(mOnFocusChangeListener);
                break;
            case Constant.RED_PACKET_TYPE_LUCKY:
                tvTitle.setText(R.string.lucky_redbag);
                linWord.setVisibility(View.GONE);
                cbPublicPassword.setVisibility(View.GONE);
                cbSaveTemp.setText(R.string.save_lucky_temp);
                showLoadingDialog(null);
                mvpPresenter.getLuckyConfig();
                break;
            case Constant.RED_PACKET_TYPE_VIDEO:
                tvTitle.setText(R.string.video_redbag);
                linWord.setVisibility(View.GONE);
                cbPublicPassword.setVisibility(View.GONE);
                cbSaveTemp.setText(R.string.save_video_temp);
                mLlSetProblem.setVisibility(View.VISIBLE);
                mTvProblemNumber.setText(getString(R.string.set_problem));
                showLoadingDialog(null);
                mvpPresenter.getConfig(Constant.RED_PACKET_TYPE_SURVEY);
                etAmout.setOnFocusChangeListener(mOnFocusChangeListener);
                etCount.setOnFocusChangeListener(mOnFocusChangeListener);
                break;
            case Constant.RED_PACKET_TYPE_SURVEY:
                tvTitle.setText(R.string.questionnaire_redbag);
                linWord.setVisibility(View.GONE);
                cbPublicPassword.setVisibility(View.GONE);
                cbSaveTemp.setText(R.string.save_problem_temp);
                mLlSetProblem.setVisibility(View.VISIBLE);
                mTvProblemNumber.setText(getString(R.string.set_problem));
                showLoadingDialog(null);
                mvpPresenter.getConfig(Constant.RED_PACKET_TYPE_SURVEY);
                etAmout.setOnFocusChangeListener(mOnFocusChangeListener);
                etCount.setOnFocusChangeListener(mOnFocusChangeListener);
                break;
        }
        latitude=getPref().getLatitude();
        longitude=getPref().getLongitude();
        redbagLatitude=latitude;
        redbagLongitude=longitude;
        tvText.setText(R.string.select_temp);
        rvPic.setLayoutManager(new GridLayoutManager(this,3));
        rvPic.addItemDecoration(new GridItemDecoration(3,CommonUtil.dip2px(this,10),false));
        rvPic.setItemAnimator(new DefaultItemAnimator());
        if (redPacketType.equals(Constant.RED_PACKET_TYPE_VIDEO)){
            videoArr.add(null);
            videoAdapter=new PickVideoAdapter(videoArr);
            videoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (TextUtils.isEmpty(videoArr.get(position))){
                        RxPermissions rxPermissions=new RxPermissions(EditRedbagActivity.this);
                        rxPermissions.request(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean aBoolean) {
                                if (aBoolean) {
                                    Intent intent = new Intent(EditRedbagActivity.this, PickerActivity.class);
                                    intent.putExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_VIDEO);//default image and video (Optional)
                                    long maxSize = 188743680L;//long long long
                                    intent.putExtra(PickerConfig.MAX_SELECT_SIZE, maxSize); //default 180MB (Optional)
                                    intent.putExtra(PickerConfig.MAX_SELECT_COUNT, 1);  //default 40 (Optional)
                                    intent.putExtra(PickerConfig.DEFAULT_SELECTED_LIST, new ArrayList<Media>()); // (Optional)
                                    startActivityForResult(intent, Constant.REQUEST_PICK_VIDEO);
                                }else {
                                    showShortToast(R.string.get_pic_permission);
                                }
                            }
                        });
                    }else {
                        IntentUtils.startVideoPreviewActivity(EditRedbagActivity.this, videoArr.get(position));
                    }
                }
            });
            videoAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    videoArr.remove(position);
                    videoAdapter.notifyItemRemoved(position);
                    if (!videoArr.contains(null)){
                        videoArr.add(null);
                    }
                    videoPath=null;
                    videoUrl=null;
                    adapter.notifyDataSetChanged();
                }
            });
            rvPic.setAdapter(videoAdapter);
        }else {
            pictureArr.add(null);
            pictureAdapter=new PickImageAdapter(pictureArr);
            pictureAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (TextUtils.isEmpty(pictureArr.get(position))){
                        RxPermissions rxPermissions=new RxPermissions(EditRedbagActivity.this);
                        rxPermissions.request(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean aBoolean) {
                                if (aBoolean) {
                                    ImageSelector.builder()
                                            .useCamera(true)
                                            .setSingle(false)
                                            .setMaxSelectCount(Constant.MAX_PICK_PICTURE-pictureArr.size()+1)
                                            .start(EditRedbagActivity.this, Constant.REQUEST_PICK_PICTURE);
                                }else {
                                    showShortToast(R.string.get_pic_permission);
                                }
                            }
                        });
                    }else {
                        ArrayList<String> selectArr=(ArrayList<String>)pictureArr.clone();
                        selectArr.remove(null);
                        IntentUtils.startImagePreviewActivity(EditRedbagActivity.this,position,selectArr);
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
            rvPic.setAdapter(pictureAdapter);
        }


        mvpPresenter.getTemplate(redPacketType);
        mvpPresenter.getEditRedbagConfig();
    }

    public void showPayResult(boolean success,String errmsg){
        if (success){
            finish();
            showShortToast(R.string.publish_success);
            if(TextUtils.equals(redPacketType,Constant.RED_PACKET_TYPE_LUCKY)) {
                IntentUtils.startLuckyActivity(EditRedbagActivity.this,redPacketUuid);
            }
        }else {
            new DialogBuilder(this)
                    .setTitle(R.string.tip)
                    .setMessage(TextUtils.isEmpty(errmsg)?getString(R.string.pay_fail):errmsg)
                    .setPositiveButton(R.string.confirm, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).create().show();
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_text, R.id.tv_link,R.id.ll_set_problem,R.id.lin_scope,R.id.lin_location,R.id.tv_rule, R.id.btn_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_text:
                Intent intent = new Intent(this, TempActivity.class);
                intent.putExtra(Constant.RED_PACKET_TYPE,redPacketType);
                startActivityForResult(intent,Constant.REQUEST_TEMPLATE);
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
            case R.id.ll_set_problem:
                //跳转设问题
                Intent intent1 = new Intent(this, EditRedPackgeProblemActivity.class);
                intent1.putExtra(Constant.PROBLEMLIST_KEY, mProblemList);
                intent1.putExtra(Constant.RED_PACKET_TYPE, redPacketType);
                startActivityForResult(intent1, Constant.REQUEST_EDIT_QUESTION);
                break;
            case R.id.lin_scope:
                SheetDialog sheetDialog=new SheetDialog(this, scopeArr, new SheetDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        switch (position){
                            case 0:
                                areaType=0;
                                kilometer=1;
                                break;
                            case 1:
                                areaType=0;
                                kilometer=3;
                                break;
                            case 2:
                                areaType=0;
                                kilometer=5;
                                break;
                            default:
                                areaType=position-2;
                                kilometer=0;
                                break;
                        }
                        tvScope.setText(scopeArr.get(position));
                    }
                });
                sheetDialog.show();
                break;
            case R.id.lin_location:
                RxPermissions rxPermissions=new RxPermissions(this);
                rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        startActivityForResult(new Intent(EditRedbagActivity.this, MapPickActivity.class),Constant.REQUEST_GET_LOCATION);
                    }
                });
                break;
            case R.id.tv_rule:
                IntentUtils.startWebActivity(this, tvRule.getText().toString(),Constant.URL_SEND_REDBAG);
                break;
            case R.id.btn_add:
                content=etContent.getText().toString().trim();
                if (!redPacketType.equals(Constant.RED_PACKET_TYPE_VIDEO)){
                    if (picUrlArr.size()==0){
                        waitUpload = (ArrayList<String>) pictureArr.clone();
                        waitUpload.remove(null);
                    }
                }
                password=etWord.getText().toString().trim();
                amountStr=etAmout.getText().toString().trim();
                if(TextUtils.isEmpty(amountStr)){
                    money=0d;
                }else {
                    money=Double.parseDouble(amountStr);
                }
//                money=0.01d;
                String countStr=etCount.getText().toString().trim();
                if(TextUtils.isEmpty(countStr)){
                    quantity=0;
                }else {
                    quantity=Integer.parseInt(countStr);
                }

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
                isPublicPassword=cbPublicPassword.isChecked()?1:0;
                isSaveTemplate=cbSaveTemp.isChecked()?1:0;
                if (TextUtils.isEmpty(latitude)||TextUtils.isEmpty(longitude)){
                    showShortToast(R.string.not_location_info);
                }else if(money<=0){
                    showShortToast(R.string.pls_set_amount);
                }else if(quantity<=0){
                    showShortToast(R.string.pls_set_redbag_num);
                }else if(!TextUtils.isEmpty(url)&&TextUtils.isEmpty(urlName)){
                    showShortToast(R.string.link_name_cant_empty);
                }else if(!TextUtils.isEmpty(urlName)&&TextUtils.isEmpty(url)){
                    showShortToast(R.string.link_url_cant_empty);
                }else if(!TextUtils.isEmpty(url)&&!url.matches(Constant.REGEX_WEBURL)){
                    showShortToast(R.string.link_format_error);
                }else if(redPacketType.equals(Constant.RED_PACKET_TYPE_VIDEO)&&TextUtils.isEmpty(videoUrl)&&TextUtils.isEmpty(videoPath)){
                    showShortToast(R.string.pls_select_video);
                }else if(redPacketType.equals(Constant.RED_PACKET_TYPE_SURVEY)&&mProblemList.size()==0){
                    showShortToast(R.string.plese_set_problem);
                }else if(!cbObeyRule.isChecked()){
                    showShortToast(R.string.agree_rule);
                }else if(!checkMoney(money,quantity)){

                }else {
                    if (TextUtils.equals(redPacketType,Constant.RED_PACKET_TYPE_PASSWORD)){
                        if (TextUtils.isEmpty(password)) {
                            showShortToast(R.string.word_not_empty);
                            return;
                        } else if (password.length() < 2 || password.length() > 20) {
                            showShortToast(R.string.word_length_limit);
                            return;
                        }
                    }
                    if (redPacketType.equals(Constant.RED_PACKET_TYPE_VIDEO)){
                        if (TextUtils.isEmpty(videoUrl)){//需要重新上传视频url
                            new File(Constant.COMPRESS_DIR_PATH).mkdirs();
                            showLoadingDialog(R.string.upload_picture);
                            File frameFile = catVideoFrame(new File(videoPath));
                            if (frameFile.exists()) {
                                //上传视频帧画面
                                mvpPresenter.fileUpload(Constant.BUSSINESSTYPE_RED_PACKET, frameFile);
                            }else {
                                closeLoadingDialog();
                                showShortToast(R.string.video_cover_dont_exist);
                            }
                        }else {
                            selectPayType();
                        }
                    }else {
                        if (waitUpload.size()>0){
                            showLoadingDialog(R.string.upload_picture);
                            new File(Constant.COMPRESS_DIR_PATH).mkdirs();
                            uploadFile();
                        }else {
                            selectPayType();
                        }
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
                selectPayType();
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
                        mvpPresenter.fileUpload(Constant.BUSSINESSTYPE_RED_PACKET,file);
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
                case Constant.REQUEST_GET_LOCATION:
                    redbagLatitude=String.valueOf(intent.getDoubleExtra(Constant.LATITUDE,0));
                    redbagLongitude=String.valueOf(intent.getDoubleExtra(Constant.LONGITUDE,0));
                    tvLocation.setText(intent.getStringExtra(Constant.NAME));
                    break;
                case Constant.REQUEST_TEMPLATE:
                    RedbagTemp temp=(RedbagTemp)intent.getSerializableExtra(Constant.TEMPLATE);
                    mvpPresenter.getTemplate(redPacketType);
                    if (temp==null){
                        return;
                    }
                    etContent.setText(temp.getContent());
                    pictureArr.clear();
                    picUrlArr.clear();
                    videoArr.clear();
                      if (!redPacketType.equals(Constant.RED_PACKET_TYPE_VIDEO)){
                          if (TextUtils.isEmpty(temp.getPicture())) {
                              addPicture(new ArrayList<String>());
                          } else {
                              List<String> pics = Arrays.asList(TextUtils.split(temp.getPicture(), ","));
                              addPicture(pics);
                          }
                      }
                    etAmout.setText(String.valueOf((int)temp.getMoney()));
                    etCount.setText(String.valueOf(temp.getQuantity()));
                    areaType=temp.getAreaType();
                    kilometer=temp.getKilometre();
                    if (temp.getAreaType()<scopeArr.size()) {
                        tvScope.setText(scopeArr.get(temp.getAreaType()));
                    }
                    etWord.setText(temp.getPassword());
                    redbagLongitude=temp.getLongitude();
                    redbagLatitude=temp.getLatitude();
                    tvLocation.setText(temp.getCountryName()+temp.getProvinceName()+temp.getCityName()+temp.getDistrictName());
                    etLinkName.setText(temp.getUrlName());
                    etLinkUrl.setText(temp.getUrl());
                    etLinkWechat.setText(temp.getWechat());
                    etLinkWeibo.setText(temp.getMicroblog());
                    if (redPacketType.equals(Constant.RED_PACKET_TYPE_VIDEO)){
                        coverUrl=temp.getCoverUrl();
                        videoUrl=temp.getVideoUrl();
                        List<String> pathArr=new ArrayList<>();
                        pathArr.add(videoUrl);
                        addVideo(pathArr);
                        if (temp.getSurveyId()>0){
                            showLoadingDialog(R.string.loading);
                            mvpPresenter.getProblems(temp.getSurveyId(), temp.getRedPacketUuid());
                        }
                    }else if (redPacketType.equals(Constant.RED_PACKET_TYPE_SURVEY)){
                        if (temp.getSurveyId()>0){
                            showLoadingDialog(R.string.loading);
                            mvpPresenter.getProblems(temp.getSurveyId(), temp.getRedPacketUuid());
                        }
                    }
                    break;
                case Constant.REQUEST_EDIT_QUESTION:
                    ArrayList<ProblemBean> problemlist = intent.getParcelableArrayListExtra(Constant.PROBLEM_LIST);
                    mProblemList.clear();
                    mProblemList.addAll(problemlist);
                    for (ProblemBean bean:mProblemList){
                        LogUtil.d(GsonUtil.GsonString(bean));
                    }

                    mTvProblemNumber.setText(String.format(getString(R.string.set_problem_number), String.valueOf(mProblemList.size())));
                    break;
            }
        }else if(resultCode == PickerConfig.RESULT_CODE){
            if (requestCode == Constant.REQUEST_PICK_VIDEO){
                ArrayList<Media> list= intent.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT);
                List<String> videoList = new ArrayList<>(1);
                for (Media media : list) {
                    videoList.add(media.path);
                    videoPath=media.path;
                    videoUrl=null;
                }
                addVideo(videoList);
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
    private void addVideo(List<String> filepath){
        videoArr.remove(null);
        videoArr.addAll(filepath);
        if (videoArr.size()<Constant.MAX_PICK_VIDEO){
            videoArr.add(null);
        }else {
            videoArr.subList(0,Constant.MAX_PICK_VIDEO-1);
        }
        videoAdapter.notifyDataSetChanged();
    }
    public void selectPayType(){
        picture=TextUtils.join(",",picUrlArr);
        final PaySelectDialog paySelectDialog = new PaySelectDialog(this,amountStr);
        paySelectDialog.setOnResultListener(new PaySelectDialog.OnResultListener() {
            @Override
            public void onSelectItem(int postion) {
                if (postion == 0) {
                    payType = Constant.PAY_TYPE_WXPAY;
                } else if (postion == 1) {
                    payType = Constant.PAY_TYPE_ALIPAY;
                } else {
                    payType = Constant.PAY_TYPE_KSB;
                    passDialog =new PassDialog(EditRedbagActivity.this, new PassDialog.OnPassDialogListener() {
                        @Override
                        public void close() {
                            selectPayType();
                        }

                        @Override
                        public void go() {

                        }

                        @Override
                        public void onNumFull(String code) {
                            startPay(code);
                        }
                    });
                    passDialog.show();

                    return;
                }
                startPay(null);
            }
        });
        paySelectDialog.show();
    }
    public void startPay(String safetyCode){
        showLoadingDialog(null);
        if (TextUtils.equals(redPacketType,Constant.RED_PACKET_TYPE_RANDOM)){
            mvpPresenter.addRandomRedbag(latitude,longitude,redbagLatitude,redbagLongitude,content,picture,areaType,kilometer,money,quantity,urlName,url,wechat,microblog,isPublicPassword,isSaveTemplate,payType,Constant.CHANNEL_CODE_ANDROID,safetyCode);
        }else if(TextUtils.equals(redPacketType,Constant.RED_PACKET_TYPE_PASSWORD)){
            mvpPresenter.addPasswordRedbag(latitude,longitude,redbagLatitude,redbagLongitude,content,picture,areaType,kilometer,password,money,quantity,urlName,url,wechat,microblog,isPublicPassword,isSaveTemplate,payType,Constant.CHANNEL_CODE_ANDROID,safetyCode);
        }else if(TextUtils.equals(redPacketType,Constant.RED_PACKET_TYPE_LUCKY)){
            mvpPresenter.addLuckyRedbag(latitude,longitude,redbagLatitude,redbagLongitude,content,picture,areaType,kilometer,money,quantity,urlName,url,wechat,microblog,isPublicPassword,isSaveTemplate,payType,Constant.CHANNEL_CODE_ANDROID,safetyCode);
        } else if (TextUtils.equals(redPacketType, Constant.RED_PACKET_TYPE_VIDEO)) {
            mvpPresenter.addVideoReabag(latitude, longitude, redbagLatitude, redbagLongitude, content, areaType, kilometer, money, quantity, urlName, url, wechat, microblog, isPublicPassword, isSaveTemplate, payType, Constant.CHANNEL_CODE_ANDROID, safetyCode, joinProblmeJson(), videoUrl,coverUrl);
        } else if (TextUtils.equals(redPacketType, Constant.RED_PACKET_TYPE_SURVEY)) {
            mvpPresenter.addSurveyReabag(latitude, longitude, redbagLatitude, redbagLongitude, content, picture, areaType, kilometer, money, quantity, urlName, url, wechat, microblog, isPublicPassword, isSaveTemplate, payType, Constant.CHANNEL_CODE_ANDROID, safetyCode, joinProblmeJson());
        }
    }
    View.OnFocusChangeListener mOnFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                //失去焦点的时候
                String amoutText = etAmout.getText().toString().trim();
                String countText = etCount.getText().toString().trim();

                if (TextUtils.isEmpty(amoutText) || TextUtils.isEmpty(countText)) {
                    return;
                }
                try {
                    double amout = Double.valueOf(amoutText);
                    int count = Integer.valueOf(countText);
                    checkMoney(amout,count);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    };
    /**
     *
     * @param amount
     * @param count
     */
    public boolean checkMoney(double amount,int count) {
        if(mRedBagConfig == null) {
            return true;
        }
        double minMoney=0;
        double oneMinMoney=0;
        double perMinMoney=amount/count;
        if (Constant.RED_PACKET_TYPE_PASSWORD.equals(redPacketType)) {
            minMoney=mRedBagConfig.getPasswordMinMoney();
            oneMinMoney=mRedBagConfig.getPasswordMinOneMoney();
        }else {
            minMoney=mRedBagConfig.getSurveyMinMoney();
            oneMinMoney=mRedBagConfig.getSurveyMinOneMoney();
        }

        if (amount<minMoney){
            showShortToast(getString(R.string.redpacke_tip_money_str) + minMoney);
            return false;
        }else if (perMinMoney<oneMinMoney){
            showShortToast(getString(R.string.redpacke_tip_one_money_str) + oneMinMoney);
            return false;
        }
        return true;
    }
    /**
     * 截取视频帧画面
     *
     * @param file
     */
    private File catVideoFrame(File file) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        //设置数据源为该文件对象指定的绝对路径
        mmr.setDataSource(file.getAbsolutePath());
        //获得视频第一帧的Bitmap对象
        Bitmap bitmap = mmr.getFrameAtTime();
        String filepngname = Constant.COMMON_PATH + File.separator + file.getName().substring(0, file.getName().indexOf(".")) + ".png";
        BitmapUtils.savePNG(bitmap, filepngname);
        return new File(filepngname);
    }
    /**
     * 拼接joinProblmeJson
     */
    private List<AddReqDtoListBean> joinProblmeJson() {
        List<AddReqDtoListBean> addReqDtoListBeanArr = new ArrayList<>();
        if (mProblemList.isEmpty()) {
            return addReqDtoListBeanArr;
        }
        try {
            for (int i = 0; i < mProblemList.size(); i++) {
                AddReqDtoListBean addReqDtoListBean = new AddReqDtoListBean();
                List<AddReqDtoListBean.OptionsListBean> listBeans = new ArrayList<>();
                ProblemBean problemBean = mProblemList.get(i);
                ArrayList<ProblemBean.ItemBean> item = problemBean.getItem();
                for (int j = 0; j < item.size(); j++) {
                    AddReqDtoListBean.OptionsListBean bean = new AddReqDtoListBean.OptionsListBean();
                    ProblemBean.ItemBean itemBean = item.get(j);
                    bean.setOptionName(problemBean.getType() == 2 ? "" : itemBean.getItemname());
                    bean.setOptionValue(itemBean.getItemcontent());
                    bean.setIsAnswer(problemBean.getType() == 2 ? 1 : itemBean.isIsanswer() ? 1 : 0);
                    bean.setSequence(j);
                    listBeans.add(bean);
                }

                addReqDtoListBean.setTitle(problemBean.getProblem());
                addReqDtoListBean.setType(problemBean.getType());
                addReqDtoListBean.setSequence(i + 1);
                addReqDtoListBean.setOptionsList(listBeans);
                addReqDtoListBeanArr.add(addReqDtoListBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addReqDtoListBeanArr;
    }
    @Override
    public void setData(final String data) {
        closeLoadingDialog();
        if (passDialog!=null&&passDialog.isShowing()){
            passDialog.dismiss();
        }
        if (!TextUtils.isEmpty(data)) {
            Order order=GsonUtil.GsonToBean(data,Order.class);
            redPacketUuid=order.getRedPacketUuid();
            switch (payType){
                case  Constant.PAY_TYPE_WXPAY:
                    if (TextUtils.isEmpty(order.getVendorResponse())){
                        showShortToast(R.string.can_get_order);
                        return;
                    }
                    WxPay.getInstance().pay(order.getVendorResponse(), new WxPay.OnResultListener() {
                        @Override
                        public void onResult(boolean success, String errMsg) {
                            showPayResult(success,errMsg);
                        }
                    });
                    break;
                case  Constant.PAY_TYPE_ALIPAY:
                    if (TextUtils.isEmpty(order.getVendorResponse())){
                        showShortToast(R.string.can_get_order);
                        return;
                    }
                    AliPay.getInstance().pay(this, order.getVendorResponse(), new AliPay.OnResultListener() {
                        @Override
                        public void onResult(boolean success, String errMsg) {
                            showPayResult(success,errMsg);
                        }
                    });
                    break;
                case  Constant.PAY_TYPE_KSB:
                    showPayResult(true,null);
                    break;
            }
        }else {
            showShortToast(R.string.can_get_order);
        }
    }

    @Override
    public void setUploadFile(UploadFile data) {
        if (redPacketType.equals(Constant.RED_PACKET_TYPE_VIDEO)){
            coverUrl=data.getUrl();
            closeLoadingDialog();

            showLoadingDialog(R.string.upload_video);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OssManager ossManager= new OssManager();
                    ossManager.uploadVideo(videoPath,new OssManager.OnResultListener(){

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onProgress(int progress) {
                        }

                        @Override
                        public void onSuccess(final String url) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    closeLoadingDialog();
                                    if (!TextUtils.isEmpty(url)){
                                        videoUrl=url;
                                        selectPayType();
                                    }
                                }
                            });
                        }

                        @Override
                        public void onFail(String code, final String errmsg) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    closeLoadingDialog();
                                    showShortToast(errmsg);
                                }
                            });

                        }
                    });
                }
            }).start();

        }else {
            waitUpload.remove(0);
            picUrlArr.add(data.getUrl());
            if (waitUpload.size()>0) {
                uploadFile();
            }else {
                closeLoadingDialog();
                selectPayType();
            }
        }

    }
    @Override
    public void setUploadfail(String msg) {
        showShortToast(R.string.upload_picture_fail);
        closeLoadingDialog();
    }
    @Override
    public void setTempData(List<RedbagTemp> data) {
        if (data!=null) {
            if (data.size() >= 5) {
                cbSaveTemp.setVisibility(View.GONE);
            } else {
                cbSaveTemp.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void setLuckyConfig(LuckyConfig data) {
        closeLoadingDialog();
        if (data!=null){
            etAmout.setText(String.valueOf(data.getJoinMoney()));
            etAmout.setEnabled(false);
            etCount.setText(String.valueOf(data.getSendQuantity()));
            etCount.setEnabled(false);
        }
    }

    @Override
    public void setConfig(EditRedbagConfig data) {
        if (data!=null){
            tvPasswordTip.setText(data.getPasswordOutTime());
        }
    }

    @Override
    public void setProblems(LookVideoResult data) {
        closeLoadingDialog();
        if (data!=null) {
            mProblemList.clear();
            List<LookVideoResult.QuestionListBean> questionList = data.getQuestionList();
            for (int index = 0; index < questionList.size(); index++) {
                ProblemBean problemBean = new ProblemBean(Parcel.obtain());
                LookVideoResult.QuestionListBean questionListBean = questionList.get(index);

                problemBean.setProblem(questionListBean.getTitle());
                problemBean.setType(questionListBean.getType());

                List<LookVideoResult.QuestionListBean.OptionsListBean> optionsList = questionListBean.getOptionsList();
                ArrayList<ProblemBean.ItemBean> items = new ArrayList<>();
                if (optionsList != null) {
                    for (int option = 0; option < optionsList.size(); option++) {
                        LookVideoResult.QuestionListBean.OptionsListBean optionsListBean = optionsList.get(option);
                        ProblemBean.ItemBean itemBean = new ProblemBean.ItemBean(Parcel.obtain());
                        itemBean.setIsanswer(optionsListBean.getIsAnswer() == 0 ? false : true);
                        itemBean.setProblemType(questionListBean.getType());
                        itemBean.setItemcontent(optionsListBean.getOptionValue());
                        itemBean.setItemname(optionsListBean.getOptionName());
                        items.add(itemBean);
                    }
                    problemBean.setItem(items);
                    mProblemList.add(problemBean);
                }
            }
            mTvProblemNumber.setText(String.format(getString(R.string.set_problem_number), String.valueOf(mProblemList.size())));
        }

    }

    @Override
    public void checkConfigSuccess(RedBagConfig data) {
        closeLoadingDialog();
        if (data!=null) {
            mRedBagConfig = data;
        }
    }
    @Override
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
        if (passDialog!=null&&passDialog.isShowing()){
            passDialog.clearCode();
        }
    }




}
