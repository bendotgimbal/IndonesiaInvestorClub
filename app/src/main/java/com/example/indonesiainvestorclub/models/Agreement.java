package com.example.indonesiainvestorclub.models;

import java.util.List;

public class Agreement {
    String parent;
    List<Childs> childs;

    public String getParent() {
        return parent;
    }

    public void setParent(String author) {
        this.parent = parent;
    }
}
