package com.guochuang.mimedia.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.guochuang.mimedia.http.retrofit.ApiCallback;
import com.guochuang.mimedia.http.retrofit.ApiClient;
import com.guochuang.mimedia.mvp.model.SlideVerifyData;
import com.guochuang.mimedia.mvp.presenter.SlideVerifyPresenter;
import com.guochuang.mimedia.mvp.view.SlideVerifyView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.LogUtil;
import com.guochuang.mimedia.tools.RxUtil;
import com.guochuang.mimedia.tools.glide.GlideApp;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.guochuang.mimedia.tools.glide.RadiusTransformation;
import com.guochuang.mimedia.view.VerifySeekBar;
import com.guochuang.mimedia.view.VerifyView;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jiguang.share.android.api.JShareInterface;


/**
 * Created by Administrator on 2017-06-24 0024.
 */

public abstract class SlideVerifyDialog extends Dialog implements SlideVerifyView {

    Context mContext;

    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.verify)
    VerifyView verify;
    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.iv_refresh)
    ImageView ivRefresh;

    SlideVerifyPresenter presenter;
    SlideVerifyData slideVerifyData;

    public abstract void onResult(String flag);

    @Override
    public void setData(final SlideVerifyData data) {
       if (data!=null){
           slideVerifyData=data;
           GlideApp.with(getContext())
                   .asBitmap()
                   .load(data.getOriCopyImages())
                   .into(new SimpleTarget<Bitmap>() {
                       @Override
                       public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                           verify.setImageBitmap(resource);
                           GlideApp.with(getContext())
                                   .asBitmap()
                                   .load(data.getSlideImages())
                                   .into(new SimpleTarget<Bitmap>() {
                                       @Override
                                       public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                           verify.setVerifyBitmap(resource);
                                           reInit();
                                       }
                                   });
                       }
                   });

       }
    }

    @Override
    public void setError(String msg) {
      Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setVerifyData(Boolean data) {
        if (data){
            dismiss();
            onResult(slideVerifyData.getUniqueFlag());
        }else {
            setVerifyError(getContext().getString(R.string.verify_fail));
        }

    }

    @Override
    public void setVerifyError(String data) {
        tvResult.setText(data);
        presenter.getVerifyData();
    }


    public interface OnResultListener {
        void onResult(String flag);
    }

    public SlideVerifyDialog(@NonNull Context context) {
        super(context, R.style.dialog_bottom_full);
        mContext = context;
        View view = View.inflate(context, R.layout.dialog_slide_verify, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        init();
        presenter=new SlideVerifyPresenter(this);
        presenter.getVerifyData();
    }
    public void init(){
        seekBar.setMax(10000);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                verify.setMove(progress*0.0001);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });
        seekBar.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_UP:
                        presenter.verifySlideCaptcha(slideVerifyData.getUniqueFlag(),String.valueOf(seekBar.getProgress()*0.0001),String.valueOf(System.currentTimeMillis()));
                        break;
                }
                return false;
            }

        });

    }


    void reInit(){
        verify.setReDraw();
        seekBar.setProgress(0);
    }


    @OnClick({R.id.iv_close, R.id.iv_refresh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.iv_refresh:
                presenter.getVerifyData();
                break;
        }
    }


}
