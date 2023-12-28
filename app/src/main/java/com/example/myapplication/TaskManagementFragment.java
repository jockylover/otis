package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.data.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskManagementFragment extends Fragment {
    private List<Task> tasksList;
    private TaskAdapter taskAdapter;
    private RecyclerView tasksRecyclerView;
    private ActivityResultLauncher<Intent> addTaskLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tasksList = new ArrayList<>();
        // 添加一些示例任务
        initializeSampleTasks();
        taskAdapter = new TaskAdapter(tasksList);

        // 初始化 ActivityResultLauncher
        addTaskLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Task newTask = (Task) data.getSerializableExtra("NEW_TASK");
                            tasksList.add(newTask);
                            taskAdapter.notifyDataSetChanged();
                        }
                    }
                }
        );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_management, container, false);
        tasksRecyclerView = view.findViewById(R.id.tasks_recycler_view);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tasksRecyclerView.setAdapter(taskAdapter);

        // 初始化添加任务按钮等其他UI元素
        Button addButton = view.findViewById(R.id.add_task_button);
        addButton.setOnClickListener(v -> launchAddTaskActivity());

        return view;
    }
    // 初始化示例任务的方法
    private void initializeSampleTasks() {
        tasksList.add(new Task("完成Android项目", 10));
        tasksList.add(new Task("阅读技术文章", 5));
        tasksList.add(new Task("编写代码练习", 8));
    }
    // 添加任务Activity启动方法
    public void launchAddTaskActivity() {
        Intent intent = new Intent(getActivity(), AddTaskActivity.class);
        addTaskLauncher.launch(intent);
    }
    public void onAddTaskButtonClicked(View view) {
        launchAddTaskActivity();
    }
}


