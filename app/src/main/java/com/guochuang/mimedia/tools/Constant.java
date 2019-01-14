package com.guochuang.mimedia.tools;

import android.os.Environment;

import com.sz.gcyh.KSHongBao.BuildConfig;
import com.guochuang.mimedia.app.App;
import com.guochuang.mimedia.http.retrofit.ApiClient;

import java.io.File;

/**
 * Created by Administrator on 2017/10/26.
 */

public class Constant {
    /**
     * 是否调试模式
     */
    public static final boolean isDebug = (BuildConfig.ENV == 0);
    public static final int HTTP_STATUS_OK = 1;
    public static final int HTTP_STATUS_FORCE_LOGIN = 1007;
    public static final int HTTP_STATUS_REDBAD_INVALID = 10012;
    public static final String PARAMS_H_TIME = "h-time";
    public static final String PARAMS_H_TENANT_CODE = "h-tenant-code";
    public static final String PARAMS_H_API_TOEKN = "h-api-token";
    public static final String PARAMS_H_NONCE = "h-nonce";

    public static final int PAGE_SIZE = 10;
    public static final int REQUEST_GET_LOCATION = 3;
    public static final int REQUEST_DEFAULT = 0;
    public static final int REQUEST_SET_NICKNAME = 4;
    public static final int REQUEST_SET_INVITER = 5;
    public static final int REQUEST_PICK_PICTURE = 6;
    public static final int REQUEST_TEMPLATE = 7;
    public static final int REQUEST_PURCHASE = 8;
    public static final int REQUEST_SET_BOARD = 9;
    public static final int REQUEST_COLLECT = 10;
    public static final int REQUEST_TRANSFER = 11;
    public static final int REQUEST_GRANT = 12;
    public static final int REQUEST_CARD_FRONT = 13;
    public static final int REQUEST_CARD_BACK = 14;
    public static final int REQUEST_PICK_ICON = 15;


    public static final String TITLE = "title";
    public static final String ARTICLEUUID = "articleUuid";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String CARDID = "cardId";
    public static final String NAME = "name";
    public static final String CARDNUMBER = "cardNumber";
    public static final String CARDMOBILE = "cardMobile";
    public static final String NICKNAME = "nickName";
    public static final String INVITER = "inviter";
    public static final String FROMLOGIN = "fromLogin";
    public static final String TEMPLATE = "template";
    public static final String URL = "url";
    public static final String CHANNEL_CODE_ANDROID = "android";
    public static final String REGIONID = "regionId";
    public static final String PURCHASE_TYPE = "purchase_type";
    public static final String MONEY = "money";
    public static final String KSB = "KSB";
    public static final String RECOMMENDDATA = "recommendData";
    public static final String AVATAR ="avatar" ;
    public static final String AREATYPE = "areaType";
    public static final String DRAWNUMBER = "drawNumber";
    public static final String TOTAL = "total";
    public static final String PASSWORD = "password";
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";
    public static final String STATISTICSID = "statisticsId";
    public static final String PARENT_TYPE = "parentType";
    public static final String SON_TYPE = "sonType";
    public static final String INCOME_TYPE = "income_type";
    public static final String TIME_TYPE = "time_type";
    public static final String ISAGENT = "isAgent";
    public static final String PAYNUMBER = "payNumber";
    public static final String DESCRIPTION = "description";


    public static final String FORMAT_DATE_SIMPLE = "yyyy-MM-dd";
    public static final String FORMAT_DATE = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_MONTH = "yyyy-MM";

    public static final String TYPE_REPORT_INFORMATION = "informationReport";
    public static final String TYPE_REPORT_REDPACKET = "redPacketReport";
    public static final String TYPE_BEENEST_REPORT = "beenestReport";

    //资讯详情
    public static final String URL_INFOMATION_DETAIL = ApiClient.HTML_URL+"information/detail/index.htm";
    //推荐协议
    public static final String URL_RULE_RECOMMEND = ApiClient.HTML_URL + "html/rule/recommend/index.htm";
    //ksb协议
    public static final String URL_RULE_KSB = ApiClient.HTML_URL + "html/rule/ksb/index.htm";
    //平台协议
    public static final String URL_SEND_REDBAG = ApiClient.HTML_URL + "html/platformRules.html";
    //疯狂规则
    public static final String URL_CRAZY_RULE = ApiClient.HTML_URL + "html/rule/crazy/index.htm";
    //代理规则
    public static final String URL_AGENT_RULE = ApiClient.HTML_URL + "project/html/rule/agentRule.html";
    //城主规则
    public static final String URL_REGION_RULE = ApiClient.HTML_URL + "html/rule/region/index.htm";
    //竞购协议
    public static final String URL_AGREEMENT = ApiClient.HTML_URL + "html/rule/region/agreement.htm";
    //客服二维码
    public static final String URL_SERVICEQR = ApiClient.HTML_URL + "html/qrcode/serviceqr/index.htm";
    //疯狂广告公告
    public static final String URL_CRAZY_NOTICE = ApiClient.HTML_URL + "html/notice/crazy/index.htm";
    //代理商协议
    public static final String URL_AGREEMENT_AGENT = ApiClient.HTML_URL + "html/agreement/agent/index.htm";
    //公告消息
    public static final String URL_NOTICE_DETAIL = ApiClient.HTML_URL+"project/html/notice.html";
    //帮助中心
    public static final String URL_HELP_CENTER = ApiClient.HTML_URL+"project/html/helpCenter.html";
    //商圈
    public static final String URL_TRADINGAREA = ApiClient.HTML_URL+"project/html/shangquan/index.html";
    //蜜玩
    public static final String URL_MIWAN = ApiClient.HTML_URL+"project/html/miWan.html";
    //我的订单
    public static final String URL_MY_ORDER = ApiClient.HTML_URL+"project/html/myOrder.html";
    //蜂窝规则
    public static final String URL_HONYCOMB_RULE = ApiClient.HTML_URL+"project/html/rule/fengwoRule.html";
    //广告投放协议
    public static final String URL_FENGCHAO_BANNER = ApiClient.HTML_URL+"project/html/rule/fengchao/banner.html";
    //竞购协议
    public static final String URL_FENGCHAO_JINGGOU = ApiClient.HTML_URL+"project/html/rule/fengchao/jinggou.html";
    //竞拍说明
    public static final String URL_FENGCHAO_JINGPAI = ApiClient.HTML_URL+"project/html/rule/fengchao/jingpai.html";

    public static final String REGEX_WEBURL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
    public static final String COMPRESS_DIR_PATH = App.getInstance().getExternalCacheDir() + File.separator + "kshb";
    public static final String COMMON_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "kshb";
    /**
     * 登录相关参数
     */
    public static final String CAPTCHA = "";
    public static final String TENANTCODE = "gcyh";
    public static final String SYSTEM_CODE = "kuaishou";
    public static final String SYSTEM_CODE_ANDROID = "Android";
    public static final String TYPE_INFOMATION = "information";
    public static final String TYPE_KSB_TYPE = "KSB_type";
    public static final String TYPE_REGION_STATISTICS = "RegionStatisticsType";

    public static final String NATION_CODE = "86";
    public static final int LOGIN_TYPE = 1;
    public static final String REGISTER_TYPE = "1";
    public static final String REGISTER_CAPTCHA_IMA = "register";
    public static final String FORGET_RESET_CAPTCHA_IMA = "forgetReset";
    public static final String SAFE_RESET_CAPTCHA_IMA = "safeReset";
    public static final String BIND_PHONE_CAPTCHA_IMA = "bindPhone";
    public static final String RESET_NATION_CODE = "86";

    public static final String RED_PACKET_TYPE = "redPacketType";
    public static final String TYPE_NOW = "now";
    public static final String TYPE_SHARE = "share";
    public static final String TYPE_REDBAG = "redbag";
    public static final String RED_PACKET_DATA = "redbag";
    public static final String RED_PACKET_DETAIL = "redbagDetail";
    public static final String RED_PACKET_ID = "redPacketId";
    public static final String RED_PACKET_UUID = "redPacketUuid";
    public static final String FROM_COLLECT = "fromCollect";
    public static final String POSITION = "position";
    public static final String START_INDEX = "startIndex";
    public static final String ARGS_LIST = "args_list";
    public static final String ARGS_ARRAY = "args_array";
    public static final String ARGS_POSITION = "args_position";
    public static final String ARGS_DATA = "data";


    public static final int STATE_HAS_GET = 1;
    public static final int STATE_UN_GET = 2;
    public static final int STATE_HAS_EXPIRE = 3;

    public static final int TYPE_PURCHASE_REGION = 1;
    public static final int TYPE_PURCHASE_AGENT = 2;
    public static final int TYPE_PURCHASE_HONEYCOMB = 3;

    public static final String RAIN_DURATION = "duration";
    public static final String RAIN_COUNT = "count";
    public static final String RAIN_UUID = "redPacketRainUuid";

    public static final String RED_PACKET_TYPE_RANDOM = "random";
    public static final String RED_PACKET_TYPE_PASSWORD = "password";
    public static final String RED_PACKET_TYPE_LUCKY = "lucky";

    public static final String ROLETYPE_ADMIN = "admin";
    public static final String ROLETYPE_PERSON = "person";
    public static final String ROLETYPE_MERCHANT = "merchant";
    public static final String ROLETYPE_SYSTEM = "system";

    public static final int PAY_TYPE_WXPAY = 11;
    public static final int PAY_TYPE_ALIPAY = 101;
    public static final int PAY_TYPE_KSB = 10001;

    public static final String BUSSINESSTYPE_RED_PACKET = "redpacket";
    public static final String BUSSINESSTYPE_RED_AVATAR = "avatar";
    public static final String BUSSINESSTYPE_ID_CARD = "idCard";
    public static final String USER_ACCOUNT_UUID = "userAccountUuid";
    public static final int REFRESH = 9020;

    public static final String MSG_TYPE_NOTICE = "notice";
    public static final String MSG_TYPE_COMMENT = "comment";
    public static final String MSG_TYPE_REDPACKET = "redPacket";

    public static final String ACTION_CHANGE_COIN = "change_coin";
    public static final String ACTION_CHANGE_AGENT = "change_agent";
    public static final String ACTION_CHANGE_CITY = "change_city";
    public static final String BID_CITY_CITY_ID = "bidCityCityId";
    public static final String DEFAULT_REDPACKET_TYPE = "0";

    public static final int CAL_TYPE_COIN = 2;
    public static final int CAL_TYPE_MONEY = 1;

    public static final int USER_ROLE_FANS = 0;
    public static final int USER_ROLE_AGENT = 1;
    public static final int USER_ROLE_MANAGER = 2;
    public static final int USER_ROLE_CHIEF = 3;
    public static final int USER_ROLE_STAR_CHIEF = 4;
    public static final int MAX_PICK_PICTURE = 6;
    //审核中
    public static final int STATUS_AUDIT_INREVIEW=0;
    //审核通过
    public static final int STATUS_AUDIT_PASS=1;
    //审核失败
    public static final int STATUS_AUDIT_FAIL=2;

    public static final String MAP_KEY_TOTALCOIN = "totalCoin";
}