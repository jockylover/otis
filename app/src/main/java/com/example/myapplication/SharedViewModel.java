package com.example.myapplication;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Integer> points = new MutableLiveData<>();

    public SharedViewModel() {
        points.setValue(0); // default value
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
}

