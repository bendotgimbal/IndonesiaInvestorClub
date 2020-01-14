package com.example.indonesiainvestorclub.models;

public class Invest {
    String name;
    Datas datas;

    public Invest(String name, Datas datas) {
        this.name = name;
        this.datas = datas;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Datas getData() {
        return datas;
    }

    public void setData(Datas data) {
        this.datas = data;
    }
}
