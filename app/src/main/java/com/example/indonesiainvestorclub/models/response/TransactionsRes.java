package com.example.indonesiainvestorclub.models.response;

import com.example.indonesiainvestorclub.models.Transactions;

import java.util.List;

public class TransactionsRes {

    private int page;
    private int pages;
    private List<Transactions> transactions;

    public TransactionsRes(int page, int pages,
                           List<Transactions> transactions) {
        this.page = page;
        this.pages = pages;
        this.transactions = transactions;
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

    public List<Transactions> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transactions> transactions) {
        this.transactions = transactions;
    }
}
