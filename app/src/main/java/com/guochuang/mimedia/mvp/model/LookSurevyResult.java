package com.guochuang.mimedia.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class LookSurevyResult {

    /**
     * surveyId : 6
     * statisticsList : [{"questionId":10,"title":"张氏说法is答复","type":0,"sequence":0,"optionValue":null,"optionsList":[{"count":0,"optionValue":"在技术大姐夫","optionName":"A"},{"count":0,"optionValue":"发你","optionName":"B"},{"count":0,"optionValue":"答复cv 行政村","optionName":"C"},{"count":0,"optionValue":"什袭珍藏选择","optionName":"D"}]},{"questionId":11,"title":"问卷问题","type":0,"sequence":1,"optionValue":null,"optionsList":[{"count":0,"optionValue":"问卷答案1","optionName":"A"},{"count":0,"optionValue":"问卷答案","optionName":"B"},{"count":0,"optionValue":"晚讷讷呢","optionName":"C"},{"count":0,"optionValue":"问卷答案","optionName":"D"}]},{"questionId":12,"title":"问卷问题","type":2,"sequence":2,"optionValue":null,"optionsList":[{"count":0,"optionValue":null,"optionName":"A"}]}]
     * drawNumber : 0
     */

    private int surveyId;
    private int drawNumber;
    private List<StatisticsListBean> statisticsList;

    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public int getDrawNumber() {
        return drawNumber;
    }

    public void setDrawNumber(int drawNumber) {
        this.drawNumber = drawNumber;
    }

    public List<StatisticsListBean> getStatisticsList() {
        return statisticsList;
    }

    public void setStatisticsList(List<StatisticsListBean> statisticsList) {
        this.statisticsList = statisticsList;
    }

    public static class StatisticsListBean implements Parcelable {
        /**
         * questionId : 10
         * title : 张氏说法is答复
         * type : 0
         * sequence : 0
         * optionValue : null
         * optionsList : [{"count":0,"optionValue":"在技术大姐夫","optionName":"A"},{"count":0,"optionValue":"发你","optionName":"B"},{"count":0,"optionValue":"答复cv 行政村","optionName":"C"},{"count":0,"optionValue":"什袭珍藏选择","optionName":"D"}]
         */

        private int questionId;
        private String title;
        private int type;
        private int sequence;
        private String optionValue;
        private List<OptionsListBean> optionsList;


        protected StatisticsListBean(Parcel in) {
            questionId = in.readInt();
            title = in.readString();
            type = in.readInt();
            sequence = in.readInt();
            optionValue = in.readString();
            optionsList = in.createTypedArrayList(OptionsListBean.CREATOR);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(questionId);
            dest.writeString(title);
            dest.writeInt(type);
            dest.writeInt(sequence);
            dest.writeString(optionValue);
            dest.writeTypedList(optionsList);
        }

        public static final Creator<StatisticsListBean> CREATOR = new Creator<StatisticsListBean>() {
            @Override
            public StatisticsListBean createFromParcel(Parcel in) {
                return new StatisticsListBean(in);
            }

            @Override
            public StatisticsListBean[] newArray(int size) {
                return new StatisticsListBean[size];
            }
        };

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

        public String getOptionValue() {
            return optionValue;
        }

        public void setOptionValue(String optionValue) {
            this.optionValue = optionValue;
        }

        public List<OptionsListBean> getOptionsList() {
            return optionsList;
        }

        public void setOptionsList(List<OptionsListBean> optionsList) {
            this.optionsList = optionsList;
        }




        public static class OptionsListBean implements Parcelable{
            /**
             * count : 0
             * optionValue : 在技术大姐夫
             * optionName : A
             */

            private int count;
            private String optionValue;
            private String optionName;

            protected OptionsListBean(Parcel in) {
                count = in.readInt();
                optionValue = in.readString();
                optionName = in.readString();
            }

            public static final Creator<OptionsListBean> CREATOR = new Creator<OptionsListBean>() {
                @Override
                public OptionsListBean createFromParcel(Parcel in) {
                    return new OptionsListBean(in);
                }

                @Override
                public OptionsListBean[] newArray(int size) {
                    return new OptionsListBean[size];
                }
            };

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getOptionValue() {
                return optionValue;
            }

            public void setOptionValue(String optionValue) {
                this.optionValue = optionValue;
            }

            public String getOptionName() {
                return optionName;
            }

            public void setOptionName(String optionName) {
                this.optionName = optionName;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(count);
                dest.writeString(optionValue);
                dest.writeString(optionName);
            }
        }
    }
}