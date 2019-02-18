package com.guochuang.mimedia.ui.activity.redbag;

import android.Manifest;
import android.content.Intent;
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
import com.guochuang.mimedia.mvp.model.LuckyConfig;
import com.guochuang.mimedia.ui.activity.MapPickActivity;
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
    ArrayList<String> pictureArr=new ArrayList<>();
    PickImageAdapter pictureAdapter;

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
                break;
            case Constant.RED_PACKET_TYPE_LUCKY:
                tvTitle.setText(R.string.lucky_redbag);
                linWord.setVisibility(View.GONE);
                cbPublicPassword.setVisibility(View.GONE);
                cbSaveTemp.setText(R.string.save_lucky_temp);
                showLoadingDialog(null);
                mvpPresenter.getLuckyConfig();
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
        mvpPresenter.getTemplate(redPacketType);
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

    @OnClick({R.id.iv_back, R.id.tv_text, R.id.tv_link,R.id.lin_scope,R.id.lin_location,R.id.tv_rule, R.id.btn_add})
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
                if (picUrlArr.size()==0){
                    waitUpload = (ArrayList<String>) pictureArr.clone();
                    waitUpload.remove(null);
                }
                password=etWord.getText().toString().trim();
                String amountStr=etAmout.getText().toString().trim();
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
                }else if(!cbObeyRule.isChecked()){
                    showShortToast(R.string.agree_rule);
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
                    if (waitUpload.size()>0){
                        showLoadingDialog(R.string.upload_picture);
                        new File(Constant.COMPRESS_DIR_PATH).mkdirs();
                        uploadFile();
                    }else {
                        selectPayType();
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
                    if (TextUtils.isEmpty(temp.getPicture())){
                        addPicture(new ArrayList<String >());
                    }else {
                        List<String> pics=Arrays.asList(TextUtils.split(temp.getPicture(),","));
                        addPicture(pics);
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
                    if (!TextUtils.isEmpty(temp.getUrlName())||!TextUtils.isEmpty(temp.getUrlName())||!TextUtils.isEmpty(temp.getWechat())||!TextUtils.isEmpty(temp.getMicroblog())){
                        linLink.setVisibility(View.VISIBLE);
                        tvLink.setText(R.string.pack_up);
                    }else {
                        linLink.setVisibility(View.GONE);
                        tvLink.setText(R.string.more);
                    }
                    etLinkName.setText(temp.getUrlName());
                    etLinkUrl.setText(temp.getUrl());
                    etLinkWechat.setText(temp.getWechat());
                    etLinkWeibo.setText(temp.getMicroblog());
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
   public void selectPayType(){
       picture=TextUtils.join(",",picUrlArr);
       PaySelectDialog paySelectDialog = new PaySelectDialog(this, String.valueOf(money));
       paySelectDialog.setOnResultListener(new PaySelectDialog.OnResultListener() {
           @Override
           public void onSelectItem(int postion) {
               if (postion == 0) {
                   payType = Constant.PAY_TYPE_WXPAY;
               } else if (postion == 1) {
                   payType = Constant.PAY_TYPE_ALIPAY;
               } else {
                   payType = Constant.PAY_TYPE_KSB;
                   new PassDialog(EditRedbagActivity.this, new PassDialog.OnPassDialogListener() {
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
                   }).show();
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
    }
}
    @Override
    public void setData(final String data) {
        closeLoadingDialog();
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
        waitUpload.remove(0);
        picUrlArr.add(data.getUrl());
        if (waitUpload.size()>0) {
            uploadFile();
        }else {
            closeLoadingDialog();
            selectPayType();
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
    public void setError(String msg) {
        closeLoadingDialog();
        showShortToast(msg);
    }


}
