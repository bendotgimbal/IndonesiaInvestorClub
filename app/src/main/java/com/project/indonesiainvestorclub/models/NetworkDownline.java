package com.project.indonesiainvestorclub.models;

import java.util.List;

public class NetworkDownline {
    private String id;
    private String uplineid;
    private String name;
    private List<NetworkData> networkData;
    private NetworkDownlineDownline networkDownlineDownline;

    public NetworkDownline(String id, String uplineid, String name, List<NetworkData> networkData, NetworkDownlineDownline networkDownlineDownline) {
        this.id = id;
        this.uplineid = uplineid;
        this.name = name;
        this.networkData = networkData;
        this.networkDownlineDownline = networkDownlineDownline;
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
        return networkData;
    }

    public void setNetworkData(List<NetworkData> networkData) {
        this.networkData = networkData;
    }

    public NetworkDownlineDownline getNetworkDownlineDownline() {
        return networkDownlineDownline;
    }

    public void setNetworkDownlineDownline(NetworkDownlineDownline networkDownlineDownline) {
        this.networkDownlineDownline = networkDownlineDownline;
    }
}
