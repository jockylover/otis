package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.data.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskManagementFragment extends Fragment {

    // RecyclerView用于展示任务列表
    private RecyclerView tasksRecyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> tasksList; // 任务列表数据源

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化静态的任务列表
        tasksList = new ArrayList<>();
        tasksList.add(new Task("学习Android开发", 10));
        tasksList.add(new Task("读一章书", 5));
        tasksList.add(new Task("锻炼30分钟", 8));
        tasksList.add(new Task("完成英语练习", 6));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_management, container, false);

        // 初始化RecyclerView和Adapter
        tasksRecyclerView = view.findViewById(R.id.tasks_recycler_view);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 假设您已经有一个TaskAdapter类来处理任务列表的显示
        taskAdapter = new TaskAdapter(tasksList);
        tasksRecyclerView.setAdapter(taskAdapter);

        return view;
    }
}

