package com.example.myapplication.data;

import java.util.Date;

public class BillingRecord {
    private String description; // 描述
    private int pointsChange; // 成就点数的变化
    private Date timestamp; // 时间戳

    public BillingRecord(String description, int pointsChange) {
        this.description = description;
        this.pointsChange = pointsChange;
        this.timestamp = new Date(); // 当前时间
    }

    // Getter 和 Setter
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPointsChange() {
        return pointsChange;
    }

    public void setPointsChange(int pointsChange) {
        this.pointsChange = pointsChange;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}

