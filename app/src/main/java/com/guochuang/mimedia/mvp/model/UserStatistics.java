package com.guochuang.mimedia.mvp.model;

import java.util.List;

public class UserStatistics {


    /**
     * todayUserNum : 8
     * yesterdayUserNum : 1
     * sumUserNum : 9
     * todayActiveNum : 64
     * yesterdayActiveNum : 18
     * sumActiveNum : 82
     * list : [{"activeUser":22,"incrementUser":0,"statisticsDate":"2018-12-25"},{"activeUser":64,"incrementUser":8,"statisticsDate":"2018-12-26"}]
     */

    private int todayUserNum;
    private int yesterdayUserNum;
    private int sumUserNum;
    private int todayActiveNum;
    private int yesterdayActiveNum;
    private int sumActiveNum;
    private int statPeriodUserNum;
    private int statPeriodActiveNum;
    private List<ListBean> list;

    public int getTodayUserNum() {
        return todayUserNum;
    }

    public void setTodayUserNum(int todayUserNum) {
        this.todayUserNum = todayUserNum;
    }

    public int getYesterdayUserNum() {
        return yesterdayUserNum;
    }

    public void setYesterdayUserNum(int yesterdayUserNum) {
        this.yesterdayUserNum = yesterdayUserNum;
    }

    public int getSumUserNum() {
        return sumUserNum;
    }

    public void setSumUserNum(int sumUserNum) {
        this.sumUserNum = sumUserNum;
    }

    public int getTodayActiveNum() {
        return todayActiveNum;
    }

    public void setTodayActiveNum(int todayActiveNum) {
        this.todayActiveNum = todayActiveNum;
    }

    public int getYesterdayActiveNum() {
        return yesterdayActiveNum;
    }

    public void setYesterdayActiveNum(int yesterdayActiveNum) {
        this.yesterdayActiveNum = yesterdayActiveNum;
    }

    public int getSumActiveNum() {
        return sumActiveNum;
    }

    public void setSumActiveNum(int sumActiveNum) {
        this.sumActiveNum = sumActiveNum;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public int getStatPeriodUserNum() {
        return statPeriodUserNum;
    }

    public void setStatPeriodUserNum(int statPeriodUserNum) {
        this.statPeriodUserNum = statPeriodUserNum;
    }

    public int getStatPeriodActiveNum() {
        return statPeriodActiveNum;
    }

    public void setStatPeriodActiveNum(int statPeriodActiveNum) {
        this.statPeriodActiveNum = statPeriodActiveNum;
    }

    public static class ListBean {
        /**
         * activeUser : 22
         * incrementUser : 0
         * statisticsDate : 2018-12-25
         */

        private int activeUser;
        private int incrementUser;
        private String statisticsDate;

        public int getActiveUser() {
            return activeUser;
        }

        public void setActiveUser(int activeUser) {
            this.activeUser = activeUser;
        }

        public int getIncrementUser() {
            return incrementUser;
        }

        public void setIncrementUser(int incrementUser) {
            this.incrementUser = incrementUser;
        }

        public String getStatisticsDate() {
            return statisticsDate;
        }

        public void setStatisticsDate(String statisticsDate) {
            this.statisticsDate = statisticsDate;
        }
    }
}
