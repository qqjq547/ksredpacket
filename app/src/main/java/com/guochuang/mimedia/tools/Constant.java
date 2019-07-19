package com.guochuang.mimedia.tools;

import android.os.Environment;

import com.sz.gcyh.KSHongBao.BuildConfig;
import com.guochuang.mimedia.app.App;

import java.io.File;

/**
 * Created by Administrator on 2017/10/26.
 */

public class Constant {
    /**
     * 是否调试模式
     */
    public static final boolean isDebug = (BuildConfig.ENV == 0);
    public static final int DEFAULT_HOST = 0;

    public static final int HTTP_STATUS_FORCE_LOGIN = 1007;
    public static final int HTTP_STATUS_REDBAD_INVALID = 10012;
    public static final String PARAMS_H_TIME = "h-time";
    public static final String PARAMS_H_TENANT_CODE = "h-tenant-code";
    public static final String PARAMS_H_API_TOEKN = "h-api-token";
    public static final String PARAMS_H_NONCE = "h-nonce";
    public static final String PARAMS_H_SYSTEM_CODE = "h-system-code";
    public static final String PARAMS_H_VERSION = "h-version";
    public static final String PARAMS_H_SIGN = "h-sign";

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
    public static final int REQUEST_ADD_ADDRESS = 15;
    public static final int REQUEST_PICK_ADDRESS = 16;
    public static final int REQUEST_SET_SHOWLIST = 17;
    public static final int REQUEST_EDIT_NESTAD = 18;
    public static final int REQUEST_SCAN_CODE = 19;
    public static final int REQUEST_ANSWER = 20;
    public static final int REQUEST_PICK_VIDEO = 21;
    public static final int REQUEST_EDIT_QUESTION = 22;
    public static final int REQUEST_EDIT_COIN_ADDRESS = 23;
    public static final int REQUEST_CAPTURE = 24;
    public static final int REQUEST_PICK_COIN_ADDRESS = 25;

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
    public static final String AVATAR = "avatar";
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
    public static final String SNATCHID = "snatchId";
    public static final String SNATCH = "snatch";
    public static final String ADDRESS = "address";
    public static final String NESTINFOID = "nestInfoId";
    public static final String NESTLOCATIONID = "nestLocationId";
    public static final String NESTSUCCESSID = "nestSuccessId";
    public static final String NESTUPDATE = "nestUpdate";
    public static final String PRICE = "price";
    public static final String INVITECODE = "inviteCode";
    public static final String STARTDATE = "startDate";
    public static final String DAYS = "days";
    public static final String NESTLATITUDE = "nestLatitude";
    public static final String NESTLONGITUDE = "nestLongitude";
    public static final String HAS_REFRESH = "hasRefresh";
    public static final String DEFAULT_CODE = "defaultCode";
    public static final String ISSHOWDEMO = "isShowDemo";
    public static final String MARKET_NAME = "marketName";
    public static final String VERSION_CODE = "versionCode";

    public static final String SWITCH_SHOW = "show";
    public static final String SWITCH_HIDE = "hide";

    public static final String PAYEE_USER = "payeeUser";

    public static final String FORMAT_DATE_SIMPLE = "yyyy-MM-dd";
    public static final String FORMAT_DATE = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_MONTH = "yyyy-MM";

    public static final String TYPE_REPORT_INFORMATION = "informationReport";
    public static final String TYPE_REPORT_REDPACKET = "redPacketReport";
    public static final String TYPE_NESTINFO_REPORT = "nestInfoReport";
    public static final String NESTAD = "nestAd";
    public static final String ISPREVIEW="isPreview";
    public static final String COINTYPE="coinType";

    public static final String REGEX_WEBURL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
    public static final String COMPRESS_DIR_PATH = App.getInstance().getExternalCacheDir() + File.separator + "kshb";
    public static final String COMMON_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "kshb";
    /**
     * 登录相关参数
     */
    public static final String CAPTCHA = "";
    public static final String TENANTCODE = "gcyh";
    public static final String H_SYSTEM_CODE = "android";
    public static final String SYSTEM_CODE = "kuaishou";
    public static final String SYSTEM_CODE_ANDROID = "Android";
    public static final String TYPE_INFOMATION = "information";
    public static final String TYPE_KSB_TYPE = "KSB_type";
    public static final String TYPE_SEAL_TYPE = "seal_type";
    public static final String TYPE_QC_TYPE = "qc_type";
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
    public static final String ROLE_TYPE = "roleType";
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
    public static final String JXW_FROM = "h5android";
    public static final String DATA = "data";
    public static final String SURVEYID = "surveyId";
    public static final String JPUSH_CHANNEL = "JPUSH_CHANNEL";
    public static final String CHANNEL_DEFAULT = "default";
    public static final String LOCKDAY = "lockDay";


    public static final int STATE_HAS_GET = 1;
    public static final int STATE_UN_GET = 2;
    public static final int STATE_HAS_EXPIRE = 3;

    public static final int TYPE_PURCHASE_REDBAG = 0;
    public static final int TYPE_PURCHASE_REGION = 1;
    public static final int TYPE_PURCHASE_AGENT = 2;
    public static final int TYPE_PURCHASE_HONEYCOMB = 3;
    public static final int TYPE_PURCHASE_SNATCH = 4;
    public static final int TYPE_PURCHASE_NESTAD = 5;

    public static final String RAIN_DURATION = "duration";
    public static final String RAIN_COUNT = "count";
    public static final String RAIN_UUID = "redPacketRainUuid";

    public static final String RED_PACKET_TYPE_RANDOM = "random";
    public static final String RED_PACKET_TYPE_PASSWORD = "password";
    public static final String RED_PACKET_TYPE_LUCKY = "lucky";
    public static final String RED_PACKET_TYPE_VIDEO = "video";
    public static final String RED_PACKET_TYPE_SURVEY = "survey";

    public static final String ROLETYPE_ADMIN = "admin";
    public static final String ROLETYPE_PERSON = "person";
    public static final String ROLETYPE_MERCHANT = "merchant";
    public static final String ROLETYPE_SYSTEM = "system";
    public static final String ROLETYPE_LINK = "link";

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
    public static final String MSG_TYPE_SNATCHACTIVITY = "snatchActivity";

    public static final String ACTION_CHANGE_COIN = "change_coin";
    public static final String ACTION_CHANGE_AGENT = "change_agent";
    public static final String ACTION_CHANGE_CITY = "change_city";
    public static final String BID_CITY_CITY_ID = "bidCityCityId";
    public static final String DEFAULT_REDPACKET_TYPE = "0";
    public static final String KSB_CODE_PAYMENT = "129";

    public static final int CAL_TYPE_COIN = 2;
    public static final int CAL_TYPE_MONEY = 1;

    public static final int USER_ROLE_FANS = 0;
    public static final int USER_ROLE_AGENT = 1;
    public static final int USER_ROLE_MANAGER = 2;
    public static final int USER_ROLE_CHIEF = 3;
    public static final int USER_ROLE_STAR_CHIEF = 4;
    public static final int MAX_PICK_PICTURE = 6;
    public static final int MAX_PICK_VIDEO = 1;
    //审核中
    public static final int STATUS_AUDIT_INREVIEW = 0;
    //审核通过
    public static final int STATUS_AUDIT_PASS = 1;
    //审核失败
    public static final int STATUS_AUDIT_FAIL = 2;

    public static final String MAP_KEY_TOTALCOIN = "totalCoin";

    public static final int TRANSFER_WXPAY = 2;
    public static final int TRANSFER_ALIPAY = 3;
    public static final int TRANSFER_BANKCARD = 4;

    public static final String SORT_DEFAULT = "DEFAULT";
    public static final String SORT_ASC = "ASC";
    public static final String SORT_DESC = "DESC";

    public static final String AD_TYPE_DETAIL = "detail";
    public static final String AD_TYPE_EDIT = "edit";
    public static final String MAP_MARKER_SPOT = "spot";


    public static final String INVITE_FRIENDS_GIVE_GIFTS = "邀请好友得好礼";
    public static final int ISDRAG = 1;
    public static final String ACTIVTYPUTBUNDLEKEY = "openActivity_key";
    public static final String PROBLEMLIST_KEY = "problemlist_key";
    public static final String VIDEO_PATH = "video_path";
    public static final String FILL_ANSWER_INFO = "fill_answer_info";
    public static final String PROBLEM_LIST = "problem_list";
    public static final String WHO_OPEN_MYWECHATACTIVITY = "WHO_OPEN_MYWECHATACTIVITY";
    public static final int GOTOAPLAYYWEIXIN = 2719;
    public static final String UESRPHONE_KEY = "UESRPHONE_KEY";
    public static final String UESRPASSWORLD_KEY = "UESRPASSWORLD_KEY";
    public static final int FENGWO_ADVERTISEMENT_LINKE = 2;
    public static final int FENGWO_ADVERTISEMENT_VIDEO = 1;
    public static final String COUNTDOWNTIME = "COUNTDOWNTIME";
    public static final int REQUEST_STARTPREVIDEO_CODE = 0x000146;
    public static final int REQUEST_STARTPRE_FENGWOLINKWEB = 0x000147;
    public static final String COUNTDOWN_GONE = "ISGONG";
    public static final String ACTION_CHANGE_QC = "ACTION_CHANGE_QC";

    public static final String COINTYPE_KSB ="1" ;
    public static final String COINTYPE_SEAL ="2" ;
    public static final String COINTYPE_QC ="3" ;
    public static final String EMAIL_KEY = "email";
    public static final int OPENT_BINDINGEMAILACTIVITY = 0x0125;
    public static final String PHONE_KEY = "phone";
    public static final int OPENT_BINDINGPHONEACITIVITY = 0x0124;
    public static final String ISADD = "isAdd" ;
    public static final String COIN_ADDRESS = "coin_address";
    public static final int CHAIN_TYPE_ETH = 1;


    //   视频红包与问卷红包 问题类型 0：单选 1：多选 2：填空题
    public static int SINGLESELECT = 0;
    public static int MORESELECT = 1;
    public static int FILL_IN_PROBLEM = 2;

    public static final String DIGITAL_CURRENCY_SEAL = "SEAL";
    public static final String DIGITAL_CURRENCY_QC = "QC";

    public static int INT_CAL_QC_TO_SEAL = 1;
    public static int INT_CAL_SEAL_TO_QC = 2;

    public static int QC_TO_OTHER = 1;
    public static int OTHER_TO_QC = 2;



}