package com.example.madproject.Medical;

public class MedIdModel {
    String name;
    String age;
    String height;
    String weight;
    String bGroup;
    String info;

    public MedIdModel() {

    }

    public MedIdModel(String name, String age, String height, String weight, String bGroup, String info) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.bGroup = bGroup;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getbGroup() {
        return bGroup;
    }

    public void setbGroup(String bGroup) {
        this.bGroup = bGroup;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
