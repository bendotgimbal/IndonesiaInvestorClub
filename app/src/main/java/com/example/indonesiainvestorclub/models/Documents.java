package com.example.indonesiainvestorclub.models;

public class Documents {
  private DocumentsID documentID;
  private DocumentsBank documentBank;

  public Documents(DocumentsID documentID,
      DocumentsBank documentBank) {
    this.documentID = documentID;
    this.documentBank = documentBank;
  }

  public DocumentsID getDocumentID() {
    return documentID;
  }

  public void setDocumentID(DocumentsID documentID) {
    this.documentID = documentID;
  }

  public DocumentsBank getDocumentBank() {
    return documentBank;
  }

  public void setDocumentBank(DocumentsBank documentBank) {
    this.documentBank = documentBank;
  }
}
