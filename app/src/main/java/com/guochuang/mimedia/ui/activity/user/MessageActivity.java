package com.guochuang.mimedia.ui.activity.user;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.Message;
import com.guochuang.mimedia.mvp.presenter.MessagePresenter;
import com.guochuang.mimedia.mvp.view.MessageView;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.PrefUtil;
import com.guochuang.mimedia.ui.adapter.MessageAdapter;
import com.guochuang.mimedia.ui.dialog.InputDialog;
import com.guochuang.mimedia.view.VerticalDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MessageActivity extends MvpActivity<MessagePresenter> implements MessageView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_message)
    RecyclerView rvMessage;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;

    List<Message> messageArr=new ArrayList<>();
    MessageAdapter adapter;
    int curPage=1;
    String replyContent;
    long parentId = 0;
    InputDialog inputDialog;
    String redPacketUuid;
    @Override
    protected MessagePresenter createPresenter() {
        return new MessagePresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_message;
    }

    @Override
    public void initViewAndData() {
        tvTitle.setText(R.string.message);
        getPref().setBoolean(PrefUtil.MSGISNEW,false);
        rvMessage.setLayoutManager(new LinearLayoutManager(this,OrientationHelper.VERTICAL,false));
        rvMessage.addItemDecoration(new VerticalDecoration(this));
        adapter=new MessageAdapter(messageArr);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Message message=messageArr.get(position);
                switch (view.getId()){
                    case R.id.tv_reply:
                        parentId=message.getCommentId();
                        redPacketUuid=message.getSourceUuid();
                        showInput();
                        inputDialog.setHint(getString(R.string.reply));
                        break;
                    case R.id.lin_content:
                        IntentUtils.startRedbagDetailActivity(MessageActivity.this,message.getSourceUuid(),Constant.ROLETYPE_PERSON,null,null);
                        break;
                }
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Message message=messageArr.get(position);
                switch (message.getType()){
                    case Constant.MSG_TYPE_NOTICE:
                        IntentUtils.startWebActivity(MessageActivity.this,message.getTitle(),Constant.URL_NOTICE_DETAIL +"?id="+message.getId());
                        break;
                    case Constant.MSG_TYPE_SNATCHACTIVITY:
                        IntentUtils.startWebActivity(MessageActivity.this,"",Constant.URL_DUOBAO_DETAIL+message.getSourceUuid());
                        break;
                }
            }
        });
        rvMessage.setAdapter(adapter);
        srlRefresh.setEnableRefresh(true);
        srlRefresh.setEnableLoadmore(true);
        srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mvpPresenter.getMessageList(curPage + 1, Constant.PAGE_SIZE);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mvpPresenter.getMessageList(1, Constant.PAGE_SIZE);
            }
        });
        mvpPresenter.getMessageList(curPage, Constant.PAGE_SIZE);
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void setData(Page<Message> data) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        curPage = data.getCurrentPage();
        if (curPage == 1) {
            messageArr.clear();
        }
        messageArr.addAll(data.getDataList());
        adapter.notifyDataSetChanged();
        if (data.getCurrentPage() >= data.getTotalPage()) {
            srlRefresh.setEnableLoadmore(false);
        } else {
            srlRefresh.setEnableLoadmore(true);
        }
    }

    @Override
    public void setCommentAdd(Integer data) {
        closeLoadingDialog();
        getPref().setBoolean(PrefUtil.MSGISNEW,false);
        parentId=0;
        if (inputDialog!=null){
            inputDialog.clearText();
            inputDialog.dismiss();
        }
        showShortToast(R.string.publish_success);
    }

    @Override
    public void setCommentReply(Integer data) {
        closeLoadingDialog();
        parentId=0;
        if (inputDialog!=null){
            inputDialog.clearText();
            inputDialog.dismiss();
        }
        showShortToast(R.string.publish_success);
    }

    @Override
    public void setError(String msg) {
            srlRefresh.finishRefresh();
            srlRefresh.finishLoadmore();
            closeLoadingDialog();
            showShortToast(msg);
    }
    private void showInput() {
        if (inputDialog==null){
            inputDialog=new InputDialog(this);
            inputDialog.setOnResultListener(new InputDialog.OnResultListener() {
                @Override
                public void onReplyText(String content) {
                    replyContent = content;
                    if (!TextUtils.isEmpty(replyContent)) {
                        showLoadingDialog(null);
                        if (parentId>0){
                            mvpPresenter.redBagCommentReply(parentId, replyContent);
                        }else {
                            mvpPresenter.redBagCommentAdd(redPacketUuid, replyContent, parentId);
                        }
                    }
                }
            });
        }
        inputDialog.show();
    }
}
