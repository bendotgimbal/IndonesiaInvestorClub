package com.example.indonesiainvestorclub.models;

import java.util.List;

public class Funds {
    private String name;
    private String type;
    private String manager;
    private String invested;
    private String equity;
    private String slots;
    private String compounding;
    private String roi;
    private Meta meta;

    public Funds(String name, String type, String manager, String invested, String equity,
        String slots, String compounding, String roi,
        Meta meta) {
        this.name = name;
        this.type = type;
        this.manager = manager;
        this.invested = invested;
        this.equity = equity;
        this.slots = slots;
        this.compounding = compounding;
        this.roi = roi;
        this.meta = meta;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getInvested() {
        return invested;
    }

    public void setInvested(String invested) {
        this.invested = invested;
    }

    public String getEquity() {
        return equity;
    }

    public void setEquity(String equity) {
        this.equity = equity;
    }

    public String getSlots() {
        return slots;
    }

    public void setSlots(String slots) {
        this.slots = slots;
    }

    public String getCompounding() {
        return compounding;
    }

    public void setCompounding(String compounding) {
        this.compounding = compounding;
    }

    public String getROI() {
        return roi;
    }

    public void setROI(String roi) {
        this.roi = roi;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
