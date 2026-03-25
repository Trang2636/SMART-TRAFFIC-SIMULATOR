package com.ra.bt.ex5;

public class Bed {
    private int bedId;
    private String bedName;
    private String status;

    public Bed() {
    }

    public Bed(int bedId, String bedName, String status) {
        this.bedId = bedId;
        this.bedName = bedName;
        this.status = status;
    }

    public int getBedId() {
        return bedId;
    }

    public void setBedId(int bedId) {
        this.bedId = bedId;
    }

    public String getBedName() {
        return bedName;
    }

    public void setBedName(String bedName) {
        this.bedName = bedName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
