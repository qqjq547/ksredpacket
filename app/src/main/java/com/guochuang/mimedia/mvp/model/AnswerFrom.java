package com.guochuang.mimedia.mvp.model;

import java.util.List;

public class AnswerFrom {


    /**
     * answerAddList : [{"optionName":"string","optionValue":"string","questionId":0,"sourceId":"string","surveyId":0}]
     * clientIp : string
     * latitude : 0
     * longitude : 0
     * redPacketUuid : string
     * tenantCode : string
     * userAccountUuid : string
     */

    private double latitude;
    private double longitude;
    private String redPacketUuid;
    private String tenantCode;
    private String userAccountUuid;
    private List<AnswerAddListBean> answerAddList;


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getRedPacketUuid() {
        return redPacketUuid;
    }

    public void setRedPacketUuid(String redPacketUuid) {
        this.redPacketUuid = redPacketUuid;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getUserAccountUuid() {
        return userAccountUuid;
    }

    public void setUserAccountUuid(String userAccountUuid) {
        this.userAccountUuid = userAccountUuid;
    }

    public List<AnswerAddListBean> getAnswerAddList() {
        return answerAddList;
    }

    public void setAnswerAddList(List<AnswerAddListBean> answerAddList) {
        this.answerAddList = answerAddList;
    }

    public static class AnswerAddListBean {
        /**
         * optionName : string
         * optionValue : string
         * questionId : 0
         * sourceId : string
         * surveyId : 0
         */
        private String optionId;
        private String optionName;
        private String optionValue;
        private long questionId;
        private String sourceId;
        private long surveyId;

        public String getOptionId() {
            return optionId;
        }

        public void setOptionId(String optionId) {
            this.optionId = optionId;
        }

        public String getOptionName() {
            return optionName;
        }

        public void setOptionName(String optionName) {
            this.optionName = optionName;
        }

        public String getOptionValue() {
            return optionValue;
        }

        public void setOptionValue(String optionValue) {
            this.optionValue = optionValue;
        }

        public long getQuestionId() {
            return questionId;
        }

        public void setQuestionId(long questionId) {
            this.questionId = questionId;
        }

        public String getSourceId() {
            return sourceId;
        }

        public void setSourceId(String sourceId) {
            this.sourceId = sourceId;
        }

        public long getSurveyId() {
            return surveyId;
        }

        public void setSurveyId(long surveyId) {
            this.surveyId = surveyId;
        }
    }
}
