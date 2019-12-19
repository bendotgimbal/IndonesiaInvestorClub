package com.example.indonesiainvestorclub.models;

import java.util.List;

public class Documents {
    private List<DocumentsID> documentID;
    private List<DocumentsBank> documentBank;

    public List<DocumentsID> getDocumentsID() {
        return documentID;
    }

    public void setDocumentsID(List<DocumentsID> documentID) {
        this.documentID = documentID;
    }

    public List<DocumentsBank> getDocumentsBank() {
        return documentBank;
    }

    public void seDocumentsBank(List<DocumentsBank> documentBank) {
        this.documentBank = documentBank;
    }
}
