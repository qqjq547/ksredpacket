package com.guochuang.mimedia.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.OnViewTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.tools.glide.GlideApp;

import java.io.File;
import java.util.List;

/**
 * 视频预览Adapter
 *
 * @author zhangjunpu
 * @date 14/11/21
 */
public class ImagePreviewAdapter extends PagerAdapter {

    private List<String> mData;
    private LayoutInflater inflater;

    private OnViewTapListener mOnViewTapListener;
    private View.OnLongClickListener onLongClickListener;

    private Context mContext;

    public ImagePreviewAdapter(Context context, List<String> url) {
        mContext = context;
        mData = url;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public String getItem(int position) {
        if (mData == null) {
            return null;
        }
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.item_image_pager, view, false);

        final ProgressBar progress = imageLayout.findViewById(R.id.loading);
        PhotoView imageView = imageLayout.findViewById(R.id.image);
        imageView.setOnViewTapListener(mOnViewTapListener);
        imageView.setOnLongClickListener(onLongClickListener);
        String url = getItem(position);
        loadImageUrl(url, imageView, progress);
        view.addView(imageLayout, 0);
        return imageLayout;
    }

    private void loadImageUrl(String url, ImageView imageView, final ProgressBar progress) {
        File file=new File(url);
        if (file.exists()){
            progress.setVisibility(View.GONE);
            GlideApp.with(mContext).load(file).into(imageView);
        }else {
            GlideApp.with(mContext)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                  .error(R.drawable.default_cover)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progress.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                            progress.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(imageView);
        }

    }

    public void setOnViewTapListener(OnViewTapListener listener) {
        mOnViewTapListener = listener;
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }
}
