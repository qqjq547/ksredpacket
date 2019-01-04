package com.guochuang.mimedia.mvp.model;

import java.util.List;

public class IncomeStatistics {

    /**
     * statisticList : [{"coin":"string","time":"string"}]
     * statistics : string
     */

    private String statistics;
    private List<StatisticListBean> statisticList;

    public String getStatistics() {
        return statistics;
    }

    public void setStatistics(String statistics) {
        this.statistics = statistics;
    }

    public List<StatisticListBean> getStatisticList() {
        return statisticList;
    }

    public void setStatisticList(List<StatisticListBean> statisticList) {
        this.statisticList = statisticList;
    }

    public static class StatisticListBean {
        /**
         * coin : string
         * time : string
         */

        private String coin;
        private String time;

        public String getCoin() {
            return coin;
        }

        public void setCoin(String coin) {
            this.coin = coin;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
