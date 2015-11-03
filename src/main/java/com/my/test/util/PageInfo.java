package com.my.test.util;

/**
 *
 * @author zhengmingcheng
 */
public class PageInfo {
    //每页的大小

    private int pageSize = 10;
    //总记录数
    private int totalReco;
    //总页数
    private int totalPage;
    //当前页
    private int currentPage = 1;
    //当前记录
    private int currentRec;
    //下一页的记录
    private int nextRec;
    //下一页
    private int nextPage;
    //上一页
    private int prePage;
    //起始多少条
    private int stRec;
    //关键字
    private String cond;

    
    public PageInfo() {
    	super();
    }
    
    public PageInfo(int pageSize, int currentPage) {
    	super();
    	this.pageSize = pageSize;
    	this.currentPage = currentPage;
    }
    
    public String getCond() {
        return cond;
    }

    public void setCond(String cond) {
        if (cond != null) {
            cond = cond.trim();
            this.cond = cond;
        } else {
            this.cond = "";
        }
    }

    public int getNextPage() {
        this.nextPage = this.currentPage + 1;
        return nextPage;
    }

    public int getPrePage() {
        this.prePage = this.currentPage - 1;
        if (this.prePage < 1) {
            this.prePage = 1;
        }
        return prePage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalReco() {
        return totalReco;
    }

    public void setTotalReco(int totalReco) {
        this.totalReco = totalReco;
    }

    public int getTotalPage() {
        if (this.totalReco % this.pageSize == 0) {
            totalPage = this.totalReco / this.pageSize;
        } else {
            totalPage = (this.totalReco - 1) / this.pageSize + 1;
        }
        return totalPage;
    }

    public int getCurrentPage() {
        if (this.currentPage > this.getTotalPage()) {
            this.currentPage = this.getTotalPage();
        }
        if (this.currentPage <= 1) {
            this.currentPage = 1;
        }
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentRec() {
        this.currentRec = (this.currentPage - 1) * this.pageSize + 1;
        if (this.currentRec < 0) {
            this.currentRec = 0;
        }
        return currentRec;
    }

    public int getNextRec() {
        this.nextRec = (this.currentPage - 1) * this.pageSize + this.pageSize;
        if (this.nextRec > this.totalReco) {
            this.nextRec = this.totalReco;
        }
        return nextRec;
    }

    public int getStRec() {

        return this.stRec = (this.currentPage - 1) * pageSize;
    }

    public void setStRec(int stRec) {
        this.stRec = stRec;
    }
}
