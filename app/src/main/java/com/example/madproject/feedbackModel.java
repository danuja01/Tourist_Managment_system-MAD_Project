package com.example.madproject;

public class feedbackModel {


    String additional, destination, enjoyable, rate, turl;

    feedbackModel() {

    }

    public feedbackModel(String additional, String destination, String enjoyable, String rate, String turl) {
        this.additional = additional;
        this.destination = destination;
        this.enjoyable = enjoyable;
        this.rate = rate;
        this.turl = turl;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getEnjoyable() {
        return enjoyable;
    }

    public void setEnjoyable(String enjoyable) {
        this.enjoyable = enjoyable;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
    public String getTurl() {
        return turl;
    }

    public void setTurl(String turl) {
        this.turl = turl;
    }
}
