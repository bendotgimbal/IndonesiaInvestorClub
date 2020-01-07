package com.example.indonesiainvestorclub.models.response;

import com.example.indonesiainvestorclub.models.Portfolios;

import java.util.List;

public class PortfolioRes {

    private int page;
    private int pages;
    private List<Portfolios> porfolios;

    public PortfolioRes(int page, int pages,
        List<Portfolios> porfolios) {
        this.page = page;
        this.pages = pages;
        this.porfolios = porfolios;
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

    public List<Portfolios> getPorfolios() {
        return porfolios;
    }

    public void setPorfolios(List<Portfolios> porfolios) {
        this.porfolios = porfolios;
    }
}