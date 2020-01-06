package com.example.indonesiainvestorclub.models.response;

import com.example.indonesiainvestorclub.models.Portfolios;

import java.util.List;

public class PortfolioRes {
    private List<Portfolios> porfolios;

    public List<Portfolios> getPorfolios() {
        return porfolios;
    }

    public void setPorfolios(List<Portfolios> porfolios) {
        this.porfolios = porfolios;
    }

    public PortfolioRes(List<Portfolios> porfolios) {
        this.porfolios = porfolios;
    }
}