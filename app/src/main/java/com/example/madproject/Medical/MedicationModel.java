package com.example.madproject.Medical;

public class MedicationModel {
    String name, period, time;

    public MedicationModel() {
    }

    public MedicationModel(String name, String period, String time) {
        this.name = name;
        this.period = period;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
