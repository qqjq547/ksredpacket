package com.guochuang.mimedia.mvp.model;

public class RegionCore {

    /**
     * isCenter : false
     * totalIncome : string
     * whereRegion : string
     * yesterDayIncome : string
     */

    private boolean isCenter;
    private String totalIncome;
    private String whereRegion;
    private String yesterDayIncome;

    public boolean isIsCenter() {
        return isCenter;
    }

    public void setIsCenter(boolean isCenter) {
        this.isCenter = isCenter;
    }

    public String getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(String totalIncome) {
        this.totalIncome = totalIncome;
    }

    public String getWhereRegion() {
        return whereRegion;
    }

    public void setWhereRegion(String whereRegion) {
        this.whereRegion = whereRegion;
    }

    public String getYesterDayIncome() {
        return yesterDayIncome;
    }

    public void setYesterDayIncome(String yesterDayIncome) {
        this.yesterDayIncome = yesterDayIncome;
    }
}
