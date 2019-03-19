package com.guochuang.mimedia.ui.activity.redbag;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.mvp.model.PictureBean;
import com.guochuang.mimedia.mvp.model.RedbagInfo;
import com.guochuang.mimedia.tools.AdCollectionView;
import com.guochuang.mimedia.tools.PrefUtil;
import com.guochuang.mimedia.ui.activity.common.ShareActivity;
import com.guochuang.mimedia.view.BadgeView;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.sz.gcyh.KSHongBao.R;
import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.http.subscriber.CountDownSubscriber;
import com.guochuang.mimedia.mvp.model.FavoriteAndPraise;
import com.guochuang.mimedia.mvp.model.RedPacketReply;
import com.guochuang.mimedia.mvp.model.RedbagDetail;
import com.guochuang.mimedia.mvp.model.UserInfo;
import com.guochuang.mimedia.mvp.presenter.RedbagDetailPresenter;
import com.guochuang.mimedia.mvp.view.RedbagDetailView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.DialogBuilder;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.RxUtil;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.guochuang.mimedia.ui.adapter.RedbagReplyAdapter;
import com.guochuang.mimedia.ui.dialog.InputDialog;
import com.guochuang.mimedia.view.MultiImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action0;

public class RedbagDetailActivity extends MvpActivity<RedbagDetailPresenter> implements RedbagDetailView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.lin_title_center)
    LinearLayout linTitleCenter;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_red_packet_details_top_all)
    LinearLayout llTopLl;
    @BindView(R.id.iv_red_packet_details_header)
    ImageView ivHeader;
    @BindView(R.id.tv_red_packet_details_name)
    TextView tvName;
    @BindView(R.id.tv_red_packet_details_scope)
    TextView tvScope;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.rl_value)
    RelativeLayout rlValue;
    @BindView(R.id.tv_redbag_tip)
    TextView tvRedbagTip;
    @BindView(R.id.tv_red_packet_details_ksb)
    TextView tvKsb;
    @BindView(R.id.tv_red_packet_details_money)
    TextView tvMoney;
    @BindView(R.id.ll_red_packet_details_receive_header)
    LinearLayout llReceiveHeader;
    @BindView(R.id.tv_red_packet_details_receive_num)
    TextView tvReceiveNum;
    @BindView(R.id.iv_draw_avatar_1)
    ImageView ivDrawAvatar1;
    @BindView(R.id.iv_draw_avatar_2)
    ImageView ivDrawAvatar2;
    @BindView(R.id.iv_draw_avatar_3)
    ImageView ivDrawAvatar3;
    @BindView(R.id.tv_red_packet_details_content)
    TextView tvContent;
    @BindView(R.id.miv_picture)
    MultiImageView mivPicture;
    @BindView(R.id.tv_url)
    TextView tvUrl;
    @BindView(R.id.lin_redbag_detail)
    LinearLayout linRedbagDetail;
    @BindView(R.id.rv_red_packet_details)
    RecyclerView rvComment;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;
    @BindView(R.id.ll_red_packet_details_money)
    LinearLayout llRedPacketDetailsMoney;
    @BindView(R.id.lin_title)
    LinearLayout linTitle;
    @BindView(R.id.iv_comment)
    ImageView ivComment;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;
    @BindView(R.id.iv_zan)
    ImageView ivZan;
    @BindView(R.id.lin_comment)
    LinearLayout linComment;
    @BindView(R.id.lin_reply)
    LinearLayout linReply;
    @BindView(R.id.lin_wechat)
    LinearLayout linWechat;
    @BindView(R.id.tv_wechat)
    TextView tvWechat;
    @BindView(R.id.lin_weibo)
    LinearLayout linWeibo;
    @BindView(R.id.tv_weibo)
    TextView tvWeibo;
    @BindView(R.id.lin_content)
    LinearLayout linContent;
    @BindView(R.id.lin_ad)
    LinearLayout linAd;
    //    @BindView(R.id.rlTemplate1)
//    RelativeLayout rlTemplate1;
    @BindView(R.id.iv_joined_arrow)
    ImageView ivJoinedArrow;
    @BindView(R.id.nsv_content)
    NestedScrollView nsvContent;
    @BindView(R.id.lin_redbag_share)
    LinearLayout linRedbagShare;

    @BindView(R.id.lin_video_head)
    LinearLayout linVideoHead;
    @BindView(R.id.tv_will_get_ksb)
    TextView tvWillGetKsb;
    @BindView(R.id.btn_open_packet)
    Button btnOpenPacket;
    @BindView(R.id.iv_video_prev)
    ImageView ivVideoPrev;

    RedbagReplyAdapter redPacketReplyAdapter;
    List<RedPacketReply> replyList = new ArrayList<>();
    RedPacketReply other;
    RedbagDetail redbagDetail;
    int curPage = 1;
    String replyContent;
    long parentId = 0;
    String redPacketUuid;
    InputDialog inputDialog;
    RedbagInfo redbagInfo;
    String roleType;
    String redPacketType;
    boolean fromCollect=false;
    String startIndex=null;
    BadgeView badgeView;
    int commentCount=0;
    FavoriteAndPraise favoriteAndPraise;

    private NativeExpressADView nativeExpressADView;

    @Override
    protected RedbagDetailPresenter createPresenter() {
        return new RedbagDetailPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_redbag_detail;
    }

    @Override
    public void initViewAndData() {
        redbagDetail = (RedbagDetail) getIntent().getSerializableExtra(Constant.RED_PACKET_DETAIL);
        showLoadingDialog(null);
        setStatusbar(R.color.bg_red, false);
        if (redbagDetail == null) {
            redPacketUuid = getIntent().getStringExtra(Constant.RED_PACKET_UUID);
            roleType = getIntent().getStringExtra(Constant.ROLE_TYPE);
            fromCollect=getIntent().getBooleanExtra(Constant.FROM_COLLECT,false);
            startIndex=getIntent().getStringExtra(Constant.START_INDEX);
            mvpPresenter.getRedPacketInfo(redPacketUuid, roleType,startIndex);
            if (roleType.equals(Constant.ROLETYPE_SYSTEM)) {
                linRedbagDetail.setVisibility(View.GONE);
                linComment.setVisibility(View.GONE);
                linReply.setVisibility(View.GONE);
                ivJoinedArrow.setVisibility(View.GONE);
                linRedbagShare.setVisibility(View.VISIBLE);
                srlRefresh.setEnableRefresh(false);
                srlRefresh.setEnableLoadmore(false);
            }else {
                if (roleType.equals(Constant.RED_PACKET_TYPE_VIDEO)){
                    rlValue.setVisibility(View.GONE);
                    tvRedbagTip.setVisibility(View.GONE);
                    linVideoHead.setVisibility(View.VISIBLE);
                    btnOpenPacket.setText(R.string.watched_video_open_redbag);
                }else if(roleType.equals(Constant.RED_PACKET_TYPE_QUESTION)){
                    rlValue.setVisibility(View.GONE);
                    tvRedbagTip.setVisibility(View.GONE);
                    linVideoHead.setVisibility(View.VISIBLE);
                    btnOpenPacket.setText(R.string.answer_open_redbag);
                }
                linRedbagDetail.setVisibility(View.VISIBLE);
                linComment.setVisibility(View.VISIBLE);
                linReply.setVisibility(View.VISIBLE);
                ivJoinedArrow.setVisibility(View.VISIBLE);
                linRedbagShare.setVisibility(View.GONE);
                other = new RedPacketReply();
                other.setId(0);
                rvComment.setLayoutManager(new LinearLayoutManager(RedbagDetailActivity.this, OrientationHelper.VERTICAL, false));
                rvComment.setItemAnimator(new DefaultItemAnimator());
                redPacketReplyAdapter = new RedbagReplyAdapter(replyList);
                redPacketReplyAdapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty, null));
                redPacketReplyAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
                redPacketReplyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if (replyList.get(position).getItemType() == RedPacketReply.OTHER) {
                            startActivity(new Intent(RedbagDetailActivity.this, ShareActivity.class));
                        }
                    }
                });
                redPacketReplyAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        parentId = replyList.get(position).getId();
                        showInput();
                        inputDialog.setHint(getString(R.string.replyed) + replyList.get(position).getNickName());
                    }
                });
                rvComment.setAdapter(redPacketReplyAdapter);
                srlRefresh.setEnableRefresh(false);
                srlRefresh.setEnableLoadmore(true);
                srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
                    @Override
                    public void onLoadmore(RefreshLayout refreshlayout) {
                        mvpPresenter.redBagCommentList(curPage + 1, Constant.PAGE_SIZE, redPacketUuid);
                    }

                    @Override
                    public void onRefresh(RefreshLayout refreshlayout) {
                        mvpPresenter.redBagCommentList(1, Constant.PAGE_SIZE, redPacketUuid);
                    }
                });
                mvpPresenter.redBagCommentList(curPage, Constant.PAGE_SIZE, redPacketUuid);
                mvpPresenter.redBagIsFavoritedOrPraised(redPacketUuid);
            }
            return;
        }
        redPacketUuid = getIntent().getStringExtra(Constant.RED_PACKET_UUID);
        roleType = getIntent().getStringExtra(Constant.ROLE_TYPE);
        redPacketType=getIntent().getStringExtra(Constant.RED_PACKET_TYPE);
        GlideImgManager.loadCircleImage(this, redbagDetail.getAvatar(), ivHeader);
        tvName.setText(redbagDetail.getNickName());
        if (TextUtils.equals(roleType,Constant.ROLETYPE_SYSTEM)) {
            linRedbagDetail.setVisibility(View.GONE);
//            ivAd.setVisibility(View.VISIBLE);
            linComment.setVisibility(View.GONE);
            linReply.setVisibility(View.GONE);
            ivJoinedArrow.setVisibility(View.GONE);
            linRedbagShare.setVisibility(View.VISIBLE);
            srlRefresh.setEnableRefresh(false);
            srlRefresh.setEnableLoadmore(false);
            setRedbagDetail(redbagDetail);
//            mvpPresenter.redPacketOpen(getPref().getLatitude(), getPref().getLongitude(), redPacketUuid);
        } else {
            linRedbagDetail.setVisibility(View.VISIBLE);
//            ivAd.setVisibility(View.GONE);
            linComment.setVisibility(View.VISIBLE);
            linReply.setVisibility(View.VISIBLE);
            ivJoinedArrow.setVisibility(View.VISIBLE);
            linRedbagShare.setVisibility(View.GONE);
            other = new RedPacketReply();
            other.setId(0);
            rvComment.setLayoutManager(new LinearLayoutManager(RedbagDetailActivity.this, OrientationHelper.VERTICAL, false));
            rvComment.setItemAnimator(new DefaultItemAnimator());
            redPacketReplyAdapter = new RedbagReplyAdapter(replyList);
            redPacketReplyAdapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty, null));
            redPacketReplyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (replyList.get(position).getItemType() == RedPacketReply.OTHER) {
                        startActivity(new Intent(RedbagDetailActivity.this, ShareActivity.class));
                    }
                }
            });
            redPacketReplyAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    parentId = replyList.get(position).getId();
                    showInput();
                    inputDialog.setHint(getString(R.string.replyed) + replyList.get(position).getNickName());
                }
            });
            rvComment.setAdapter(redPacketReplyAdapter);
            srlRefresh.setEnableRefresh(false);
            srlRefresh.setEnableLoadmore(true);
            srlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
                @Override
                public void onLoadmore(RefreshLayout refreshlayout) {
                    mvpPresenter.redBagCommentList(curPage + 1, Constant.PAGE_SIZE, redPacketUuid);
                }

                @Override
                public void onRefresh(RefreshLayout refreshlayout) {
                    mvpPresenter.redBagCommentList(1, Constant.PAGE_SIZE, redPacketUuid);
                }
            });
//            mvpPresenter.redPacketPoolOpen(getPref().getLatitude(), getPref().getLongitude(), redPacketUuid, getIntent().getStringExtra(Constant.PASSWORD));
            setRedbagDetail(redbagDetail);
            mvpPresenter.redBagCommentList(curPage, Constant.PAGE_SIZE, redPacketUuid);
            mvpPresenter.redBagIsFavoritedOrPraised(redPacketUuid);
        }
    }

    @Override
    public void onBackPressed() {
        if (ivBack.getVisibility() == View.VISIBLE) {
            if (fromCollect&&favoriteAndPraise!=null&&favoriteAndPraise.getIsFavorited()==0){
                setResult(RESULT_OK,getIntent());
                finish();
            }else {
                super.onBackPressed();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (nativeExpressADView != null) {
            nativeExpressADView.destroy();
        }
    }

    @OnClick({R.id.iv_back, R.id.rl_red_packet_details_header,R.id.lin_wechat,R.id.lin_weibo,R.id.lin_redbag_share, R.id.tv_reply, R.id.iv_comment, R.id.iv_collect, R.id.iv_zan, R.id.tv_url,R.id.btn_open_packet,R.id.iv_video_prev})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.rl_red_packet_details_header:
                if (redbagDetail != null &&TextUtils.equals(redPacketType,Constant.ROLETYPE_PERSON)) {
                    if (ivBack.getVisibility() == View.VISIBLE && ivJoinedArrow.getVisibility() == View.VISIBLE) {
                        IntentUtils.startRedbagJoinedActivity(this, redPacketUuid, redbagDetail.getAvatar(), redbagDetail.getNickName(), redbagDetail.getCoin(), redbagDetail.getMoney(), redbagDetail.getAreaType(), redbagDetail.getDrawNumber(), String.valueOf(redbagDetail.getQuantity()));
                    }
                } else if (redbagInfo != null) {
                    if (ivBack.getVisibility() == View.VISIBLE && ivJoinedArrow.getVisibility() == View.VISIBLE) {
                        IntentUtils.startRedbagJoinedActivity(this, redPacketUuid, redbagInfo.getSenderAvatar(), redbagInfo.getSenderNickName(), redbagInfo.getDrawCoin(), redbagInfo.getMoney(), redbagInfo.getArea(), redbagInfo.getReceiveUserNum(), redbagInfo.getRedPacketTotal());
                    }
                }
                break;
            case R.id.lin_wechat:
                CommonUtil.copyMsg(RedbagDetailActivity.this, tvWechat.getText().toString().trim());
                new DialogBuilder(RedbagDetailActivity.this)
                        .setTitle(R.string.tip)
                        .setMessage(R.string.copy_wechat)
                        .setNegativeButton(R.string.cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setPositiveButton(R.string.confirm, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                IntentUtils.startWechatApp(RedbagDetailActivity.this);
                            }
                        }).create().show();
                break;
            case R.id.lin_weibo:
                CommonUtil.copyMsg(RedbagDetailActivity.this, tvWeibo.getText().toString().trim());
                new DialogBuilder(RedbagDetailActivity.this)
                        .setTitle(R.string.tip)
                        .setMessage(R.string.copy_weibo)
                        .setNegativeButton(R.string.cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setPositiveButton(R.string.confirm, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                IntentUtils.startWeiboApp(RedbagDetailActivity.this);
                            }
                        }).create().show();
                break;
            case R.id.lin_redbag_share:
                startActivity(new Intent(RedbagDetailActivity.this, ShareActivity.class));
                break;
            case R.id.tv_reply:
                parentId = 0;
                showInput();
                inputDialog.setHint("");
                break;
            case R.id.iv_comment:
                nsvContent.scrollTo(0, linComment.getTop());
                break;
            case R.id.iv_collect:
                if (favoriteAndPraise==null)
                    return;
                if (favoriteAndPraise.getIsFavorited()==1) {
                    mvpPresenter.redBagFavoriteDelete(redPacketUuid);
                } else {
                    mvpPresenter.redBagFavoriteAdd(redPacketUuid);
                }
                break;
            case R.id.iv_zan:
                if (favoriteAndPraise==null)
                    return;
                if (favoriteAndPraise.getIsPraised()==1) {
                    mvpPresenter.redBagPraiseDelete(redPacketUuid);
                } else {
                    mvpPresenter.redBagPraiseAdd(redPacketUuid);
                }
                break;
            case R.id.tv_url:
                if (redbagDetail != null) {
                    IntentUtils.startOutWebActivity(this, redbagDetail.getUrl());
                } else if (redbagInfo != null) {
                    IntentUtils.startOutWebActivity(this, redbagInfo.getUrl());
                }
                break;
            case R.id.btn_open_packet:
                startActivity(new Intent(this,AnswerActivity.class));
                break;
            case R.id.iv_video_prev:
//                IntentUtils.startVideoPreviewActivity(this,redbagDetail.get);
//                startActivity(new Intent(this,VideoPreviewActivity.class).putExtra(Constant.URL,""));
                break;

        }
    }

    public void setPicture(List<String> pictureArr) {
            mivPicture.setRadius(5);
            mivPicture.setList(pictureArr);
            mivPicture.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList.addAll(mivPicture.getImagesList());
                    IntentUtils.startImagePreviewActivity(RedbagDetailActivity.this, position, arrayList);
                }
            });
    }

    private void showInput() {
        if (inputDialog == null) {
            inputDialog = new InputDialog(this);
            inputDialog.setOnResultListener(new InputDialog.OnResultListener() {
                @Override
                public void onReplyText(String content) {
                    replyContent = content;
                    if (!TextUtils.isEmpty(replyContent)) {
                        showLoadingDialog(null);
                        if (parentId > 0) {
                            mvpPresenter.redBagCommentReply(parentId, replyContent);
                        } else {
                            mvpPresenter.redBagCommentAdd(redPacketUuid, replyContent, parentId);
                        }
                    }
                }
            });
        }
        inputDialog.show();
    }

    public void setLongClick() {
        tvContent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                CommonUtil.copyMsg(RedbagDetailActivity.this, tvContent.getText().toString().trim());
                showShortToast(R.string.copy_success);
                return false;
            }
        });
    }

    @Override
    public void setRedbagDetail(final RedbagDetail redbagDetail) {
        closeLoadingDialog();
        if (redbagDetail != null) {
            this.redbagDetail = redbagDetail;
            GlideImgManager.loadCircleImage(this, redbagDetail.getAvatar(), ivHeader);
            tvName.setText(redbagDetail.getNickName());
            if (redbagDetail.isDrawSuccess()) {
                tvKsb.setText(redbagDetail.getCoin());
                tvMoney.setText(String.format(getString(R.string.format_add_yuan), redbagDetail.getMoney()));
                tvMoney.setVisibility(View.VISIBLE);
                tvNotice.setVisibility(View.GONE);
                rlValue.setVisibility(View.VISIBLE);
                sendBroadcast(new Intent(Constant.ACTION_CHANGE_COIN));
                if (getPref().getBoolean(PrefUtil.SOUNDSWITCH,true)){
                    CommonUtil.playRing(this, R.raw.gold);
                }
            } else {
                tvNotice.setText(redbagDetail.getReason());
                tvNotice.setVisibility(View.VISIBLE);
                rlValue.setVisibility(View.GONE);
            }
            tvScope.setText(redbagDetail.getAreaType());
            if (redbagDetail.getDrawAvatar() != null) {
                if (redbagDetail.getDrawAvatar().size() >= 1) {
                    GlideImgManager.loadCircleImage(this, redbagDetail.getDrawAvatar().get(0), ivDrawAvatar1);
                }
                if (redbagDetail.getDrawAvatar().size() >= 2) {
                    GlideImgManager.loadCircleImage(this, redbagDetail.getDrawAvatar().get(1), ivDrawAvatar2);
                }
                if (redbagDetail.getDrawAvatar().size() >= 3) {
                    GlideImgManager.loadCircleImage(this, redbagDetail.getDrawAvatar().get(2), ivDrawAvatar3);
                }
            }
            setLongClick();
            if (TextUtils.equals(roleType,Constant.ROLETYPE_SYSTEM)) {
                tvReceiveNum.setText(String.format(getString(R.string.format_people_get_redbag_money), redbagDetail.getDrawNumber()));
                if (redbagDetail.getSystemAd() != null && redbagDetail.getSystemAd().size() != 0) {
                    AdCollectionView adCollectionView = new AdCollectionView(this, linAd);
                    adCollectionView.init(AdCollectionView.TYPE_SYSTEM, AdCollectionView.LOCATION_REDBAG, redbagDetail.getSystemAd().get(0).getPicture(), redbagDetail.getSystemAd().get(0).getJumpUrl());
                    nativeExpressADView = adCollectionView.getNativeExpressADView();
                }
            } else {
                tvReceiveNum.setText(String.format(getString(R.string.format_people_get_redbag), redbagDetail.getDrawNumber()));
                tvContent.setText(redbagDetail.getContent());
                if (redbagDetail != null && redbagDetail.getPicture().size() > 0) {
                    List<String> picArr=new ArrayList<>();
                    for (PictureBean bean: redbagDetail.getPicture()) {
                        picArr.add(bean.getPicture());
                    }
                    setPicture(picArr);
                }
                if (TextUtils.isEmpty(redbagDetail.getUrl())) {
                    tvUrl.setVisibility(View.GONE);
                } else {
                    tvUrl.setVisibility(View.VISIBLE);
                    tvUrl.setText(redbagDetail.getUrlName());
                }
                if (TextUtils.isEmpty(redbagDetail.getWechat())) {
                    linWechat.setVisibility(View.GONE);
                } else {
                    linWechat.setVisibility(View.VISIBLE);
                    tvWechat.setText(redbagDetail.getWechat());
                }
                if (TextUtils.isEmpty(redbagDetail.getMicroblog())) {
                    linWeibo.setVisibility(View.GONE);
                } else {
                    linWeibo.setVisibility(View.VISIBLE);
                    tvWeibo.setText(redbagDetail.getMicroblog());
                }
                if (redPacketType.equals(Constant.RED_PACKET_TYPE_VIDEO)){
                    rlValue.setVisibility(View.GONE);
                    tvRedbagTip.setVisibility(View.GONE);
                    linVideoHead.setVisibility(View.VISIBLE);
                    btnOpenPacket.setText(R.string.watched_video_open_redbag);
                    tvMoney.setText(String.format(getString(R.string.format_add_yuan), redbagDetail.getMoney()));
                    tvMoney.setVisibility(View.VISIBLE);
                }else if(redPacketType.equals(Constant.RED_PACKET_TYPE_QUESTION)){
                    rlValue.setVisibility(View.GONE);
                    tvRedbagTip.setVisibility(View.GONE);
                    linVideoHead.setVisibility(View.VISIBLE);
                    btnOpenPacket.setText(R.string.answer_open_redbag);
                    tvMoney.setText(String.format(getString(R.string.format_add_yuan), redbagDetail.getMoney()));
                    tvMoney.setVisibility(View.VISIBLE);
                }else {
                    startAnim();
                }
            }
            startCountDown(redbagDetail.getReadingSecond());

        }
    }

    @Override
    public void setInfo(RedbagInfo data) {
        closeLoadingDialog();
        if (data != null) {
            this.redbagInfo=data;
            GlideImgManager.loadCircleImage(this, data.getSenderAvatar(), ivHeader);
            tvName.setText(data.getSenderNickName());
            tvKsb.setText(data.getDrawCoin());
            tvMoney.setText(String.format(getString(R.string.format_add_yuan), data.getMoney()));
            tvMoney.setVisibility(View.VISIBLE);
            tvNotice.setVisibility(View.GONE);
            rlValue.setVisibility(View.VISIBLE);
            tvScope.setText(data.getArea());
            tvReceiveNum.setText(String.format(getString(R.string.format_people_get_redbag), data.getReceiveUserNum()));
            if (data.getReceiveUserAvatar() != null) {
                if (data.getReceiveUserAvatar().size() >= 1) {
                    GlideImgManager.loadCircleImage(this, data.getReceiveUserAvatar().get(0), ivDrawAvatar1);
                }
                if (data.getReceiveUserAvatar().size() >= 2) {
                    GlideImgManager.loadCircleImage(this, data.getReceiveUserAvatar().get(1), ivDrawAvatar2);
                }
                if (data.getReceiveUserAvatar().size() >= 3) {
                    GlideImgManager.loadCircleImage(this, data.getReceiveUserAvatar().get(2), ivDrawAvatar3);
                }
            }
            setLongClick();
            if (roleType.equals(Constant.ROLETYPE_SYSTEM)) {
                tvReceiveNum.setText(String.format(getString(R.string.format_people_get_redbag_money), data.getReceiveUserNum()));
                AdCollectionView adCollectionView = new AdCollectionView(this, linAd);
                adCollectionView.init(AdCollectionView.TYPE_SYSTEM, AdCollectionView.LOCATION_REDBAG, "1108034968", "4050447759674968");
            } else {
                tvReceiveNum.setText(String.format(getString(R.string.format_people_get_redbag), data.getReceiveUserNum()));
                tvContent.setText(data.getRedPacketContent());
                if (data != null && data.getPicture().size() > 0) {
                    List<String> picArr=new ArrayList<>();
                    for (PictureBean bean:data.getPicture()) {
                        picArr.add(bean.getPicture());
                    }
                    setPicture(picArr);
                }
                if (TextUtils.isEmpty(data.getUrl())) {
                    tvUrl.setVisibility(View.GONE);
                } else {
                    tvUrl.setVisibility(View.VISIBLE);
                    tvUrl.setText(data.getUrlName());
                }
                if (TextUtils.isEmpty(data.getWechat())) {
                    linWechat.setVisibility(View.GONE);
                } else {
                    linWechat.setVisibility(View.VISIBLE);
                    tvWechat.setText(data.getWechat());
                }
                if (TextUtils.isEmpty(data.getMicroblog())) {
                    linWeibo.setVisibility(View.GONE);
                } else {
                    linWeibo.setVisibility(View.VISIBLE);
                    tvWeibo.setText(data.getMicroblog());
                }

                if (redPacketType.equals(Constant.RED_PACKET_TYPE_VIDEO)){
                    rlValue.setVisibility(View.GONE);
                    tvRedbagTip.setVisibility(View.GONE);
                    linVideoHead.setVisibility(View.VISIBLE);
                    btnOpenPacket.setText(R.string.watched_video_open_redbag);
                    tvMoney.setText(String.format(getString(R.string.format_add_yuan), redbagDetail.getMoney()));
                    tvMoney.setVisibility(View.VISIBLE);
                }else if(redPacketType.equals(Constant.RED_PACKET_TYPE_QUESTION)){
                    rlValue.setVisibility(View.GONE);
                    tvRedbagTip.setVisibility(View.GONE);
                    linVideoHead.setVisibility(View.VISIBLE);
                    btnOpenPacket.setText(R.string.answer_open_redbag);
                    tvMoney.setText(String.format(getString(R.string.format_add_yuan), redbagDetail.getMoney()));
                    tvMoney.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void setPraiseAdd(Integer data) {
        closeLoadingDialog();
        favoriteAndPraise.setIsPraised(1);
        ivZan.setImageResource(R.drawable.ic_redbag_zan_check);
    }

    @Override
    public void setPraiseDelete(Boolean data) {
        closeLoadingDialog();
        favoriteAndPraise.setIsPraised(0);
        ivZan.setImageResource(R.drawable.ic_redbag_zan_nor);
    }

    @Override
    public void setFavoriteAdd(Integer data) {
        closeLoadingDialog();
        favoriteAndPraise.setIsFavorited(1);
        ivCollect.setImageResource(R.drawable.ic_fav_check);
    }

    @Override
    public void setFavoriteDelete(Boolean data) {
        closeLoadingDialog();
        favoriteAndPraise.setIsFavorited(0);
        ivCollect.setImageResource(R.drawable.ic_fav_nor);
    }

    @Override
    public void setCommentList(Page<RedPacketReply> data) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        curPage = data.getCurrentPage();
        if (curPage == 1) {
            replyList.clear();
            commentCount=data.getTotalCount();
            initDotView(commentCount);
        }
        replyList.remove(other);
        replyList.addAll(data.getDataList());
        replyList.add(other);
        redPacketReplyAdapter.notifyDataSetChanged();
        if (data.getCurrentPage() >= data.getTotalPage()) {
            srlRefresh.setEnableLoadmore(false);
        } else {
            srlRefresh.setEnableLoadmore(true);
        }
    }

    @Override
    public void setCommentAdd(Integer data) {
        closeLoadingDialog();
        parentId = 0;
        if (inputDialog != null) {
            inputDialog.clearText();
            inputDialog.dismiss();
        }
        commentCount=commentCount+1;
        initDotView(commentCount);
        showShortToast(R.string.publish_success);
        UserInfo info = App.getInstance().getUserInfo();
        if (info != null) {
            RedPacketReply reply = new RedPacketReply();
            reply.setId(data);
            reply.setAvatar(info.getAvatar());
            reply.setNickName(info.getNickName());
            reply.setContent(replyContent);
            reply.setIsCanReply(0);
            reply.setCommentList(new ArrayList<String>());
            redPacketReplyAdapter.addData(0, reply);
        }
    }

    @Override
    public void setCommentReply(Integer data) {
        closeLoadingDialog();
        commentCount=commentCount+1;
        initDotView(commentCount);
        for (RedPacketReply reply : replyList) {
            if (reply.getId() == parentId) {
                reply.getCommentList().add(replyContent);
                redPacketReplyAdapter.notifyDataSetChanged();
                break;
            }
        }
        parentId = 0;
        if (inputDialog != null) {
            inputDialog.clearText();
            inputDialog.dismiss();
        }
        showShortToast(R.string.publish_success);
    }

    @Override
    public void setIsFavoritedAndPraised(FavoriteAndPraise data) {
        this.favoriteAndPraise=data;
        if (favoriteAndPraise.getIsPraised()==1) {
            ivZan.setImageResource(R.drawable.ic_redbag_zan_check);
        } else {
            ivZan.setImageResource(R.drawable.ic_redbag_zan_nor);
        }
        if (favoriteAndPraise.getIsFavorited()==1) {
            ivCollect.setImageResource(R.drawable.ic_fav_check);
        } else {
            ivCollect.setImageResource(R.drawable.ic_fav_nor);
        }
    }

    @Override
    public void setError(String msg) {
        srlRefresh.finishRefresh();
        srlRefresh.finishLoadmore();
        closeLoadingDialog();
        showShortToast(msg);
    }

    public void startAnim() {
        llTopLl.post(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator translationY = ObjectAnimator.ofFloat(linContent, "translationY", 0, -llTopLl.getHeight());
                ObjectAnimator alpha = ObjectAnimator.ofFloat(llTopLl, "alpha", 1.0f, 0f);
                AnimatorSet set = new AnimatorSet();
                set.play(translationY).with(alpha);
                set.setDuration(2000);
                set.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        linContent.setTranslationY(0);
                        llTopLl.setVisibility(View.GONE);
                        linTitleCenter.setVisibility(View.VISIBLE);
                        tvTitle.setText(redbagDetail.getCoin());
                        tvTitle.setTextColor(getResources().getColor(R.color.text_white));
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                set.start();
            }
        });
    }
    private void startCountDown(int second) {
        addSubscription(RxUtil.countdown(second)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        ivBack.setVisibility(View.GONE);
                        tvBack.setVisibility(View.VISIBLE);
                    }
                })
                .subscribe(new CountDownSubscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        ivBack.setVisibility(View.VISIBLE);
                        tvBack.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ivBack.setVisibility(View.VISIBLE);
                        tvBack.setVisibility(View.GONE);
                        showShortToast(e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        super.onNext(integer);
                        tvBack.setText(String.format(getString(R.string.format_second_unit), integer));
                    }
                }));
    }
    public void initDotView(int count) {
        if (badgeView == null)
            badgeView = new BadgeView(this, ivComment);
        badgeView.setText(String.valueOf(count));
        badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        badgeView.setTextColor(getResources().getColor(R.color.text_white));
        badgeView.setBadgeBackgroundColor(Color.RED);
        badgeView.setTextSize(6);
        int width = CommonUtil.dip2px(this, 8);
        badgeView.setHeight(width);
        badgeView.setBadgeMargin(CommonUtil.dip2px(this, 5), CommonUtil.dip2px(this, 5)); //各边间隔
        if (count > 0) {
            badgeView.show();
        } else {
            badgeView.hide();
        }
    }
}
