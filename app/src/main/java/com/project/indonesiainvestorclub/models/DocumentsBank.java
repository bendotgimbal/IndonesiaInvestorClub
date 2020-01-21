package com.project.indonesiainvestorclub.models;

public class DocumentsBank {
    private String status;
    private String img;

    public DocumentsBank(String status, String img) {
        this.status = status;
        this.img = img;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
