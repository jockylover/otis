package com.example.myapplication.data;
import java.util.Date;
public class Reward {
    private String name; // 奖励名称
    private Date createdAt; // 创建时间
    private int coinCost; // 兑换奖励需要消耗的任务币点数

    public Reward(String name, int coinCost) {
        this.name = name;
        this.createdAt = new Date(); // 设置为当前时间
        this.coinCost = coinCost;
    }

    // Getter 和 Setter 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getCoinCost() {
        return coinCost;
    }

    public void setCoinCost(int coinCost) {
        this.coinCost = coinCost;
    }
}
