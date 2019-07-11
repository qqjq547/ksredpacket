package com.guochuang.mimedia.view;

import java.util.Random;
 
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.guochuang.mimedia.tools.LogUtil;

/**
 * @author ZhengJingle
 */
public class VerifyView extends View {

	Bitmap drawBitmap;//背景图
	Bitmap verifyBitmap;//验证图
	boolean reCalc = true;//是否需要重新计算
	int moveX;//移动x坐标
	int moveMax;//最大移动
	int slideWidth;

	public VerifyView(Context context) {
		super(context);
	}

	public VerifyView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public VerifyView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//获取宽度的模式和尺寸
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		setMeasuredDimension(widthMeasureSpec,widthSize*360/590);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (drawBitmap == null || verifyBitmap==null) return;
		if (reCalc) {
			/*
			 * 背景图
			 */
			int width = getWidth();
			int height = getHeight();
			drawBitmap=Bitmap.createScaledBitmap(drawBitmap, width, height, false);
			slideWidth=height*verifyBitmap.getWidth()/verifyBitmap.getHeight();
			verifyBitmap=Bitmap.createScaledBitmap(verifyBitmap, slideWidth, height, false);
			moveMax = width-slideWidth;
			reCalc = false;//下次不用再进入这个if
		}
		Paint paint = new Paint();
		canvas.drawBitmap(drawBitmap, 0, 0, paint);//画背景图
		paint.setColor(Color.parseColor("#ffffffff"));
		canvas.drawBitmap(verifyBitmap, moveX, 0, paint);//画验证图片
	}

	public void setImageBitmap(Bitmap bitmap) {
		this.drawBitmap = bitmap;
	}

	public void setVerifyBitmap(Bitmap bitmap){
		this.verifyBitmap=bitmap;
	}

	public void setMove(double precent) {
		if (precent < 0 || precent > 1) return;
		moveX = (int) (moveMax * precent);
		invalidate();
	}

	public void setReDraw() {
		reCalc = true;
		invalidate();
	}
}