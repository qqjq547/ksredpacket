package com.guochuang.mimedia.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.sz.gcyh.KSHongBao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/22.
 */

public class RemindDialog extends Dialog {

    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    String detailUrl;

    public RemindDialog(@NonNull Context context,String imageUrl,String detailUrl) {
        super(context, R.style.dialog_bottom_full);
        setCanceledOnTouchOutside(true);
        setCancelable(false);
        View view = View.inflate(context, R.layout.dialog_remind, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        GlideImgManager.loadImage(context,imageUrl,ivImage);
        this.detailUrl=detailUrl;
    }

    @OnClick({R.id.iv_image, R.id.iv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_image:
                IntentUtils.startWebActivity(App.getInstance().lastActivity(),"",detailUrl);
                cancel();
                break;
            case R.id.iv_close:
                cancel();
                break;
        }
    }
}
