package com.guochuang.mimedia.mvp.model;

import java.util.List;

public class LookVideoResult {


    /**
     * surveyId : 2
     * questionList : [{"questionId":2,"title":"XSADCZXCZ","type":1,"sequence":0,"optionsList":[{"tenantCode":"gcyh","id":4,"createUser":null,"createDate":"2019-03-13 14:25:58","updateUser":null,"updateDate":null,"questionId":2,"optionName":"A","optionValue":"SAFF","isAnswer":0,"sequence":0},{"tenantCode":"gcyh","id":5,"createUser":null,"createDate":"2019-03-13 14:25:58","updateUser":null,"updateDate":null,"questionId":2,"optionName":"B","optionValue":"ASDAF","isAnswer":0,"sequence":1},{"tenantCode":"gcyh","id":6,"createUser":null,"createDate":"2019-03-13 14:25:58","updateUser":null,"updateDate":null,"questionId":2,"optionName":"C","optionValue":"DASDSA","isAnswer":0,"sequence":2},{"tenantCode":"gcyh","id":7,"createUser":null,"createDate":"2019-03-13 14:25:58","updateUser":null,"updateDate":null,"questionId":2,"optionName":"D","optionValue":"DSADCAS","isAnswer":0,"sequence":3}]}]
     * questionsCount : 1
     * drawNumber : null
     */

    private int surveyId;
    private int questionsCount;
    private String drawNumber;
    private List<QuestionListBean> questionList;

    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public int getQuestionsCount() {
        return questionsCount;
    }

    public void setQuestionsCount(int questionsCount) {
        this.questionsCount = questionsCount;
    }

    public String getDrawNumber() {
        return drawNumber;
    }

    public void setDrawNumber(String drawNumber) {
        this.drawNumber = drawNumber;
    }

    public List<QuestionListBean> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<QuestionListBean> questionList) {
        this.questionList = questionList;
    }

    public static class QuestionListBean {
        /**
         * questionId : 2
         * title : XSADCZXCZ
         * type : 1
         * sequence : 0
         * optionsList : [{"tenantCode":"gcyh","id":4,"createUser":null,"createDate":"2019-03-13 14:25:58","updateUser":null,"updateDate":null,"questionId":2,"optionName":"A","optionValue":"SAFF","isAnswer":0,"sequence":0},{"tenantCode":"gcyh","id":5,"createUser":null,"createDate":"2019-03-13 14:25:58","updateUser":null,"updateDate":null,"questionId":2,"optionName":"B","optionValue":"ASDAF","isAnswer":0,"sequence":1},{"tenantCode":"gcyh","id":6,"createUser":null,"createDate":"2019-03-13 14:25:58","updateUser":null,"updateDate":null,"questionId":2,"optionName":"C","optionValue":"DASDSA","isAnswer":0,"sequence":2},{"tenantCode":"gcyh","id":7,"createUser":null,"createDate":"2019-03-13 14:25:58","updateUser":null,"updateDate":null,"questionId":2,"optionName":"D","optionValue":"DSADCAS","isAnswer":0,"sequence":3}]
         */

        private int questionId;
        private String title;
        private int type;
        private int sequence;
        private List<OptionsListBean> optionsList;

        public int getQuestionId() {
            return questionId;
        }

        public void setQuestionId(int questionId) {
            this.questionId = questionId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getSequence() {
            return sequence;
        }

        public void setSequence(int sequence) {
            this.sequence = sequence;
        }

        public List<OptionsListBean> getOptionsList() {
            return optionsList;
        }

        public void setOptionsList(List<OptionsListBean> optionsList) {
            this.optionsList = optionsList;
        }

        public static class OptionsListBean {
            /**
             * tenantCode : gcyh
             * id : 4
             * createUser : null
             * createDate : 2019-03-13 14:25:58
             * updateUser : null
             * updateDate : null
             * questionId : 2
             * optionName : A
             * optionValue : SAFF
             * isAnswer : 0
             * sequence : 0
             */

            private String tenantCode;
            private int id;
            private Object createUser;
            private String createDate;
            private Object updateUser;
            private Object updateDate;
            private int questionId;
            private String optionName;
            private String optionValue;
            private int isAnswer;
            private int sequence;

            public String getTenantCode() {
                return tenantCode;
            }

            public void setTenantCode(String tenantCode) {
                this.tenantCode = tenantCode;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getCreateUser() {
                return createUser;
            }

            public void setCreateUser(Object createUser) {
                this.createUser = createUser;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public Object getUpdateUser() {
                return updateUser;
            }

            public void setUpdateUser(Object updateUser) {
                this.updateUser = updateUser;
            }

            public Object getUpdateDate() {
                return updateDate;
            }

            public void setUpdateDate(Object updateDate) {
                this.updateDate = updateDate;
            }

            public int getQuestionId() {
                return questionId;
            }

            public void setQuestionId(int questionId) {
                this.questionId = questionId;
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

            public int getIsAnswer() {
                return isAnswer;
            }

            public void setIsAnswer(int isAnswer) {
                this.isAnswer = isAnswer;
            }

            public int getSequence() {
                return sequence;
            }

            public void setSequence(int sequence) {
                this.sequence = sequence;
            }
        }
    }
}
