package com.guochuang.mimedia.ui.activity;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.presenter.ImagePreviewPresneter;
import com.guochuang.mimedia.mvp.view.ImagePreviewView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.DialogBuilder;
import com.guochuang.mimedia.view.HackyViewPager;
import com.guochuang.mimedia.ui.adapter.ImagePreviewAdapter;
import com.sz.gcyh.KSHongBao.R;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * 视频剧照
 *
 * @author zhangjunpu
 * @date 14/11/21
 */
public class ImagePreviewActivity extends MvpActivity<ImagePreviewPresneter> implements ImagePreviewView {

    @BindView(R.id.viewPager)
    HackyViewPager viewPager;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    ImagePreviewAdapter adapter;
    List<String> urls=new ArrayList<>();
    protected int pageSize;
    protected int pagerPosition;
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constant.ARGS_DATA, viewPager.getCurrentItem());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    protected ImagePreviewPresneter createPresenter() {
        return new ImagePreviewPresneter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_image_preview;
    }

    @Override
    public void initViewAndData() {
        setStatusbar(R.color.bg_black, false);
    }

    protected ViewPager.OnPageChangeListener onPagerChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int i) {
            setPages(i);
        }
    };

    protected void initData(Bundle savedInstanceState) {
        if (getIntent().hasExtra(Constant.ARGS_ARRAY)) {
            String[] array = getIntent().getStringArrayExtra(Constant.ARGS_ARRAY);
            urls = Arrays.asList(array);
        } else {
            urls = getIntent().getStringArrayListExtra(Constant.ARGS_LIST);
        }
        pageSize = urls.size();
        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(Constant.ARGS_DATA);
        } else {
            pagerPosition = getIntent().getIntExtra(Constant.ARGS_POSITION, 0);
        }

        adapter = new ImagePreviewAdapter(this, urls);
        adapter.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new DialogBuilder(ImagePreviewActivity.this)
                        .setTitle(R.string.tip)
                        .setMessage(R.string.save_local)
                        .setNegativeButton(R.string.cancel,null)
                        .setPositiveButton(R.string.confirm, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showLoadingDialog(null);
                                mvpPresenter.downloadPicture(adapter.getItem(viewPager.getCurrentItem()));
                            }
                        }).create().show();
                return false;
            }
        });
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(pagerPosition);
        viewPager.addOnPageChangeListener(onPagerChangeListener);
        setPages(pagerPosition);

        if (pageSize == 1) tvTitle.setVisibility(View.INVISIBLE);
    }

    protected void setPages(int position) {
        tvTitle.setText((position + 1) + "/" + pageSize);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void setData(final Bitmap data) {
        closeLoadingDialog();
        if (data!=null){
            RxPermissions rxPermissions=new RxPermissions(this);
            rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
                @Override
                public void call(Boolean aBoolean) {
                  if (aBoolean){
                      String filePath=Constant.COMMON_PATH +File.separator+ System.currentTimeMillis() + ".png";
                      CommonUtil.saveBitmap(data,filePath);
                      showShortToast(R.string.save_image_success);
                  }else {
                      showShortToast(R.string.get_permission);
                  }
                }
            });
        }
    }

    @Override
    public void setError(String msg) {
      closeLoadingDialog();
      showShortToast(msg);
    }
}