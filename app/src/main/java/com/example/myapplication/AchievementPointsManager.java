package com.example.myapplication;
public class AchievementPointsManager {
    private int points;

    public AchievementPointsManager() {
        this.points = 0; // 初始成就点数，可以从持久化存储中加载
    }

    public void addPoints(int pointsToAdd) {
        this.points += pointsToAdd;
    }

    public void subtractPoints(int pointsToSubtract) {
        this.points -= pointsToSubtract;
        if (this.points < 0) {
            this.points = 0; // 防止点数变成负数
        }
        // 更新存储的点数或通知UI更新
    }

    public int getPoints() {
        return points;
    }
}

