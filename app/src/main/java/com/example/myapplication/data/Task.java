package com.example.myapplication.data;
import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {
    private String name; // 任务名称
    private Date createdAt; // 创建时间
    private int coinValue; // 完成任务后可以获得的任务币点数

    public Task(String name, int coinValue) {
        this.name = name;
        this.createdAt = new Date(); // 设置为当前时间
        this.coinValue = coinValue;
    }

    // Getter 和 Setter 方法
    public String getName() {
        return name;
    }
    public interface TaskCompletionListener {
        void onTaskCompleted(Task task);
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

    public int getCoinValue() {
        return coinValue;
    }

    public void setCoinValue(int coinValue) {
        this.coinValue = coinValue;
    }
}
