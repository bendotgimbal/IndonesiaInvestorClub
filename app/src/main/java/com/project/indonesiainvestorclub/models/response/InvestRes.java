package com.project.indonesiainvestorclub.models.response;

import com.project.indonesiainvestorclub.models.CurrentData;
import com.project.indonesiainvestorclub.models.Datas;
import com.project.indonesiainvestorclub.models.FundInvest;
import com.project.indonesiainvestorclub.models.Invest;
import com.project.indonesiainvestorclub.models.ParticipantInvest;
import com.project.indonesiainvestorclub.models.PerformanceInvest;
import com.project.indonesiainvestorclub.models.UserInvest;

import java.util.List;

public class InvestRes {
//    private List<Invest> invests;
////    private List<Datas> datas;
//
//    public InvestRes(List<Invest> invests) {
//        this.invests = invests;
////        this.datas = datas;
//    }
//
//    public List<Invest> getInvest() {
//        return invests;
//    }
//
//    public void setInvest(List<Invest> invests) {
//        this.invests = invests;
//    }
//
////    public List<Datas> getDatas() {
////        return datas;
////    }
////
////    public void setDatas(List<Datas> datas) {
////        this.datas = datas;
////    }

//    private List<Invest> invests;
//
//    public List<Invest> getInvests() {
//        return invests;
//    }
//
//    public void setInvests(List<Invest> invest) {
//        this.invests = invests;
//    }


//    private Invest invests;
//
//    public Invest getInvests() {
//        return invests;
//    }
//
//    public void setInvests(Invest invests) {
//        this.invests = invests;
//    }

    private Invest invests;
    private FundInvest fundinvests;
    private ParticipantInvest participantInvest;
    private List<CurrentData> user;
    private UserInvest userInvest;

    public InvestRes(Invest invests, FundInvest fundinvests,
                     ParticipantInvest participantInvest,
                     List<CurrentData> user,UserInvest userInvest) {
        this.invests = invests;
        this.fundinvests = fundinvests;
        this.participantInvest = participantInvest;
        this.user = user;
        this.userInvest = userInvest;
    }

    public InvestRes(Invest invest, FundInvest fundInvest, ParticipantInvest participantInvest, List<CurrentData> users) {
    }

    public Invest getInvests() {
        return invests;
    }

    public void setInvests(Invest invests) {
        this.invests = invests;
    }

    public FundInvest getFundInvests() {
        return fundinvests;
    }

    public void setFundInvests(FundInvest fundinvests) {
        this.fundinvests = fundinvests;
    }

    public ParticipantInvest getParticipantInvest() {
        return participantInvest;
    }

    public void setParticipantInvest(ParticipantInvest participantInvest) {
        this.participantInvest = participantInvest;
    }

    public List<CurrentData> getUser() {
        return user;
    }

    public void setUser(List<CurrentData> user) {
        this.user = user;
    }

    public UserInvest getUserInvest() {
        return userInvest;
    }

    public void setUserInvest(UserInvest userInvest) {
        this.userInvest = userInvest;
    }


//    private PerformanceInvest performance;
//    private FundInvest fund;
//    private ParticipantInvest participant;
//    private UserInvest user;
//
//    public InvestRes(PerformanceInvest performance, FundInvest fund, ParticipantInvest participant,
//                     UserInvest user) {
//        this.performance = performance;
//        this.fund = fund;
//        this.participant = participant;
//        this.user = user;
//    }
//
//    public InvestRes(ParticipantInvest participantInvest, List<FundInvest> fundsInvestList, ParticipantInvest participantInvest1, ParticipantInvest participantInvest2) {
//    }
//
//    public PerformanceInvest getPerformanceInvest() {
//        return performance;
//    }
//
//    public void setPerformanceInvest(PerformanceInvest performance) {
//        this.performance = performance;
//    }
//
//    public FundInvest getFundInvest() {
//        return fund;
//    }
//
//    public void setFundInvest(FundInvest fund) {
//        this.fund = fund;
//    }
//
//    public ParticipantInvest getParticipantInvest() {
//        return participant;
//    }
//
//    public void setParticipantInvest(ParticipantInvest participant) {
//        this.participant = participant;
//    }
//
//    public UserInvest getUserInvest() {
//        return user;
//    }
//
//    public void setUserInvest(UserInvest user) {
//        this.user = user;
//    }

//    String name;
//    Datas datas;
//
//    public InvestRes(String name, Datas datas) {
//        this.name = name;
//        this.datas = datas;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setame(String name) {
//        this.name = name;
//    }
//
//    public Datas getDatas() {
//        return datas;
//    }
//
//    public void setDatas(Datas datas) {
//        this.datas = datas;
//    }
}
