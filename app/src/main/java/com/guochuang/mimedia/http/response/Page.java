package com.guochuang.mimedia.http.response;

import java.util.List;

public class Page<T> {

    /**
     * currentPage : 0
     * dataList : [{"articleId":0,"author":"string","commentNumber":0,"picture":"string","praiseNumber":0,"title":"string"}]
     * map : {}
     * pageSize : 0
     * totalCount : 0
     * totalPage : 0
     */
    private int currentPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private Object map;
    private List<T> dataList;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public Object getMap() {
        return map;
    }

    public void setMap(Object map) {
        this.map = map;
    }
}
