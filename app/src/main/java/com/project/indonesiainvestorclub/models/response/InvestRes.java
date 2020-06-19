package com.project.indonesiainvestorclub.models.response;

import com.project.indonesiainvestorclub.models.CurrentData;
import com.project.indonesiainvestorclub.models.FundInvest;
import com.project.indonesiainvestorclub.models.Invest;
import com.project.indonesiainvestorclub.models.ParticipantInvest;
import com.project.indonesiainvestorclub.models.UserDetailInvest;

import java.util.List;

public class InvestRes {
  private Invest invests;
  private FundInvest funds;
  private ParticipantInvest participant;
  private List<CurrentData> user;
  private UserDetailInvest userInvest;

  public InvestRes(Invest invests, FundInvest funds,
      ParticipantInvest participant,
      List<CurrentData> user) {
    this.invests = invests;
    this.funds = funds;
    this.participant = participant;
    this.user = user;
  }

  public Invest getInvests() {
    return invests;
  }

  public void setInvests(Invest invests) {
    this.invests = invests;
  }

  public FundInvest getFunds() {
    return funds;
  }

  public void setFunds(FundInvest funds) {
    this.funds = funds;
  }

  public ParticipantInvest getParticipant() {
    return participant;
  }

  public void setParticipant(ParticipantInvest participant) {
    this.participant = participant;
  }

  public List<CurrentData> getUser() {
    return user;
  }

  public void setUser(List<CurrentData> user) {
    this.user = user;
  }

  public UserDetailInvest getUserDetailInvest() {
    return userInvest;
  }

  public void setUserDetailInvest(UserDetailInvest participant) {
    this.userInvest = userInvest;
  }
}
