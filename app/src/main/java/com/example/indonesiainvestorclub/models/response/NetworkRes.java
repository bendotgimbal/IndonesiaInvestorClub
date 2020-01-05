package com.example.indonesiainvestorclub.models.response;

import com.example.indonesiainvestorclub.models.Commissions;

import java.util.List;

public class NetworkRes {
    private List<Commissions> commissions;

    public List<Commissions> getCommissions() {
        return commissions;
    }

    public void setCommissions(List<Commissions> commissions) {
        this.commissions = commissions;
    }

    public NetworkRes(List<Commissions> commissions) {
        this.commissions = commissions;
    }
}
