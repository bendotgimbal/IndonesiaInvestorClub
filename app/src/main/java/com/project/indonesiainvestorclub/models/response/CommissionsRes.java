package com.project.indonesiainvestorclub.models.response;

import com.project.indonesiainvestorclub.models.Commissions;

import java.util.List;

public class CommissionsRes {
    int page, pages;
    List<Commissions> commissionsList;

    public CommissionsRes(int page, int pages, List<Commissions> commissionsList) {
        this.page = page;
        this.pages = pages;
        this.commissionsList = commissionsList;
    }

    public int getPage() {
        return page;
    }

    public int getPages() {
        return pages;
    }

    public List<Commissions> getCommissionsList() {
        return commissionsList;
    }
}
