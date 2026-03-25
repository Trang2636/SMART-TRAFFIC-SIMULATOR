package com.ra.bt.ex5;

public class Patient {
    private int patientId;
    private String fullName;
    private int age;
    private int bedId;

    public Patient() {
    }

    public Patient(String fullName, int age, int bedId) {
        this.fullName = fullName;
        this.age = age;
        this.bedId = bedId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getBedId() {
        return bedId;
    }

    public void setBedId(int bedId) {
        this.bedId = bedId;
    }
}
