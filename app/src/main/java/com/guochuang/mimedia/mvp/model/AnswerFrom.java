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

    private String latitude;
    private String longitude;
    private String redPacketUuid;
    private List<AnswerAddListBean> answerAddList;


    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getRedPacketUuid() {
        return redPacketUuid;
    }

    public void setRedPacketUuid(String redPacketUuid) {
        this.redPacketUuid = redPacketUuid;
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
        private String questionId;
        private String sourceId;
        private String surveyId;

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

        public String getQuestionId() {
            return questionId;
        }

        public void setQuestionId(String questionId) {
            this.questionId = questionId;
        }

        public String getSourceId() {
            return sourceId;
        }

        public void setSourceId(String sourceId) {
            this.sourceId = sourceId;
        }

        public String getSurveyId() {
            return surveyId;
        }

        public void setSurveyId(String surveyId) {
            this.surveyId = surveyId;
        }
    }
}
