package com.example.indonesiainvestorclub.models;

import java.util.List;

public class Login {
    private String id;
    private String refcode;
    private String email;
    private String sponsor;
    private String network;
    private String avatar;
    private List<Groups> groups;

    public Login(String id, String refcode, String email, String sponsor, String network,
        String avatar, List<Groups> groups) {
        this.id = id;
        this.refcode = refcode;
        this.email = email;
        this.sponsor = sponsor;
        this.network = network;
        this.avatar = avatar;
        this.groups = groups;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getRefCode() {
        return refcode;
    }

    public void setRefCode(String refcode) {
        this.id = refcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.id = email;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<Groups> getGroups() {
        return groups;
    }
    public void setGroups(List<Groups> groups) {
        this.groups = groups;
    }
}
