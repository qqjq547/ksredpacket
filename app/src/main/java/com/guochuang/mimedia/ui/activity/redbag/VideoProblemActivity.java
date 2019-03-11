package com.guochuang.mimedia.ui.activity.redbag;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dmcbig.mediapicker.utils.ScreenUtils;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.base.dialog.AlertDialog;
import com.guochuang.mimedia.base.navigationbar.DefaultNavigationBar;
import com.guochuang.mimedia.base.recycleview.WrapEmptyRecyclerView;
import com.guochuang.mimedia.base.recycleview.adapter.MultiTypeSupport;
import com.guochuang.mimedia.mvp.model.ProblemBean;
import com.guochuang.mimedia.mvp.presenter.VideoProblemPresenter;
import com.guochuang.mimedia.mvp.view.VideoProblemView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.ui.adapter.ProblemDialogInAdapter;
import com.guochuang.mimedia.ui.adapter.VideoProblemAdapter;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class VideoProblemActivity extends MvpActivity<VideoProblemPresenter> implements VideoProblemView {

    @BindView(R.id.recycler_list)
    WrapEmptyRecyclerView recyclerList;
    @BindView(R.id.tv_nuber)
    TextView tvNuber;
    @BindView(R.id.tv_add_problem)
    TextView tvAddProblem;
    private VideoProblemAdapter mVideoProblemAdapter;
    private List mData = new ArrayList(5);
    private String mRedPacketType;
    private ArrayList<ProblemBean> mProblemList = new ArrayList<>();
    private ProblemDialogInAdapter mProblemDialogInAdapter;
    private ArrayList<ProblemBean.ItemBean> mDialogData;
    private ProblemBean mEditProblemBean, mCustomaryProblemBean;
    private boolean mIsRestProblem = false;
    private DefaultNavigationBar.Builder mNavigationbuilder;

    @Override
    protected VideoProblemPresenter createPresenter() {
        return new VideoProblemPresenter();
    }


    @Override
    protected void paserIntent(Bundle bundle) {
        super.paserIntent(bundle);
        mProblemList = bundle.getParcelableArrayList(Constant.PROBLEMLIST_KEY);
        mRedPacketType = bundle.getString(Constant.OPEN_VIDEOPROBLEMACTIVITY_TYPE);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_videoproblem;
    }

    @Override
    public void initViewAndData() {
        mNavigationbuilder = new DefaultNavigationBar.Builder(this);
        if (Constant.RED_PACKET_TYPE_VIDEO.equals(mRedPacketType)) {
            mNavigationbuilder.setTitle("视频红包问题");
        } else {
            mNavigationbuilder.setTitle("问卷红包问题");
        }

        mNavigationbuilder.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭activity

                //把题目的值带回去
                Intent intent = getIntent();
                intent.putParcelableArrayListExtra(Constant.问题数据集合, mProblemList);
                setResult(RESULT_OK, intent);
                finish();


            }
        }).build();

        recyclerList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mVideoProblemAdapter = new VideoProblemAdapter(this, mProblemList, R.layout.item_problem_layout,mRedPacketType);
        mVideoProblemAdapter.setOnEditeClickLisenter(new VideoProblemAdapter.OnEditeClick() {
            @Override
            public void onClick(int position) {
                //修改

                EditProblem(position);
//                showDialog();
            }
        });

        mVideoProblemAdapter.setOnDeleteClickLisenter(new VideoProblemAdapter.OnDeleteClick() {
            @Override
            public void onClick(int position) {
                mProblemList.remove(position);
                refreshUI();
            }
        });

        recyclerList.setAdapter(mVideoProblemAdapter);
        recyclerList.isShowEmptyPage();
        refreshUI();

    }

    private void EditProblem(int position) {
        //修改前的数据
        mIsRestProblem = true;
        mCustomaryProblemBean = mProblemList.get(position);
        mEditProblemBean = mCustomaryProblemBean.clone();

        showDialog(mEditProblemBean);


    }


    //RefreshUI
    private void refreshUI() {
        if(mProblemList.size()>0) {
            tvNuber.setText(Html.fromHtml("已添加<font color = '#ff7519'>" + mProblemList.size() + "</font>个问题"));
        }

        if (mProblemList.size() >= 5) {
            tvAddProblem.setVisibility(View.GONE);
        } else {
            tvAddProblem.setVisibility(View.VISIBLE);
        }

        if (mVideoProblemAdapter != null) {
            recyclerList.isShowEmptyPage();
            mVideoProblemAdapter.notifyDataSetChanged();
        }


    }


    @OnClick(R.id.tv_add_problem)
    public void onViewClicked() {
        //添加问题
        mIsRestProblem = false;
        showDialog(null);


    }

    private void showDialog(ProblemBean problemBean_) {
        final ProblemBean problemBean;
        mDialogData = new ArrayList<>();
        if (problemBean_ == null) {
            problemBean = new ProblemBean(Parcel.obtain());
            setItmeType(problemBean, 0);

        } else {
            problemBean = problemBean_;

        }


        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_problem_layout)
                .setGravity(Gravity.CENTER)
                .setWidthAndHeight((int) (ScreenUtils.getScreenWidth(this) * 0.9), ViewGroup.LayoutParams.WRAP_CONTENT)
                .create();

        LinearLayout ll_header_root = alertDialog.getView(R.id.ll_header_root);
        if (Constant.RED_PACKET_TYPE_QUESTION.equals(mRedPacketType)) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll_header_root.getLayoutParams();
            layoutParams.bottomMargin = ScreenUtils.dp2px(this, 8);
            ll_header_root.setLayoutParams(layoutParams);
        }


        EditText etInputProblem = alertDialog.getView(R.id.et_input_problem);
        if (!TextUtils.isEmpty(problemBean.getProblem())) {
            etInputProblem.setText(problemBean.getProblem());
            etInputProblem.setSelection(problemBean.getProblem().trim().length());
        }

        RadioGroup rgProblemType = alertDialog.getView(R.id.rg_problem_type);


        rgProblemType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 里面是一个多类型的recycleView
                //根据type 切换中间布局
                switch (checkedId) {
                    case R.id.rb_single_select:
                        setItmeType(problemBean, 0);
                        break;
                    case R.id.rb_many_select:
                        setItmeType(problemBean, 1);
                        break;
                    case R.id.eb_fill_in_blanks:
                        setItmeType(problemBean, 2);
                        break;
                }


            }
        });

        RadioButton childAt = (RadioButton) rgProblemType.getChildAt(problemBean.getType());
        childAt.setChecked(true);

        RecyclerView dialogInRecycle = alertDialog.getView(R.id.dailog_recycler_list);
        dialogInRecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        mProblemDialogInAdapter = new ProblemDialogInAdapter(this, mDialogData, new MultiTypeSupport<ProblemBean.ItemBean>() {
            @Override
            public int getLayoutId(ProblemBean.ItemBean item, int position) {
                switch (item.getProblemType()) {
                    case 0:
                        return R.layout.dialog_problem_center_select_item_layout;
                    case 1:
                        return R.layout.dialog_problem_center_select_item_layout;
                    case 2:
                        return R.layout.dialog_problem_center_t_layout;


                }
                return 0;
            }
        }, mRedPacketType);

        dialogInRecycle.setAdapter(mProblemDialogInAdapter);


        alertDialog.getView(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        final EditText et_input_problem = alertDialog.getView(R.id.et_input_problem);
        alertDialog.getView(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                problemBean.setProblem(et_input_problem.getText().toString().trim());
                if (TextUtils.isEmpty(problemBean.getProblem())) {
                    showShortToast("请填写问题");
                    return;
                }

                //todo 判断红包的类型     区分条件
                if(Constant.RED_PACKET_TYPE_VIDEO.equals(mRedPacketType)) {




                    if (problemBean.getType() == 2) {
                        if (TextUtils.isEmpty(problemBean.getItem().get(0).getItemcontent())) {
                            showShortToast("请填写答案");
                            return;
                        }
                    } else {
                        Log.e("onClick: ", problemBean.toString());
                        //判斷是否設置答案
                        List<ProblemBean.ItemBean> items = problemBean.getItem();
                        boolean flag = false;
                        for (ProblemBean.ItemBean item : items) {
                            if (item.isIsanswer()) {
                                flag = true;
                                break;
                            }
                        }

                        if (!flag) {
                            showShortToast("请设置答案");
                            return;
                        }
                    }












                }else {









                }














                if (mIsRestProblem) {
                    mProblemList.remove(mCustomaryProblemBean);
                }
                mProblemList.add(problemBean);


                alertDialog.dismiss();
                //刷新外部列表
                refreshUI();

            }
        });
        alertDialog.show();

    }

    /**
     * 设置问题类型
     *
     * @param problemBean
     * @param type
     */
    private void setItmeType(ProblemBean problemBean, int type) {

        int number;
        if (type == 2) {
            number = 1;
        } else {
            number = 4;
        }

        if (!mIsRestProblem) {

            crateItme(problemBean, type, number);
        } else {
            mDialogData.clear();
            mDialogData.addAll(problemBean.getItem());
            //修改的
            changeProblemType(problemBean, type);
        }

        problemBean.setType(type);
    }

    /**
     * 改变类型
     */
    private void changeProblemType(ProblemBean problemBean, int type) {
        if (problemBean.getType() != type) {
            List<ProblemBean.ItemBean> item = problemBean.getItem();
            for (ProblemBean.ItemBean itemBean : item) {
                itemBean.setProblemType(type);
                itemBean.setIsanswer(false);
            }
            if (mProblemDialogInAdapter != null) {
                mProblemDialogInAdapter.notifyDataSetChanged();
            }
        }


    }

    /**
     * 初始化Dialog 问题的Item
     *
     * @param problemBean
     * @param type
     * @param number
     */
    private void crateItme(ProblemBean problemBean, int type, int number) {
        mDialogData.clear();
        //设置回显
        for (int i = 0; i < number; i++) {
            ProblemBean.ItemBean itemBean = new ProblemBean.ItemBean(Parcel.obtain());
            itemBean.setIsanswer(false);
            String name = i == 0 ? "A" : i == 1 ? "B" : i == 2 ? "C" : "D";
            itemBean.setItemname(name);
            itemBean.setProblemType(type);
            mDialogData.add(itemBean);
        }
        problemBean.setItem(mDialogData);


        if (mProblemDialogInAdapter != null) {
            mProblemDialogInAdapter.notifyDataSetChanged();
        }
    }


}