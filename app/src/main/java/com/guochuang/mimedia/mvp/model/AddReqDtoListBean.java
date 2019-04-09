package com.guochuang.mimedia.mvp.model;

import java.io.Serializable;
import java.util.List;

public  class AddReqDtoListBean implements Serializable {
        /**
         * optionsList : [{"isAnswer":0,"optionName":"string","optionValue":"string","sequence":0}]
         * sequence : 0
         * surveyId : 0
         * title : string
         * type : 0
         */

        private int sequence;
        private int surveyId;
        private String title;
        private int type;
        private List<OptionsListBean> optionsList;

        public int getSequence() {
            return sequence;
        }

        public void setSequence(int sequence) {
            this.sequence = sequence;
        }

        public int getSurveyId() {
            return surveyId;
        }

        public void setSurveyId(int surveyId) {
            this.surveyId = surveyId;
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

        public List<OptionsListBean> getOptionsList() {
            return optionsList;
        }

        public void setOptionsList(List<OptionsListBean> optionsList) {
            this.optionsList = optionsList;
        }

        public static class OptionsListBean implements Serializable{
            /**
             * isAnswer : 0
             * optionName : string
             * optionValue : string
             * sequence : 0
             */

            private int isAnswer;
            private String optionName;
            private String optionValue;
            private int sequence;

            public int getIsAnswer() {
                return isAnswer;
            }

            public void setIsAnswer(int isAnswer) {
                this.isAnswer = isAnswer;
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

            public int getSequence() {
                return sequence;
            }

            public void setSequence(int sequence) {
                this.sequence = sequence;
            }
        }
    }