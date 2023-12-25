package com.example.myapplication;

import java.io.Serializable;

public class Task implements Serializable {

    private String name;
    private int rewardPoints;
    private String deadline;
    private boolean completed;

    public Task(String name, int rewardPoints, String deadline, boolean completed) {
        this.name = name;
        this.rewardPoints = rewardPoints;
        this.deadline = deadline;
        this.completed = completed;
    }

    // Getter 方法

    public String getName() {
        return name;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public String getDeadline() {
        return deadline;
    }

    public boolean isCompleted() {
        return completed;
    }
}

