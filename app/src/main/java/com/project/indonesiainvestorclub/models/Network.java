package com.project.indonesiainvestorclub.models;

import java.util.List;

public class Network {
    private String id;
    private String uplineid;
    private String name;
    private List<NetworkData> networkdata;

    public Network(String id, String uplineid, String name, List<NetworkData> networkdata) {
        this.id = id;
        this.uplineid = uplineid;
        this.name = name;
        this.networkdata = networkdata;
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
}
