package com.example.indonesiainvestorclub.models.response;

import com.example.indonesiainvestorclub.models.Funds;

import java.util.List;

public class FundsRes {
    private List<Funds> funds;
    public List<Funds> getFunds() {
        return funds;
    }
    public void setFunds(List<Funds> funds) {
        this.funds = funds;
    }
}
