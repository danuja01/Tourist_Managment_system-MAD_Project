package com.example.madproject.Medical;

public class MedicationModel {
    String name;
    String period;
    String time;
    String id;

    public MedicationModel() {
    }

    public MedicationModel(String name, String period, String time, String id) {
        this.name = name;
        this.period = period;
        this.time = time;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
