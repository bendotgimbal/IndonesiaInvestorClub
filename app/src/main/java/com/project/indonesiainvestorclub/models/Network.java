package com.project.indonesiainvestorclub.models;

import java.util.List;

public class Network {
    private String id;
    private String uplineid;
    private String name;
    private List<NetworkDownline> networkdownline;

public Network(String id, String uplineid, String name, List<NetworkDownline> networkdownline) {
        this.id = id;
        this.uplineid = uplineid;
        this.name = name;
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

    public List<NetworkDownline> getNetworkDownline() {
        return networkdownline;
    }

    public void setNetworkDownline(List<NetworkDownline> networkdownline) {
        this.networkdownline = networkdownline;
    }
}
