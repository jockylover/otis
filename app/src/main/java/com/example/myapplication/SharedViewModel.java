package com.example.myapplication;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.data.BillingRecord;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Integer> points = new MutableLiveData<>();
    private MutableLiveData<List<BillingRecord>> billingRecords = new MutableLiveData<>();
    public SharedViewModel() {
        points.setValue(0); // default value
        billingRecords.setValue(new ArrayList<>()); // 初始化账单记录列表
    }

    public void setPoints(int points) {
        this.points.setValue(points);
    }

    public void addPoints(int pointsToAdd) {
        if (points.getValue() != null) {
            this.points.setValue(points.getValue() + pointsToAdd);
        }
    }

    public void subtractPoints(int pointsToSubtract) {
        if (points.getValue() != null) {
            this.points.setValue(Math.max(points.getValue() - pointsToSubtract, 0));
        }
    }

    public LiveData<Integer> getPoints() {
        return points;
    }
    public LiveData<List<BillingRecord>> getBillingRecords() {
        return billingRecords;
    }

    public void addBillingRecord(String description, int pointsChange) {
        List<BillingRecord> currentRecords = billingRecords.getValue();
        if (currentRecords != null) {
            // 添加新的账单记录
            currentRecords.add(new BillingRecord(description, pointsChange));
            billingRecords.setValue(currentRecords); // 更新LiveData
        }
    }
}

