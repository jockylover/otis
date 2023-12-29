package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.data.Task;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        EditText taskNameEditText = findViewById(R.id.task_name_edit_text);
        EditText taskPointEditText = findViewById(R.id.task_point_edit_text);
        Button confirmButton = findViewById(R.id.confirm_button);

        confirmButton.setOnClickListener(v -> {
                String taskName = taskNameEditText.getText().toString().trim();
                String taskPointsString = taskPointEditText.getText().toString().trim();

                // 检查任务名称是否为空
                if (taskName.isEmpty()) {
                    taskNameEditText.setError("任务名称不能为空");
                    taskNameEditText.requestFocus();
                    return;
                }

                // 检查点数是否为有效的数字
                int taskPoints;
                try {
                    taskPoints = Integer.parseInt(taskPointsString);
                } catch (NumberFormatException e) {
                    taskPointEditText.setError("请输入有效的数字");
                    taskPointEditText.requestFocus();
                    return;
                }

                // 创建任务对象并设置结果
            Task newTask = new Task(taskName, taskPoints);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("NEW_TASK", newTask);
            setResult(RESULT_OK, resultIntent);


            finish(); // 关闭Activity
        });
    }


}

