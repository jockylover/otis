package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.data.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskManagementFragment extends Fragment implements Task.TaskCompletionListener{
    private List<Task> tasksList;
    private TaskAdapter taskAdapter;
    private RecyclerView tasksRecyclerView;
    private ActivityResultLauncher<Intent> addTaskLauncher;
    private TextView pointsTextView;
    private SharedViewModel sharedViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tasksList = new ArrayList<>();
        // 添加一些示例任务
        initializeSampleTasks();
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

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
//    public void setPointsManager(AchievementPointsManager pointsManager) {
//        this.pointsManager = pointsManager;
//    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_management, container, false);
        tasksRecyclerView = view.findViewById(R.id.tasks_recycler_view);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        taskAdapter = new TaskAdapter(tasksList, sharedViewModel, this);
        tasksRecyclerView.setAdapter(taskAdapter);

        Button addButton = view.findViewById(R.id.add_task_button);
        addButton.setOnClickListener(v -> launchAddTaskActivity());

        pointsTextView = view.findViewById(R.id.pointsTextView);
        updatePointsTextView(); // 更新TextView显示的点数

        return view;
    }
    public void setSharedViewModel(SharedViewModel sharedViewModel) {
        this.sharedViewModel = sharedViewModel;
    }
    public void onTaskCompleted(Task task) {
        if (sharedViewModel != null) {
            Toast.makeText(getContext(), "任务完成！获得了 " + task.getCoinValue() + " 点成就点", Toast.LENGTH_SHORT).show();
        }
    }

    private void updatePointsTextView() {
        if (sharedViewModel != null) {
            sharedViewModel.getPoints().observe(getViewLifecycleOwner(), points -> {
                pointsTextView.setText("成就点数: " + points);
            });
        }
    }

    // 初始化示例任务的方法
    private void initializeSampleTasks() {
        tasksList.add(new Task("复习马克思主义原理1小时", 10));
        tasksList.add(new Task("复习计算机网络30分钟", 5));
        tasksList.add(new Task("做一道编程题", 8));
    }
    // 添加任务Activity启动方法
    public void launchAddTaskActivity() {
        Intent intent = new Intent(getActivity(), AddTaskActivity.class);
        addTaskLauncher.launch(intent);
    }
    public void addNewTask(Task newTask) {
        tasksList.add(newTask);
        taskAdapter.notifyDataSetChanged();
    }
    public void onAddTaskButtonClicked(View view) {
        launchAddTaskActivity();
    }
}


