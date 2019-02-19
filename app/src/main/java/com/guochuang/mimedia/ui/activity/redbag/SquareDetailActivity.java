package com.guochuang.mimedia.ui.activity.redbag;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.base.MvpActivity;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.FavoriteAndPraise;
import com.guochuang.mimedia.mvp.model.PictureBean;
import com.guochuang.mimedia.mvp.model.RedPacketReply;
import com.guochuang.mimedia.mvp.model.RedbagAd;
import com.guochuang.mimedia.mvp.model.RedbagDetail;
import com.guochuang.mimedia.mvp.model.UserInfo;
import com.guochuang.mimedia.mvp.presenter.SquareDetailPresenter;
import com.guochuang.mimedia.mvp.view.SquareDetailView;
import com.guochuang.mimedia.tools.CommonUtil;
import com.guochuang.mimedia.tools.Constant;
import com.guochuang.mimedia.tools.DialogBuilder;
import com.guochuang.mimedia.tools.IntentUtils;
import com.guochuang.mimedia.tools.glide.GlideImgManager;
import com.guochuang.mimedia.ui.activity.common.ShareActivity;
import com.guochuang.mimedia.ui.adapter.RedbagReplyAdapter;
import com.guochuang.mimedia.ui.dialog.InputDialog;
import com.guochuang.mimedia.view.BadgeView;
import com.guochuang.mimedia.view.MultiImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.sz.gcyh.KSHongBao.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SquareDetailActivity extends MvpActivity<SquareDetailPresenter> implements SquareDetailView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_red_packet_details_header)
    ImageView ivHeader;
    @BindView(R.id.tv_red_packet_details_name)
    TextView tvName;
    @BindView(R.id.tv_red_packet_details_content)
    TextView tvContent;
    @BindView(R.id.miv_picture)
    MultiImageView mivPicture;
    @BindView(R.id.tv_url)
    TextView tvUrl;
    @BindView(R.id.lin_redbag_detail)
    LinearLayout linRedbagDetail;
    @BindView(R.id.iv_ad)
    ImageView ivAd;
    @BindView(R.id.rv_red_packet_details)
    RecyclerView rvComment;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;
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
    @BindView(R.id.nsv_content)
    NestedScrollView nsvContent;

    RedbagReplyAdapter redPacketReplyAdapter;
    List<RedPacketReply> replyList = new ArrayList<>();
    RedPacketReply other;
    RedbagDetail data;
    RedbagAd ad;
    int curPage = 1;
    String replyContent;
    long parentId = 0;
    String redPacketUuid;
    InputDialog inputDialog;
    FavoriteAndPraise favoriteAndPraise;
    int commentCount=0;
    BadgeView badgeView;

    @Override
    protected SquareDetailPresenter createPresenter() {
        return new SquareDetailPresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_square_detail;
    }

    @Override
    public void initViewAndData() {
        redPacketUuid = getIntent().getStringExtra(Constant.RED_PACKET_UUID);
        showLoadingDialog(null);
        setStatusbar(R.color.bg_red, false);
        mvpPresenter.getSquareDetail(redPacketUuid);
            linRedbagDetail.setVisibility(View.VISIBLE);
            ivAd.setVisibility(View.GONE);
            linComment.setVisibility(View.VISIBLE);
            linReply.setVisibility(View.VISIBLE);
            other = new RedPacketReply();
            other.setId(0);
            rvComment.setLayoutManager(new LinearLayoutManager(SquareDetailActivity.this, OrientationHelper.VERTICAL, false));
            rvComment.setItemAnimator(new DefaultItemAnimator());
            redPacketReplyAdapter = new RedbagReplyAdapter(replyList);
            redPacketReplyAdapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty,null));
            redPacketReplyAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
           redPacketReplyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (replyList.get(position).getItemType()==RedPacketReply.OTHER){
                    startActivity(new Intent(SquareDetailActivity.this,ShareActivity.class));
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

    @Override
    public void onBackPressed() {
        if (ivBack.getVisibility() == View.VISIBLE) {
            super.onBackPressed();
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_reply, R.id.iv_ad, R.id.iv_comment, R.id.iv_collect, R.id.iv_zan, R.id.tv_url})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_ad:
                if (ad != null && !TextUtils.isEmpty(ad.getJumpUrl())) {
                    IntentUtils.startOutWebActivity(this, ad.getJumpUrl());
                }
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
                if (data != null) {
                    IntentUtils.startOutWebActivity(this, data.getUrl());
                }
                break;
        }
    }

    public void setPicture() {
        if (data != null && data.getPicture().size() > 0) {
            List<String> pictureArr = new ArrayList<>();
            for (PictureBean bean : data.getPicture()) {
                pictureArr.add(bean.getPicture());
            }
            mivPicture.setRadius(5);
            mivPicture.setList(pictureArr);
            mivPicture.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList.addAll(mivPicture.getImagesList());
                    IntentUtils.startImagePreviewActivity(SquareDetailActivity.this, position, arrayList);
                }
            });
        }
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
        linWechat.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                CommonUtil.copyMsg(SquareDetailActivity.this, tvWechat.getText().toString().trim());
                new DialogBuilder(SquareDetailActivity.this)
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
                                IntentUtils.startWechatApp(SquareDetailActivity.this);
                            }
                        }).create().show();
                return false;
            }
        });
        linWeibo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                CommonUtil.copyMsg(SquareDetailActivity.this, tvWeibo.getText().toString().trim());
                new DialogBuilder(SquareDetailActivity.this)
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
                                IntentUtils.startWeiboApp(SquareDetailActivity.this);
                            }
                        }).create().show();
                return false;
            }
        });
    }

    @Override
    public void setData(final RedbagDetail data) {
        closeLoadingDialog();
        if (data != null) {
            this.data = data;
            GlideImgManager.loadCircleImage(this, data.getAvatar(), ivHeader);
            tvName.setText(data.getNickName());
            setLongClick();
                tvContent.setText(data.getContent());
                setPicture();
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
