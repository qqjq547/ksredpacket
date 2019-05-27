package com.guochuang.mimedia.tools;

import com.guochuang.mimedia.http.retrofit.ApiClient;

public class UrlConfig {
    //用户协议
    public static final String URL_REGIST_AGREEMENT = "project/html/rule/agreementRule.html";
    //资讯详情
    public static final String URL_INFOMATION_DETAIL = "information/detail/index.htm";
    //推荐协议
    public static final String URL_RULE_RECOMMEND = "project/html/rule/awardRule.html";
    //ksb协议
    public static final String URL_RULE_QC = "project/html/rule/recommendedRule.html";
    //平台协议
    public static final String URL_SEND_REDBAG = "html/platformRules.html";
    //疯狂规则
    public static final String URL_CRAZY_RULE = "html/rule/crazy/index.htm";
    //代理规则
    public static final String URL_AGENT_RULE = "project/html/rule/agentRule.html";
    //城主规则
    public static final String URL_REGION_RULE = "project/html/rule/cityLordRule.html";
    //竞购协议
    public static final String URL_AGREEMENT = "html/rule/region/agreement.htm";
    //客服二维码
    public static final String URL_SERVICEQR = "html/qrcode/serviceqr/index.htm";
    //疯狂广告公告
    public static final String URL_CRAZY_NOTICE = "html/notice/crazy/index.htm";
    //代理商协议
    public static final String URL_AGREEMENT_AGENT = "html/agreement/agent/index.htm";
    //公告消息
    public static final String URL_NOTICE_DETAIL = "project/html/notice.html";
    //帮助中心
    public static final String URL_HELP_CENTER = "project/html/fengwo/index.html#/helpCenter";
    //商圈
    public static final String URL_TRADINGAREA = "project/html/shangquan/index.html";
    //蜜玩
    public static final String URL_MIWAN = "project/html/fengwo/index.html#miWan";
    //我的订单
    public static final String URL_MY_ORDER = "project/html/fengwo/index.html#/myOrder";
    //蜂窝规则
    public static final String URL_HONYCOMB_RULE = "project/html/rule/fengwoRule.html";
    //广告投放协议
    public static final String URL_FENGCHAO_BANNER = "project/html/rule/fengchao/banner.html";
    //竞购协议
    public static final String URL_FENGCHAO_JINGGOU = "project/html/rule/fengchao/jinggou.html";
    //竞拍说明
    public static final String URL_FENGCHAO_JINGPAI = "project/html/rule/fengchao/jingpai.html";
    //显示地图网页
    public static final String URL_BMAP_URL = "project/html/fengwo/index.html#/BMapComponent";
    //夺宝规则
    public static final String URL_DUOBAO_RULE = "project/html/rule/duobaoRule.html";
    //夺宝详情
    public static final String URL_DUOBAO_DETAIL = "project/html/fengwo/index.html#/treasureDetails?snatchId=";
    //我的夺宝
    public static final String URL_DUOBAO_TREASURE_NUMBER = "project/html/fengwo/index.html#/treasureNumber?type=false&snatchRecordId=";
    //淘区块
    public static final String URL_DUOBAO_INDEX = "project/html/fengwo/index.html#/treasureIndex";
    //淘区块活动说明
    public static final String URL_ACTIVE_RULE = "project/html/rule/activeRule.html";

    public static String getHtmlUrl(String path){
        return ApiClient.HTML_URL+path;
    }
}
