package com.guochuang.mimedia.ui.activity.redbag;

import android.content.Intent;
import android.os.Parcel;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.mvp.model.ProblemBean;
import com.guochuang.mimedia.view.dialog.AlertDialog;
import com.guochuang.mimedia.view.recycleview.WrapEmptyRecyclerView;
import com.guochuang.mimedia.view.recycleview.adapter.MultiTypeSupport;
import com.guochuang.mimedia.mvp.presenter.VideoProblemPresenter;
import com.guochuang.mimedia.mvp.view.VideoProblemView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.DialogBuilder;
import com.guochuang.mimedia.ui.adapter.EditRedPackgeProblemAdapter;
import com.guochuang.mimedia.ui.adapter.ProblemDialogInAdapter;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 编辑视频与问卷问题
 */
public class EditRedPackgeProblemActivity extends MvpActivity<VideoProblemPresenter> implements VideoProblemView {

    @BindView(R.id.recycler_list)
    WrapEmptyRecyclerView recyclerList;
    @BindView(R.id.tv_nuber)
    TextView tvNuber;
    @BindView(R.id.tv_add_problem)
    TextView tvAddProblem;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    private EditRedPackgeProblemAdapter mEditRedPackgeProblemAdapter;
    private String mRedPacketType;
    private ArrayList<ProblemBean> mProblemList = new ArrayList<>();
    private ProblemDialogInAdapter mProblemDialogInAdapter;
    private ArrayList<ProblemBean.ItemBean> mDialogData;
    private ProblemBean mEditProblemBean, mCustomaryProblemBean;
    private boolean mIsRestProblem = false;

    @Override
    protected VideoProblemPresenter createPresenter() {
        return new VideoProblemPresenter();
    }


    @Override
    public int getLayout() {
        return R.layout.activity_videoproblem;
    }

    @Override
    public void initViewAndData() {
        setStatusbar(R.color.white, true);
        mProblemList = getIntent().getParcelableArrayListExtra(Constant.PROBLEMLIST_KEY);
        mRedPacketType = getIntent().getStringExtra(Constant.RED_PACKET_TYPE);

        if (Constant.RED_PACKET_TYPE_VIDEO.equals(mRedPacketType)) {
            mTvTitle.setText(getResources().getString(R.string.video_redbag_problem));
        } else {
            mTvTitle.setText(getResources().getString(R.string.surevy_redbag_problem));
        }

        recyclerList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mEditRedPackgeProblemAdapter = new EditRedPackgeProblemAdapter(mProblemList, R.layout.item_problem_layout, mRedPacketType);
        mEditRedPackgeProblemAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_edit_problem:
                        EditProblem(position);
                        break;
                    case R.id.iv_delete_problem:
                        //删除提示框
                        deletDialog(position);

                        break;
                }
            }
        });

        recyclerList.setAdapter(mEditRedPackgeProblemAdapter);
        recyclerList.isShowEmptyPage();
        refreshUI();

    }


    private void deletDialog(final int position) {

        new DialogBuilder(this)
                .setTitle(R.string.tip)
                .setMessage(R.string.tips_delete_problem)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.confirm, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mProblemList.remove(position);
                        refreshUI();

                    }
                }).create().show();

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
        if (mProblemList.size() > 0) {
            tvNuber.setVisibility(View.VISIBLE);

            tvNuber.setText(Html.fromHtml(String.format(getString(R.string.added_problme_number), mProblemList.size())));
        } else {
            tvNuber.setVisibility(View.GONE);
        }

        if (mProblemList.size() >= 5) {
            tvAddProblem.setVisibility(View.GONE);
        } else {
            tvAddProblem.setVisibility(View.VISIBLE);
        }

        if (mEditRedPackgeProblemAdapter != null) {
            recyclerList.isShowEmptyPage();
            mEditRedPackgeProblemAdapter.notifyDataSetChanged();
        }


    }



    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        intent.putParcelableArrayListExtra(Constant.PROBLEM_LIST, mProblemList);
        setResult(RESULT_OK, intent);
        super.onBackPressed();

    }


    @OnClick({R.id.iv_back, R.id.tv_add_problem})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                //把题目的值带回去

                break;
            case R.id.tv_add_problem:
                mIsRestProblem = false;
                showDialog(null);

                break;
        }
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
                .setWidthAndHeight((int) (CommonUtil.getScreenW(this) * 0.9), ViewGroup.LayoutParams.WRAP_CONTENT)
                .create();

        LinearLayout ll_header_root = alertDialog.getView(R.id.ll_header_root);
        if (Constant.RED_PACKET_TYPE_SURVEY.equals(mRedPacketType)) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll_header_root.getLayoutParams();
            layoutParams.bottomMargin = CommonUtil.dip2px(this, 8);
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
                        setItmeType(problemBean, Constant.SINGLESELECT);
                        break;
                    case R.id.rb_many_select:
                        setItmeType(problemBean, Constant.MORESELECT);
                        break;
                    case R.id.eb_fill_in_blanks:
                        setItmeType(problemBean, Constant.FILL_IN_PROBLEM);
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

                if (!checkCondition(problemBean)) {
                    return;
                }

                //移除多余的选项
                removeRedundantOptions(problemBean);

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
     * checkCondition 校验条件是否满足需求
     *
     * @param problemBean
     */
    private boolean checkCondition(ProblemBean problemBean) {

        if (TextUtils.isEmpty(problemBean.getProblem())) {
            showShortToast(R.string.fill_problem_title);
            return false;
        }

        // 判断红包的类型    区分条件   视频红包

        if (problemBean.getType() == Constant.FILL_IN_PROBLEM) {
            //填空题
            if (Constant.RED_PACKET_TYPE_VIDEO.equals(mRedPacketType)) {
                //并且是视频红包
                if (TextUtils.isEmpty(problemBean.getItem().get(0).getItemcontent())) {
                    showShortToast(R.string.please_set_answer);
                    return false;
                }
            }
        } else {
            //选择题
            //判斷是否設置答案
            List<ProblemBean.ItemBean> items = problemBean.getItem();


            int number = 0;
            for (ProblemBean.ItemBean item : items) {
                if (!TextUtils.isEmpty(item.getItemcontent())) {
                    number++;
                }

            }

            if (number < 2) {
                showShortToast(R.string.provide_least_two_items);
                return false;
            }


            if (Constant.RED_PACKET_TYPE_VIDEO.equals(mRedPacketType)) {
                //只有视频红包问题才能设置答案
                boolean flag = false;
                for (ProblemBean.ItemBean item : items) {
                    if (item.isIsanswer()) {
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    showShortToast(R.string.please_set_answer);
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 移除多余的选项 Remove redundant options
     */
    private void removeRedundantOptions(ProblemBean problemBean) {
        Iterator<ProblemBean.ItemBean> iterator = problemBean.getItem().iterator();
        while (iterator.hasNext()) {
            ProblemBean.ItemBean item = iterator.next();
            if (TextUtils.isEmpty(item.getItemcontent())) {
                iterator.remove();
            }
        }

    }

    /**
     * 设置问题类型
     *
     * @param problemBean
     * @param type
     */
    private void setItmeType(ProblemBean problemBean, int type) {

        int number;
        if (type == Constant.FILL_IN_PROBLEM) {
            //填空题
            number = 1;
        } else {
            number = 4;
        }

        if (!mIsRestProblem) {
            if (problemBean.getItem() == null || problemBean.getItem().isEmpty()) {
                crateItme(problemBean, type, number);
            } else {
                changeProblemType(problemBean, type);
            }

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
                itemBean.setIsanswer(type == 2 ? true : false);
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
            itemBean.setIsanswer(type == 2 ? true : false);
            String name = i == 0 ? "A" : i == 1 ? "B" : i == 2 ? "C" : "D";
            itemBean.setItemname(type == 2 ? "" : name);
            itemBean.setProblemType(type);
            mDialogData.add(itemBean);
        }
        problemBean.setItem(mDialogData);


        if (mProblemDialogInAdapter != null) {
            mProblemDialogInAdapter.notifyDataSetChanged();
        }
    }


}
