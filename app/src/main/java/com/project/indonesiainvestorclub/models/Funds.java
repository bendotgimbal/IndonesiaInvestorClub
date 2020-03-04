package com.project.indonesiainvestorclub.models;

public class Funds {
  private String id;
  private String name;
  private String type;
  private String manager;
  private String invested;
  private String equity;
  private String slots;
  private String compounding;
  private String roi;
  private Meta meta;
  private Bank bank;
//  private String usdidr;
  private String idr_value;

  int index;

  public Funds(String id, String name, String type, String manager, String invested, String equity,
      String slots, String compounding, String roi, Meta meta, String idr_value) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.manager = manager;
    this.invested = invested;
    this.equity = equity;
    this.slots = slots;
    this.compounding = compounding;
    this.roi = roi;
    this.meta = meta;
    this.idr_value = idr_value;
  }

//  public Funds(String name, String type, String manager, String invested, String equity,
//      String slots, String compounding, String roi, Meta meta, Bank bank, String usdidr) {
//    this.name = name;
//    this.type = type;
//    this.manager = manager;
//    this.invested = invested;
//    this.equity = equity;
//    this.slots = slots;
//    this.compounding = compounding;
//    this.roi = roi;
//    this.meta = meta;
//    this.bank = bank;
//    this.usdidr = usdidr;
//  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public String getID() {
    return id;
  }

  public void setID(String id) {
    this.id = id;
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

  public String getTypeManager(){
    return getType()+" - "+getManager();
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

  public Bank getBank() {
    return bank;
  }

  public void setBank(Bank bank) {
    this.bank = bank;
  }

//  public String getUsdidr() {
//    return usdidr;
//  }
//
//  public void setUsdidr(String usdidr) {
//    this.usdidr = usdidr;
//  }

  public String getIDRValue() {
    return idr_value;
  }

  public void setIDRValue(String idr_value) {
    this.idr_value = idr_value;
  }
}
