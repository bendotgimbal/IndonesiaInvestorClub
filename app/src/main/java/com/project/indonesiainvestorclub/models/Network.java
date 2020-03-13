package com.project.indonesiainvestorclub.models;

import java.util.List;

public class Network {
    private String id;
    private String uplineid;
    private String name;
    private List<NetworkData> networkdata;
    private String commission_usd;
    private String commission_idr;
    private List<NetworkDownline> networkdownline;

    public Network(String id, String uplineid, String name, List<NetworkData> networkdata, String commission_usd, String commission_idr, List<NetworkDownline> networkdownline) {
        this.id = id;
        this.uplineid = uplineid;
        this.name = name;
        this.networkdata = networkdata;
        this.commission_usd = commission_usd;
        this.commission_idr = commission_idr;
        this.networkdownline = networkdownline;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getUplineID() {
        return uplineid;
    }

    public void setUplineID(String uplineid) {
        this.uplineid = uplineid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NetworkData> getNetworkData() {
        return networkdata;
    }

    public void setNetworkData(List<NetworkData> networkdata) {
        this.networkdata = networkdata;
    }

    public String getCommissionUSD() {
        return commission_usd;
    }

    public void setCommissionUSD(String commission_usd) {
        this.commission_usd = commission_usd;
    }

    public String getCommissionIDR() {
        return commission_idr;
    }

    public void setCommissionID(String commission_idr) {
        this.commission_idr = commission_idr;
    }

    public List<NetworkDownline> getNetworkDownline() {
        return networkdownline;
    }

    public void setNetworkDownline(List<NetworkDownline> networkdownline) {
        this.networkdownline = networkdownline;
    }
}
