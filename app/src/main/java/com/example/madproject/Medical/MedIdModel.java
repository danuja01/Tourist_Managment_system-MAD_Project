package com.example.madproject.Medical;

public class MedIdModel {
    String name;
    int age;
    double height;
    double weight;
    String bGroup;
    String info;

    public MedIdModel() {

    }

    public MedIdModel(String name, int age, double height, double weight, String bGroup, String info) {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
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
