package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity {

    private EditText taskNameEditText;
    private EditText dueDateEditText;
    private EditText rewardPointsEditText;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskNameEditText = findViewById(R.id.editTextTaskName);
        dueDateEditText = findViewById(R.id.editTextDueDate);
        rewardPointsEditText = findViewById(R.id.editTextRewardPoints);
        addButton = findViewById(R.id.buttonAddTask);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });
    }
    private void addTask() {
        String taskName = taskNameEditText.getText().toString();
        String dueDate = dueDateEditText.getText().toString();
        int rewardPoints = Integer.parseInt(rewardPointsEditText.getText().toString());

        // Create a new Task object with the entered values
        Task newTask = new Task(taskName, rewardPoints, dueDate, false);
        // Send the newTask back to MainActivity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("newTask", newTask);
        setResult(RESULT_OK, resultIntent);
        // Close the activity
        finish();
    }
}

