package com.guochuang.mimedia.http.retrofit;

import com.guochuang.mimedia.http.response.HttpResponse;
import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.AdInfo;
import com.guochuang.mimedia.mvp.model.Address;
import com.guochuang.mimedia.mvp.model.AlipayAccout;
import com.guochuang.mimedia.mvp.model.Area;
import com.guochuang.mimedia.mvp.model.BenefitType;
import com.guochuang.mimedia.mvp.model.BidPrice;
import com.guochuang.mimedia.mvp.model.BoardDetail;
import com.guochuang.mimedia.mvp.model.CalValue;
import com.guochuang.mimedia.mvp.model.CityBidRecord;
import com.guochuang.mimedia.mvp.model.DrawStatistics;
import com.guochuang.mimedia.mvp.model.GeoCode;
import com.guochuang.mimedia.mvp.model.HomeRegion;
import com.guochuang.mimedia.mvp.model.IncomeDetail;
import com.guochuang.mimedia.mvp.model.IncomeStatistics;
import com.guochuang.mimedia.mvp.model.DictionaryType;
import com.guochuang.mimedia.mvp.model.InfoItem;
import com.guochuang.mimedia.mvp.model.InviterUser;
import com.guochuang.mimedia.mvp.model.KsbRecord;
import com.guochuang.mimedia.mvp.model.KsbTransfer;
import com.guochuang.mimedia.mvp.model.KsbTrend;
import com.guochuang.mimedia.mvp.model.CurrentRegion;
import com.guochuang.mimedia.mvp.model.CityBidHall;
import com.guochuang.mimedia.mvp.model.LuckyConfig;
import com.guochuang.mimedia.mvp.model.LuckyResult;
import com.guochuang.mimedia.mvp.model.MegviiSerach;
import com.guochuang.mimedia.mvp.model.Message;
import com.guochuang.mimedia.mvp.model.MyKsb;
import com.guochuang.mimedia.mvp.model.MyKsbGrantRec;
import com.guochuang.mimedia.mvp.model.MyKsbTransRec;
import com.guochuang.mimedia.mvp.model.MyRegion;
import com.guochuang.mimedia.mvp.model.MyWechat;
import com.guochuang.mimedia.mvp.model.NameAuthAndSafety;
import com.guochuang.mimedia.mvp.model.NameAuthGet;
import com.guochuang.mimedia.mvp.model.CommentInfo;
import com.guochuang.mimedia.mvp.model.CommentRedbag;
import com.guochuang.mimedia.mvp.model.NestAuctionMsg;
import com.guochuang.mimedia.mvp.model.NestAuctionRecord;
import com.guochuang.mimedia.mvp.model.NestInfoLimit;
import com.guochuang.mimedia.mvp.model.NestTemplate;
import com.guochuang.mimedia.mvp.model.Order;
import com.guochuang.mimedia.mvp.model.QrCode;
import com.guochuang.mimedia.mvp.model.RainMsg;
import com.guochuang.mimedia.mvp.model.RainRecord;
import com.guochuang.mimedia.mvp.model.RecommedUser;
import com.guochuang.mimedia.mvp.model.RecommendData;
import com.guochuang.mimedia.mvp.model.RecommendDetail;
import com.guochuang.mimedia.mvp.model.RedbagBenefit;
import com.guochuang.mimedia.mvp.model.RedbagInfo;
import com.guochuang.mimedia.mvp.model.RedbagReceived;
import com.guochuang.mimedia.mvp.model.RedbagRecord;
import com.guochuang.mimedia.mvp.model.BindingPhone;
import com.guochuang.mimedia.mvp.model.Captcha;
import com.guochuang.mimedia.mvp.model.CardList;
import com.guochuang.mimedia.mvp.model.Category;
import com.guochuang.mimedia.mvp.model.FavoriteAndPraise;
import com.guochuang.mimedia.mvp.model.InfoDetail;
import com.guochuang.mimedia.mvp.model.RedPacketReply;
import com.guochuang.mimedia.mvp.model.RedbagDetail;
import com.guochuang.mimedia.mvp.model.RedbagTemp;
import com.guochuang.mimedia.mvp.model.RedbagUser;
import com.guochuang.mimedia.mvp.model.RegionCore;
import com.guochuang.mimedia.mvp.model.RegionCoreHome;
import com.guochuang.mimedia.mvp.model.RegionDetail;
import com.guochuang.mimedia.mvp.model.RegistUser;
import com.guochuang.mimedia.mvp.model.Reply;
import com.guochuang.mimedia.mvp.model.Redbag;
import com.guochuang.mimedia.mvp.model.SafeCenter;
import com.guochuang.mimedia.mvp.model.SetUpUser;
import com.guochuang.mimedia.mvp.model.ShareBenefit;
import com.guochuang.mimedia.mvp.model.Snatch;
import com.guochuang.mimedia.mvp.model.SnatchAddress;
import com.guochuang.mimedia.mvp.model.SnatchShow;
import com.guochuang.mimedia.mvp.model.Square;
import com.guochuang.mimedia.mvp.model.UploadFile;
import com.guochuang.mimedia.mvp.model.UserInfo;
import com.guochuang.mimedia.mvp.model.UserStatistics;
import com.guochuang.mimedia.mvp.model.VersionMsg;


import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Administrator on 2017/10/26.
 */

public interface ApiStore {
    //获取分类资讯
    @GET("/api/v1/information/recommend/get")
    Observable<HttpResponse<Page<InfoItem>>> getRecommendList(
            @Query("currentPage") int currentPage,
            @Query("pageSize") int pageSize,
            @Query("categoryId") int categoryId);

    //查询资讯
    @GET("/api/v1/information/recommend/get")
    Observable<HttpResponse<Page<InfoItem>>> getRecommendSearch(
            @Query("currentPage") int currentPage,
            @Query("pageSize") int pageSize,
            @Query("title") String title);

    //开始阅读
    @FormUrlEncoded
    @POST("/api/v1/information/reading/begin_read")
    Observable<HttpResponse<InfoDetail>> begin_read(
            @Field("articleUuid") String articleUuid);

    //结束阅读
    @FormUrlEncoded
    @POST("/api/v1/information/reading/end_read")
    Observable<HttpResponse<String>> end_read(
            @Field("articleUuid") String articleUuid);

    //查询评论列表
    @GET("/api/v1/information/comment/article_uuid")
    Observable<HttpResponse<Page<Reply>>> commentGet(
            @Query("currentPage") int currentPage,
            @Query("pageSize") int pageSize,
            @Query("articleUuid") String articleUuid);

    //添加评论
    @FormUrlEncoded
    @POST("/api/v1/information/comment/add")
    Observable<HttpResponse<Integer>> commentAdd(
            @Field("articleUuid") String articleUuid,
            @Field("content") String content,
            @Field("parentId") long parentId);

    @FormUrlEncoded
    @POST("/api/v1/information/comment/delete")
    Observable<HttpResponse<Boolean>> infoCommentDelete(
            @Field("commentId") long commentId);

    @GET("/api/v1/information/comment/user_account_uuid")
    Observable<HttpResponse<Page<CommentInfo>>> getUserInfoComment(
            @Query("currentPage") int currentPage,
            @Query("pageSize") int pageSize);

    //添加收藏
    @FormUrlEncoded
    @POST("/api/v1/information/favorite/add")
    Observable<HttpResponse<Integer>> favoriteAdd(
            @Field("articleUuid") String articleUuid);

    //删除收藏
    @FormUrlEncoded
    @POST("/api/v1/information/favorite/delete")
    Observable<HttpResponse<Boolean>> favoriteDelete(
            @Field("articleUuid") String articleUuid);

    //查询资讯收藏
    @GET("/api/v1/information/favorite/get")
    Observable<HttpResponse<Page<InfoItem>>> getUserInfofavorite(
            @Query("currentPage") int currentPage,
            @Query("pageSize") int pageSize);

    //添加点赞
    @FormUrlEncoded
    @POST("/api/v1/information/praise/add")
    Observable<HttpResponse<Integer>> praiseAdd(
            @Field("articleUuid") String articleUuid);

    //取消点赞
    @FormUrlEncoded
    @POST("/api/v1/information/praise/delete")
    Observable<HttpResponse<Boolean>> praiseDelete(
            @Field("articleUuid") String articleUuid);

    //添加举报内容
    @FormUrlEncoded
    @POST("/api/v1/information/report/add")
    Observable<HttpResponse<Integer>> reportAdd(
            @Field("articleUuid") String articleUuid,
            @Field("content") String content,
            @Field("types") String types);

    //阅读后，领取奖励
    @FormUrlEncoded
    @POST("/api/v1/information/reading/generate")
    Observable<HttpResponse<String>> readingGenerate(
            @Field("articleUuid") String articleUuid);

    //分享成功添加记录
    @FormUrlEncoded
    @POST("/api/v1/information/share/add")
    Observable<HttpResponse<Integer>> shareAdd(
            @Field("articleUuid") String articleUuid,
            @Field("type") String type);

    //获取资讯的分类
    @GET("/api/v1/common/category/get")
    Observable<HttpResponse<List<Category>>> categoryGet(
            @Query("systemCode") String systemCode,
            @Query("type") String type
    );

    @GET("/api/v1/common/version/get")
    Observable<HttpResponse<VersionMsg>> versionGet(
            @Query("systemCode") String systemCode,
            @Query("sequence") String sequence
    );

    @FormUrlEncoded
    @POST("/api/v1/common/feedback/add")
    Observable<HttpResponse<Integer>> feedbackAdd(
            @Field("systemCode") String systemCode,
            @Field("content") String content,
            @Field("picture") String picture
    );

    //获取系统红包
    @GET("/api/v1/redpacket/red_packet_pool/get")
    Observable<HttpResponse<List<Redbag>>> redPacketGet(
            @Query("latitude") String latitude,
            @Query("longitude") String longitude
    );

    //打开系统红包
    @FormUrlEncoded
    @POST("/api/v1/redpacket/system_red_packet/open")
    Observable<HttpResponse<RedbagDetail>> redPacketOpen(
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("systemRedPacketUuid") String systemRedPacketUuid
    );

    //打开系统红包
    @FormUrlEncoded
    @POST("/api/v1/redpacket/red_packet_pool/open")
    Observable<HttpResponse<RedbagDetail>> redPacketPoolOpen(
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("redPacketUuid") String redPacketUuid,
            @Field("password") String password
    );

    //发随机红包
    @FormUrlEncoded
    @POST("/api/v1/redpacket/person_red_packet/add_random")
    Observable<HttpResponse<String>> addRandomRedbag(
            @Field("userLatitude") String userLatitude,
            @Field("userLongitude") String userLongitude,
            @Field("redPacketLatitude") String redPacketLatitude,
            @Field("redPacketLongitude") String redPacketLongitude,
            @Field("content") String content,
            @Field("picture") String picture,
            @Field("areaType") int areaType,
            @Field("kilometer") int kilometer,
            @Field("money") Double money,
            @Field("quantity") int quantity,
            @Field("urlName") String urlName,
            @Field("url") String url,
            @Field("wechat") String wechat,
            @Field("microblog") String microblog,
            @Field("isPublicPassword") int isPublicPassword,
            @Field("isSaveTemplate") int isSaveTemplate,
            @Field("payType") int payType,
            @Field("channelCode") String channelCode,
            @Field("safetyCode") String safetyCode
    );

    //发口令红包
    @FormUrlEncoded
    @POST("/api/v1/redpacket/person_red_packet/add_password")
    Observable<HttpResponse<String>> addPasswordRedbag(
            @Field("userLatitude") String userLatitude,
            @Field("userLongitude") String userLongitude,
            @Field("redPacketLatitude") String redPacketLatitude,
            @Field("redPacketLongitude") String redPacketLongitude,
            @Field("content") String content,
            @Field("picture") String picture,
            @Field("areaType") int areaType,
            @Field("kilometer") int kilometer,
            @Field("password") String password,
            @Field("money") Double money,
            @Field("quantity") int quantity,
            @Field("urlName") String urlName,
            @Field("url") String url,
            @Field("wechat") String wechat,
            @Field("microblog") String microblog,
            @Field("isPublicPassword") int isPublicPassword,
            @Field("isSaveTemplate") int isSaveTemplate,
            @Field("payType") int payType,
            @Field("channelCode") String channelCode,
            @Field("safetyCode") String safetyCode
    );
    //发随机红包
    @FormUrlEncoded
    @POST("/api/v1/redpacket/person_red_packet/add_lucky")
    Observable<HttpResponse<String>> addLuckyRedbag(
            @Field("userLatitude") String userLatitude,
            @Field("userLongitude") String userLongitude,
            @Field("redPacketLatitude") String redPacketLatitude,
            @Field("redPacketLongitude") String redPacketLongitude,
            @Field("content") String content,
            @Field("picture") String picture,
            @Field("areaType") int areaType,
            @Field("kilometer") int kilometer,
            @Field("money") Double money,
            @Field("quantity") int quantity,
            @Field("urlName") String urlName,
            @Field("url") String url,
            @Field("wechat") String wechat,
            @Field("microblog") String microblog,
            @Field("isPublicPassword") int isPublicPassword,
            @Field("isSaveTemplate") int isSaveTemplate,
            @Field("payType") int payType,
            @Field("channelCode") String channelCode,
            @Field("safetyCode") String safetyCode
    );

    //个人发红包的记录
    @GET("/api/v1/redpacket/person_red_packet/user_account_uuid")
    Observable<HttpResponse<Page<RedbagRecord>>> getSendRedbagList(
            @Query("currentPage") int currentPage,
            @Query("pageSize") int pageSize
    );

    //上传图片
    @Multipart
    @POST("/api/v1/file/upload")
    Observable<HttpResponse<UploadFile>> fileUpload(
            @Part("businessType") String businessType,
            @Part MultipartBody.Part file
    );

    //添加点赞
    @FormUrlEncoded
    @POST("/api/v1/redpacket/praise/add")
    Observable<HttpResponse<Integer>> redBagPraiseAdd(
            @Field("redPacketUuid") String redPacketUuid);

    //删除点赞
    @FormUrlEncoded
    @POST("/api/v1/redpacket/praise/delete")
    Observable<HttpResponse<Boolean>> redBagPraiseDelete(
            @Field("redPacketUuid") String redPacketUuid);

    //添加收藏
    @FormUrlEncoded
    @POST("/api/v1/redpacket/favorite/add")
    Observable<HttpResponse<Integer>> redBagFavoriteAdd(
            @Field("redPacketUuid") String redPacketUuid);

    //删除收藏
    @FormUrlEncoded
    @POST("/api/v1/redpacket/favorite/delete")
    Observable<HttpResponse<Boolean>> redBagFavoriteDelete(
            @Field("redPacketUuid") String redPacketUuid);

    //个人收藏的红包
    @GET("/api/v1/redpacket/favorite/get")
    Observable<HttpResponse<Page<CommentRedbag>>> getFavoriteRedbag(
            @Query("currentPage") int currentPage,
            @Query("pageSize") int pageSize
    );

    //查询用户是否点赞过红包
    @GET("/api/v1/redpacket/person_red_packet/user_behavior")
    Observable<HttpResponse<FavoriteAndPraise>> redBagIsFavoritedOrPraised(
            @Query("redPacketUuid") String redPacketUuid);

    //获取红包的评论列表
    @GET("/api/v1/redpacket/comment/red_packet_uuid")
    Observable<HttpResponse<Page<RedPacketReply>>> redBagCommentList(
            @Query("currentPage") int currentPage,
            @Query("pageSize") int pageSize,
            @Query("redPacketUuid") String redPacketUuid);

    @FormUrlEncoded
    @POST("/api/v1/redpacket/comment/add")
    Observable<HttpResponse<Integer>> redBagCommentAdd(
            @Field("redPacketUuid") String redPacketUuid,
            @Field("content") String content,
            @Field("parentId") long parentId
    );

    @FormUrlEncoded
    @POST("/api/v1/redpacket/comment/delete")
    Observable<HttpResponse<Boolean>> redBagCommentDelete(
            @Field("commentId") long commentId);

    @FormUrlEncoded
    @POST("/api/v1/redpacket/comment/reply")
    Observable<HttpResponse<Integer>> redBagCommentReply(
            @Field("commentId") long commentId,
            @Field("content") String content
    );

    @GET("/api/v1/redpacket/comment/user_account_uuid")
    Observable<HttpResponse<Page<CommentRedbag>>> getUserRedbagComment(
            @Query("currentPage") int currentPage,
            @Query("pageSize") int pageSize);

    //查询是否有红包雨
    @GET("/api/v1/redpacket/red_packet_rain/get")
    Observable<HttpResponse<RainMsg>> redPacketRainGet();

    //红包雨记录
    @GET("/api/v1/redpacket/red_packet_rain/get_list")
    Observable<HttpResponse<Page<RainRecord>>> redPacketRainGetList(
            @Query("currentPage") int currentPage,
            @Query("pageSize") int pageSize
    );

    //红包雨提示
    @FormUrlEncoded
    @POST("/api/v1/redpacket/red_packet_rain/is_tip")
    Observable<HttpResponse<Boolean>> redPacketRainIsTip(
            @Field("redPacketRainUuid") String redPacketRainUuid
    );

    //领取红包雨
    @FormUrlEncoded
    @POST("/api/v1/redpacket/red_packet_rain/draw")
    Observable<HttpResponse<String>> redPacketRainDraw(
            @Field("redPacketRainUuid") String redPacketRainUuid,
            @Field("quantity") int quantity
    );

    // 手机登录
    @FormUrlEncoded
    @POST("/api/v1/user/account/login")
    Observable<HttpResponse<String>> userAccoutLogin(
            @Field("username") String username,
            @Field("password") String password,
            @Field("captcha") String captcha,
            @Field("systemCode") String systemCode,
            @Field("loginType") Integer loginType,
            @Field("networkType") String networkType,
            @Field("udid") String udid,
            @Field("deviceId") String deviceId,
            @Field("deviceName") String deviceName,
            @Field("deviceModel") String deviceModel,
            @Field("vendor") String vendor,
            @Field("operatingSystem") String operatingSystem,
            @Field("appVersion") String appVersion,
            @Field("deviceResolution") String deviceResolution,
            @Field("imei") String imei,
            @Field("pushId") String pushId);

    // 手机注册
    @FormUrlEncoded
    @POST("/api/v1/user/register/mobile")
    Observable<HttpResponse<String>> userRegisterMobile(
            @Field("tenantCode") String tenantCode,
            @Field("nationCode") String nationCode,
            @Field("mobile") String mobile,
            @Field("captcha") String captcha,
            @Field("password") String password,
            @Field("registerSource") String registerSource,
            @Field("inviteCode") String inviteCode);

    // 微信注册(参数有待修改)
    @FormUrlEncoded
    @POST("/api/v1/token/user/app_wechat_login")
    Observable<HttpResponse<String>> appWechatLogin(
            @Field("tenantCode") String tenantCode,
            @Field("systemCode") String systemCode,
            @Field("code") String code,
            @Field("loginType") Integer loginType,
            @Field("networkType") String networkType,
            @Field("udid") String udid,
            @Field("deviceId") String deviceId,
            @Field("deviceName") String deviceName,
            @Field("deviceModel") String deviceModel,
            @Field("vendor") String vendor,
            @Field("operatingSystem") String operatingSystem,
            @Field("appVersion") String appVersion,
            @Field("deviceResolution") String deviceResolution,
            @Field("imei") String imei,
            @Field("pushId") String pushId
    );

    // 绑定微信
    @POST("/api/v1/token/user/app_wechat_bind")
    Observable<HttpResponse<String>> userAppWechatBind(
            @Query("tenantCode") String tenantCode,
            @Query("systemCode") String systemCode,
            @Query("code") String code);

    // 重制密码
    @FormUrlEncoded
    @POST("/api/v1/user/account/reset_password")
    Observable<HttpResponse<String>> userResetPassword(
            @Field("nationCode") String nationCode,
            @Field("mobile") String mobile,
            @Field("captcha") String captcha,
            @Field("password") String password);

    // 图形验证码 手机注册
    @POST("/api/v1/file/captcha/register")
    Observable<HttpResponse<Captcha>> userPhoneCaptcha(
            @Query("mobile") String tenantCode);

    // 图形验证码 重制密码
    @POST("/api/v1/file/captcha/reset_password")
    Observable<HttpResponse<Captcha>> userResetPasswordCaptcha(
            @Query("mobile") String tenantCode);

    // 图形验证码 绑定手机
    @POST("/api/v1/file/captcha/bind_mobile")
    Observable<HttpResponse<Captcha>> userBindMobileCaptcha(
            @Query("mobile") String tenantCode);

    // 图形验证码 重制安全码(参数有待修改)
    @POST("/api/v1/file/captcha/reset_safe_code")
    Observable<HttpResponse<Captcha>> userResetSafeCodeCaptcha(
            @Query("mobile") String tenantCode);

    // 短信验证码 重制密码
    @POST("/api/v1/user/sms/reset_password")
    Observable<HttpResponse<String>> userSmsResetPassword(
            @Query("mobile") String mobile,
            @Query("captcha") String captcha,
            @Query("uuid") String uuid);

    // 短信验证码 注册
    @POST("/api/v1/user/sms/register")
    Observable<HttpResponse<String>> userSmsRegister(
            @Query("mobile") String mobile,
            @Query("captcha") String captcha,
            @Query("uuid") String uuid);

    // 短信验证码 绑定手机
    @POST("/api/v1/user/sms/bind_mobile")
    Observable<HttpResponse<String>> userSmsBindMobile(
            @Query("mobile") String mobile,
            @Query("captcha") String captcha,
            @Query("uuid") String uuid);

    // 短信验证-重置&设置安全码
    @POST("/api/v1/user/sms/reset_safety_code")
    Observable<HttpResponse<String>> userSmsResetSafetyCode(
            @Query("mobile") String mobile,
            @Query("captcha") String captcha,
            @Query("uuid") String uuid);

    // 绑定手机
    @POST("/api/v1/user/account/bind_mobile")
    Observable<HttpResponse<BindingPhone>> userBindPhone(
            @Query("mobile") String mobile,
            @Query("captcha") String captcha,
            @Query("userAccountUuid") String userAccountUuid,
            @Query("password") String password);

    // 获取银行卡列表
    @GET("/api/v1/user/bank_card/list")
    Observable<HttpResponse<List<CardList>>> userBandCardList();

    // 绑定银行卡
    @POST("/api/v1/user/bank_card/bind")
    Observable<HttpResponse<Boolean>> userBankCardBind(
            @Query("bankName") String bankName,
            @Query("mobile") String mobile,
            @Query("name") String name,
            @Query("cardNumber") String cardNumber,
            @Query("type") String type,
            @Query("safetyCode") String safetyCode
    );

    // 更新银行卡
    @POST("/api/v1/user/bank_card/update")
    Observable<HttpResponse<Boolean>> userBankCardUpdate(
            @Query("bankName") String bankName,
            @Query("mobile") String mobile,
            @Query("name") String name,
            @Query("cardNumber") String cardNumber,
            @Query("type") String type,
            @Query("safetyCode") String safetyCode
    );

    // 解绑银行卡
    @POST("/api/v1/user/bank_card/unbind")
    Observable<HttpResponse<Boolean>> userBankCardUnbind(
            @Query("id") String id,
            @Query("safetyCode") String safetyCode
    );

    // 身份认证-上传认证信息
    @FormUrlEncoded
    @POST("/api/v1/user/name_auth/auth")
    Observable<HttpResponse<String>> userNameAuthAuth(
            @Field("realName") String realName,
            @Field("idCard") String idCard,
            @Field("idCardPicture") String idCardPicture,
            @Field("vendorResponse") String vendorResponse
    );

    // 身份认证-获取信息
    @GET("/api/v1/user/name_auth/get")
    Observable<HttpResponse<NameAuthGet>> userNameAuthGet();

    // 身份认证-OCR认证
    @Multipart
    @POST("/faceid/v3/ocridcard")
    Observable<MegviiSerach> ocrIdCard(
            @Part("api_key") String apiKey,
            @Part("api_secret") String apiSecret,
            @Part MultipartBody.Part file
    );

    // 安全码-重置&设置
    @POST("/api/v1/user/safe/reset")
    Observable<HttpResponse<String>> userSafeReset(
            @Query("captcha") String captcha,
            @Query("safetyCode") String safetyCode
    );

    // 安全码&实名-是否已设置
    @POST("/api/v1/user/safe/status")
    Observable<HttpResponse<NameAuthAndSafety>> safeStatus();

    // 我的ksb-转赠
    @POST("/api/v1/user/transfer/add")
    Observable<HttpResponse<String>> userTransferAdd(
            @Query("doneeAddress") String doneeAddress,
            @Query("coin") double coin,
            @Query("type") String type,
            @Query("safetyCode") String safetyCode
    );

    // 我的ksb-转赠记录
    @GET("/api/v1/user/transfer/get_by_donor")
    Observable<HttpResponse<Page<MyKsbGrantRec>>> userTransferGetByDonor(
            @Query("currentPage") int currentPage,
            @Query("pageSize") Integer pageSize
    );

    // 安全中心
    @GET("/api/v1/user/info/security_center")
    Observable<HttpResponse<SafeCenter>> userInfoSecurityCenter();

    // 绑定支付宝
    @POST("/api/v1/user/info/bind_alipay")
    Observable<HttpResponse<String>> userInfoBindAlipay(
            @Query("alipayRealName") String alipayRealName,
            @Query("alipayAccount") String alipayAccount,
            @Query("safetyCode") String safetyCode
    );

    // 获取支付宝信息
    @GET("/api/v1/user/info/alipay_account")
    Observable<HttpResponse<AlipayAccout>> userInfoAlipayAccout();

    @GET("/api/v1/user/info/user_account_uuid")
    Observable<HttpResponse<UserInfo>> getUserInfo();


    @FormUrlEncoded
    @POST("/api/v1/user/info/update")
    Observable<HttpResponse<Boolean>> setNickName(
            @Field("nickName") String nickName
    );

    @FormUrlEncoded
    @POST("/api/v1/user/info/update")
    Observable<HttpResponse<Boolean>> setAvatar(
            @Field("nickName") String nickName,
            @Field("avatar") String avatar
    );


    @GET("/api/v1/user/info/set_up")
    Observable<HttpResponse<SetUpUser>> getSetupInfo();

    @FormUrlEncoded
    @POST("/api/v1/user/invite/add")
    Observable<HttpResponse<Boolean>> addInviter(
            @Field("inviteCode") String inviteCode
    );

    @GET("/api/v1/user/invite/invite_outline")
    Observable<HttpResponse<RecommendData>> getRecommendData();

    @GET("/api/v1/user/invite/my_inviter")
    Observable<HttpResponse<InviterUser>> getMyInviter();

    @GET("/api/v1/user/wallet/my_ksb")
    Observable<HttpResponse<MyKsb>> getMyKsb();

    //获取用户的币
    @GET("/api/v1/user/wallet/coin")
    Observable<HttpResponse<String>> getWalletCoin();

    // 获取用户的金额
    @GET("/api/v1/user/wallet/money")
    Observable<HttpResponse<String>> getWalletMoney();

    @GET("/api/v1/user/wallet/cal")
    Observable<HttpResponse<CalValue>> calValue(
            @Query("numericalValue") double numericalValue,
            @Query("type") int type
    );

    @GET("/api/v1/user/wechat_info/user_account_uuid")
    Observable<HttpResponse<MyWechat>> getWechatInfo();

    @GET("/api/v1/common/notice/get_scroll_bar")
    Observable<HttpResponse<List<String>>> getScrollBar();

    @GET("/api/v1/common/region/get_two")
    Observable<HttpResponse<List<Area>>> getRegion();

    @GET("/api/v1/redpacket/red_packet_template/get")
    Observable<HttpResponse<List<RedbagTemp>>> getTemplate(
            @Query("redPacketType") String redPacketType
    );

    @FormUrlEncoded
    @POST("/api/v1/redpacket/red_packet_template/delete")
    Observable<HttpResponse<Boolean>> deleteTemplate(
            @Field("templateId") long templateId
    );

    @GET("/api/v1/common/message/list")
    Observable<HttpResponse<Page<Message>>> getMessageList(
            @Query("currentPage") int currentPage,
            @Query("pageSize") Integer pageSize
    );

    @GET("/api/v1/common/message/is_news")
    Observable<HttpResponse<Boolean>> messageIsNews();

    @POST("/api/v1/user/account/logout")
    Observable<HttpResponse<String>> userAccoutLogout();

    @GET("/api/v1/user/region_delegate/get_current")
    Observable<HttpResponse<CurrentRegion>> getCurrentRegion(
            @Query("latitude") String latitude,
            @Query("longitude") String longitude
    );

    @GET("/api/v1/user/region_delegate/get_details")
    Observable<HttpResponse<RegionDetail>> getRegionDetail(
            @Query("regionId") long regionId
    );

    @GET("/api/v1/user/region_delegate/my_region")
    Observable<HttpResponse<MyRegion>> getMyRegion();

    @FormUrlEncoded
    @POST("/api/v1/user/region_delegate/is_lock")
    Observable<HttpResponse<Boolean>> cityIsLock(
            @Field("regionId") long regionId,
            @Field("isLock") int isLock
    );

    @GET("/api/v1/user/region_order/get_list")
    Observable<HttpResponse<Page<CityBidRecord>>> getBidRecord(
            @Query("currentPage") int currentPage,
            @Query("pageSize") Integer pageSize,
            @Query("regionId") long regionId
    );

    // 我的ksb-提现
    @POST("/api/v1/user/withdrawals/apply")
    Observable<HttpResponse<String>> userWithdrawals(
            @Query("money") int money,
            @Query("payType") int payType,
            @Query("id") String id,
            @Query("account") String account,
            @Query("safetyCode") String safetyCode
    );

    // 我的ksb-提现记录
    @GET("/api/v1/user/withdrawals/get")
    Observable<HttpResponse<Page<MyKsbTransRec>>> userWithdrawalsGet(
            @Query("currentPage") int currentPage,
            @Query("pageSize") int pageSize
    );

    // 我的ksb-提现限制
    @GET("/api/v1/user/withdrawals/limit")
    Observable<HttpResponse<KsbTransfer>> userWithdrawalsLimit();

    @GET("/api/v1/user/coin_money/trend_map")
    Observable<HttpResponse<List<KsbTrend>>> getKsbTrend();

    @POST("/api/v1/common/qrCode/get")
    Observable<HttpResponse<QrCode>> getQrCode();

    // 城主-竞购
    @FormUrlEncoded
    @POST("/api/v1/user/region_delegate/add")
    Observable<HttpResponse<String>> buyCityOwner(
            @Field("regionId") long regionId,
            @Field("money") double money,
            @Field("payType") int payType,
            @Field("channelCode") String channelCode,
            @Field("safetyCode") String safetyCode
    );

    // 城主-竞购大厅
    @GET("/api/v1/user/region_delegate/get_List")
    Observable<HttpResponse<Page<CityBidHall>>> userBidCityHall(
            @Query("currentPage") int currentPage,
            @Query("pageSize") int pageSize,
            @Query("latitude") String latitude,
            @Query("longitude") String longitude,
            @Query("provinceName") String provinceName,
            @Query("cityName") String cityName,
            @Query("districtName") String districtName,
            @Query("hottest") Integer hottest,
            @Query("dereliction") Integer dereliction,
            @Query("price") Integer price
    );

    @GET("/api/v1/hbase/redPacket/getRedPacketDetailsList")
    Observable<HttpResponse<List<RedbagReceived>>> getReceiveRedbag(
            @Query("type") String type,
            @Query("startIndex") String startIndex,
            @Query("pageSize") int pageSize
    );

    @GET("/api/v1/hbase/ksb/getKsbDetailsList")
    Observable<HttpResponse<List<KsbRecord>>> getKsbRecord(
            @Query("type") String type,
            @Query("startIndex") String startIndex,
            @Query("pageSize") int pageSize
    );

    @GET("/api/v1/user/invite/invite_count")
    Observable<HttpResponse<RecommendDetail>> getRecommendDetail();

    @GET("/api/v1/user/invite/market_count")
    Observable<HttpResponse<String>> getMarketCount();

    @GET("/api/v1/user/invite/direct_agent_list")
    Observable<HttpResponse<Page<RecommedUser>>> getDirectAgent(
            @Query("currentPage") int currentPage,
            @Query("pageSize") Integer pageSize,
            @Query("sort") String sort
    );

    @GET("/api/v1/user/invite/direct_fans_list")
    Observable<HttpResponse<Page<RecommedUser>>> getDirectFans(
            @Query("currentPage") int currentPage,
            @Query("pageSize") Integer pageSize,
            @Query("sort") String sort

    );

    @GET("/api/v1/redpacket/user_draw_statistics/get")
    Observable<HttpResponse<DrawStatistics>> getDrawStatistics();

    @GET("/api/v1/user/agent/agent_money")
    Observable<HttpResponse<Integer>> getAgentMoney();

    @FormUrlEncoded
    @POST("/api/v1/user/agent/upgrade_agent")
    Observable<HttpResponse<Order>> upgradeAgent(
            @Field("channelCode") String channelCode,
            @Field("payType") int payType,
            @Field("originalMoney") double originalMoney,
            @Field("safetyCode") String safetyCode
    );

    @GET("/api/v1/user/agent/user_role")
    Observable<HttpResponse<Integer>> getUserRole();

    @GET("/api/v1/hbase/ksb/getKsbBonusDetailsList")
    Observable<HttpResponse<List<ShareBenefit>>> getKsbBonusDetailsList(
            @Query("startIndex") String startIndex,
            @Query("pageSize") int pageSize
    );

    @FormUrlEncoded
    @POST("/api/v1/user/region_notice/add")
    Observable<HttpResponse<Boolean>> addRegioNotice(
            @Field("regionId") long regionId,
            @Field("content") String content,
            @Field("picture") String picture,
            @Field("urlName") String urlName,
            @Field("url") String url
    );

    @GET("/api/v1/user/region_notice/get")
    Observable<HttpResponse<BoardDetail>> getRegioNotice(
            @Query("regionId") long regionId
    );

    @FormUrlEncoded
    @POST("/api/v1/user/region_notice/update")
    Observable<HttpResponse<Boolean>> updateRegioNotice(
            @Field("regionId") long regionId,
            @Field("content") String content,
            @Field("picture") String picture,
            @Field("urlName") String urlName,
            @Field("url") String url
    );

    @GET("/api/v1/hbase/redPacket/getRedPacketUserDetails")
    Observable<HttpResponse<List<RedbagUser>>> getRedbagUserDetail(
            @Query("redPacketUuid") String redPacketUuid,
            @Query("startIndex") String startIndex,
            @Query("pageSize") int pageSize
    );

    @GET("/api/v1/hbase/redPacket/getRedPacketInfo")
    Observable<HttpResponse<RedbagInfo>> getRedPacketInfo(
            @Query("redPacketUuid") String redPacketUuid,
            @Query("redPacketType") String redPacketType,
            @Query("startIndex") String startIndex
    );

    @GET("/api/v1/user/region_center/get")
    Observable<HttpResponse<RegionCore>> getRegionCore();

    @GET("/api/v1/user/region_center/home")
    Observable<HttpResponse<RegionCoreHome>> getRegionCoreHome();

    @GET("/api/v1/user/user_statistic/list")
    Observable<HttpResponse<Page<RegistUser>>> getIncreaseUser(
            @Query("currentPage") int currentPage,
            @Query("pageSize") int pageSize,
            @Query("beginRegisterDate") String beginRegisterDate,
            @Query("endRegisterDate") String endRegisterDate
    );

    @GET("/api/v1/user/user_statistic/get")
    Observable<HttpResponse<UserStatistics>> getUserStatistics(
            @Query("beginRegisterDate") String beginRegisterDate,
            @Query("endRegisterDate") String endRegisterDate
    );

    @GET("/api/v1/user/region_center/statistics")
    Observable<HttpResponse<IncomeStatistics>> getIncomeStatistics(
            @Query("statisticsId") long statisticsId,
            @Query("parentType") String parentType,
            @Query("sonType") String sonType,
            @Query("beginTime") String beginTime,
            @Query("endTime") String endTime
    );

    @GET("/api/v1/user/region_center/detail")
    Observable<HttpResponse<Page<IncomeDetail>>> getIncomeDetail(
            @Query("currentPage") int currentPage,
            @Query("pageSize") int pageSize,
            @Query("statisticsId") long statisticsId,
            @Query("parentType") String parentType,
            @Query("sonType") String sonType,
            @Query("beginTime") String beginTime,
            @Query("endTime") String endTime
    );
    @GET("/api/v1/common/dictionary/get_list")
    Observable<HttpResponse<List<DictionaryType>>> getDictionaryType(
            @Query("type") String type);

    @GET("/api/v1/user/region_order/premium")
    Observable<HttpResponse<Page<BidPrice>>> getBidPrice(
            @Query("currentPage") int currentPage,
            @Query("pageSize") int pageSize);

    @GET("/api/v1/user/region_order/screen")
    Observable<HttpResponse<List<BenefitType>>> getBenefitType();

    @GET("/api/v1/user/region_order/profit")
    Observable<HttpResponse<Page<RedbagBenefit>>> getRedbagBenefit(
            @Query("currentPage") int currentPage,
            @Query("pageSize") int pageSize,
            @Query("cityName") String cityName,
            @Query("districtName") String districtName);

    @GET("/api/v1/redpacket/red_packet_square/get_list")
    Observable<HttpResponse<Page<Square>>> getSquareList(
            @Query("currentPage") int currentPage,
            @Query("pageSize") int pageSize,
            @Query("latitude") String latitude,
            @Query("longitude") String longitude
    );

    @GET("/api/v1/user/region_delegate/get")
    Observable<HttpResponse<HomeRegion>> getHomeRegion(
            @Query("latitude") String latitude,
            @Query("longitude") String longitude
    );
    @GET("/api/v1/redpacket/red_packet_pool/get_kilometre")
    Observable<HttpResponse<Integer>> getKilometre();

    @GET("/api/v1/redpacket/lucky_config/get")
    Observable<HttpResponse<LuckyConfig>> getLuckyConfig();

    @FormUrlEncoded
    @POST("/api/v1/redpacket/person_red_packet/open")
    Observable<HttpResponse<LuckyResult>> openLuckyRedPacket(
            @Field("redPacketUuid") String redPacketUuid);

    @GET("/api/v1/common/geocoder/geocode")
    Observable<HttpResponse<GeoCode>> getGeocode(
            @Query("latitude") String latitude,
            @Query("longitude") String longitude,
            @Query("address") String address
    );
    @GET("/api/v1/redpacket/red_packet_square/details")
    Observable<HttpResponse<RedbagDetail>> getSquareDetail(
            @Query("redPacketUuid") String redPacketUuid
    );

    @GET("/api/v1/common/ad/get_vendor_ad")
    Observable<HttpResponse<AdInfo>> commonAdGetVedorAd();

    @GET("/api/v1/redpacket/red_packet_pool/get_location")
    Observable<HttpResponse<List<Redbag>>> getLocationRedabg(
            @Query("latitude") String latitude,
            @Query("longitude") String longitude
    );
    @GET
    Observable<ResponseBody> downloadPicture(@Url String fileUrl);

    @FormUrlEncoded
    @POST("/api/v1/user/name_auth/manual_audit")
    Observable<HttpResponse<Integer>> manualAudit(
            @Field("realName") String realName,
            @Field("idCard") String idCard,
            @Field("idCardPicture") String idCardPicture,
            @Field("idCardBackPicture") String idCardBackPicture
    );
    @FormUrlEncoded
    @POST("/api/v1/activity/beehiveDetails/appCreateOrder")
    Observable<HttpResponse<Order>> appCreateOrder(
            @Field("channelCode") String channelCode,
            @Field("payNumber") int payNumber,
            @Field("payType") int payType,
            @Field("longitude") String longitude,
            @Field("latitude") String latitude,
            @Field("safetyCode") String safetyCode
    );
    @FormUrlEncoded
    @POST("/api/v1/user/userAddress/add")
    Observable<HttpResponse<Boolean>> userAddressAdd(
            @Field("trackName") String trackName,
            @Field("trackMobile") String trackMobile,
            @Field("province") String province,
            @Field("city") String city,
            @Field("district") String district,
            @Field("address") String address,
            @Field("isDefault") int isDefault
    );
    @FormUrlEncoded
    @POST("/api/v1/user/userAddress/del")
    Observable<HttpResponse<Boolean>> userAddressDel(
            @Field("userAddressUuid") String userAddressUuid
    );
    @FormUrlEncoded
    @POST("/api/v1/user/userAddress/update")
    Observable<HttpResponse<Boolean>> userAddressUpdate(
            @Field("userAddressUuid") String userAddressUuid,
            @Field("trackName") String trackName,
            @Field("trackMobile") String trackMobile,
            @Field("province") String province,
            @Field("city") String city,
            @Field("district") String district,
            @Field("address") String address
    );
    @GET("/api/v1/user/userAddress/pagelist")
    Observable<HttpResponse<Page<Address>>> userAddressList(
            @Query("currentPage") int currentPage,
            @Query("pageSize") int pageSize
    );
    @GET("/api/v1/activity/snatchRecord/pageSnatchRecordlist")
    Observable<HttpResponse<Page<Snatch>>> getSnatchRecordlist(
            @Query("currentPage") int currentPage,
            @Query("pageSize") int pageSize
    );
    @FormUrlEncoded
    @POST("/api/v1/activity/snatchAddress/setSnatchwinAddress")
    Observable<HttpResponse<Boolean>> setWinAddress(
            @Field("snatchId") long snatchId,
            @Field("userAddressUuid") String userAddressUuid
    );
    @GET("/api/v1/activity/snatchAddress/querySnatchwinAddress")
    Observable<HttpResponse<SnatchAddress>> getSnatchDetail(
            @Query("snatchId") long currentPage
    );
    @FormUrlEncoded
    @POST("/api/v1/activity/snatchShow/add")
    Observable<HttpResponse<Boolean>> addSnatchShow(
            @Field("snatchId") long snatchId,
            @Field("content") String content,
            @Field("imgs") String imgs
    );
    @GET("/api/v1/activity/snatchShow/queryShow")
    Observable<HttpResponse<SnatchShow>> querySnatchShow(
            @Query("snatchRecordId") long snatchRecordId
    );
    @FormUrlEncoded
    @POST("/api/v1/activity/snatch/create_order")
    Observable<HttpResponse<Order>> createSnatchOrder(
            @Field("channelCode") String channelCode,
            @Field("payType") int payType,
            @Field("snatchId") long snatchId,
            @Field("buyCount") int buyCount,
            @Field("longitude") String longitude,
            @Field("latitude") String latitude,
            @Field("safetyCode") String safetyCode
    );
    @GET("/api/v1/activity/snatch/get_vendor_pay")
    Observable<HttpResponse<Order>> getOrderVendor(
            @Query("orderId") long orderId
    );

    @GET("/api/v1/nest_info/delete_template")
    Observable<HttpResponse<Boolean>> deleteNestTemplate(
            @Query("nestTemplateId") long nestTemplateId
    );
    @FormUrlEncoded
    @POST("/api/v1/nest_info/edit")
    Observable<HttpResponse<Boolean>> nesteEdit(
            @Field("nestInfoId") long nestInfoId,
            @Field("nestLocationId") long nestLocationId,
            @Field("nestTimeInfoId") long nestTimeInfoId,
            @Field("shortMsg") String shortMsg,
            @Field("coverPicture") String coverPicture,
            @Field("introduction") String introduction,
            @Field("pictures") String pictures,
            @Field("title") String title,
            @Field("contactPhone") String contactPhone,
            @Field("address") String address,
            @Field("addressDetail") String addressDetail,
            @Field("addressLat") String addressLat,
            @Field("addressLng") String addressLng,
            @Field("linkText") String linkText,
            @Field("linkUrl") String linkUrl,
            @Field("wechat") String wechat,
            @Field("weibo") String weibo,
            @Field("isSaveTemplate") int isSaveTemplate
    );
    @GET("/api/v1/nest_info/limit")
    Observable<HttpResponse<NestInfoLimit>> nesteLimit();

    @GET("/api/v1/nest_info/template_count")
    Observable<HttpResponse<Integer>> nesteTemplateCount();

    @GET("/api/v1/nest_info/template_list")
    Observable<HttpResponse<List<NestTemplate>>> nesteTemplateList();

    @GET("/api/v1/nest_time_auction/get")
    Observable<HttpResponse<NestAuctionMsg>> nestAuctionMsg();

    @GET("/api/v1/nest_time_auction/list")
    Observable<HttpResponse<Page<NestAuctionRecord>>> nestAuctionList(
            @Query("nestTimeInfoId") long nestTimeInfoId,
            @Query("currentPage") int currentPage,
            @Query("pageSize") int pageSize

    );


}