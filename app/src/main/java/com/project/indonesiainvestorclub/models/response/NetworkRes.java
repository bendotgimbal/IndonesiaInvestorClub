package com.project.indonesiainvestorclub.models.response;

import com.project.indonesiainvestorclub.models.Commissions;

import java.util.List;

public class NetworkRes {

    private int page;
    private int pages;
    private List<Commissions> commissions;

    public NetworkRes(int page, int pages,List<Commissions> commissions) {
        this.page = page;
        this.pages = pages;
        this.commissions = commissions;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<Commissions> getCommissions() {
        return commissions;
    }

    public void setCommissions(List<Commissions> commissions) {
        this.commissions = commissions;
    }
}
