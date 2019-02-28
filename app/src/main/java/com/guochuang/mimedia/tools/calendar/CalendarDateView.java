package com.guochuang.mimedia.tools.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sz.gcyh.KSHongBao.R;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by codbking on 2016/12/18.
 * email:codbking@gmail.com
 * github:https://github.com/codbking
 * blog:http://www.jianshu.com/users/49d47538a2dd/latest_articles
 */

public class CalendarDateView extends ViewPager {

    HashMap<Integer, CalendarView> views = new HashMap<>();
    private CalendarView.OnItemClickListener onItemClickListener;

    private int MAXCOUNT=6;


    private int row = 6;

    private CaledarAdapter mAdapter;
    private int calendarItemHeight = 0;

    public void setAdapter(CaledarAdapter adapter) {
        mAdapter = adapter;
        initData();
    }

    public void setOnItemClickListener(CalendarView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CalendarDateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CalendarDateView);
        row = a.getInteger(R.styleable.CalendarDateView_cbd_calendar_row, 5);
        a.recycle();
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int calendarHeight = 0;
        if (getAdapter() != null) {
            CalendarView view = (CalendarView) getChildAt(0);
            if (view != null) {
                calendarHeight = view.getMeasuredHeight();
                calendarItemHeight = view.getItemHeight();
            }
        }
        setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(calendarHeight, MeasureSpec.EXACTLY));
    }
    public void update() {
        for (CalendarView calView:views.values()){
            calView.updateView();
        }
    }
    private void init() {
       final int[] dateArr= CalendarUtil.getYMD(new Date());

        setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {

                CalendarView view = new CalendarView(container.getContext(), row);

                view.setOnItemClickListener(onItemClickListener);
                view.setAdapter(mAdapter);

                view.setData(CalendarFactory.getMonthOfDayList(dateArr[0],dateArr[1]+position-Integer.MAX_VALUE/2),position==Integer.MAX_VALUE/2);
                container.addView(view);
                views.put(position, view);

                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
                views.remove(position);
            }
        });

        addOnPageChangeListener(new SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                if (onItemClickListener != null) {
                    CalendarView view = views.get(position);
                    if (view==null){
                        return;
                    }
                    Object[] obs = view.getSelect();
                    onItemClickListener.onItemClick((View) obs[0], (int) obs[1], (CalendarBean) obs[2]);
                }
            }
        });
    }


    private void initData() {
        setCurrentItem(Integer.MAX_VALUE/2, false);
        getAdapter().notifyDataSetChanged();
    }

}
