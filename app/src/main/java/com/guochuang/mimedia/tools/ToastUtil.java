package com.guochuang.mimedia.tools;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sz.gcyh.KSHongBao.R;

public class ToastUtil {
    public static void showSuccess(Context context, String messages, int drawableId) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_toast, null);
        ImageView iv = view.findViewById(R.id.iv_icon);
        iv.setImageResource(drawableId);
        TextView text = view.findViewById(R.id.tv_message);
        if (TextUtils.isEmpty(messages)){
            text.setVisibility(View.GONE);
        }else {
            text.setVisibility(View.VISIBLE);
            text.setText(messages);
        }
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

}
