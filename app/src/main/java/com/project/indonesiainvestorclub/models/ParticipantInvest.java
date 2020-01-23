package com.project.indonesiainvestorclub.models;

public class ParticipantInvest {
    private ParticipantInvestPrevious participantInvestPrevious;
    private ParticipantInvestCurrent participantInvestCurrent;

    public ParticipantInvest(ParticipantInvestPrevious participantInvestPrevious,
                             ParticipantInvestCurrent participantInvestCurrent) {
        this.participantInvestPrevious = participantInvestPrevious;
        this.participantInvestCurrent = participantInvestCurrent;
    }

    public ParticipantInvestPrevious getParticipantInvestPrevious() {
        return participantInvestPrevious;
    }

    public void setParticipantInvestPrevious(ParticipantInvestPrevious participantInvestPrevious) {
        this.participantInvestPrevious = participantInvestPrevious;
    }

    public ParticipantInvestCurrent getParticipantInvestCurrent() {
        return participantInvestCurrent;
    }

    public void setParticipantInvestCurrent(ParticipantInvestCurrent participantInvestCurrent) {
        this.participantInvestCurrent = participantInvestCurrent;
    }
}
