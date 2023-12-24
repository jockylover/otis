package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        // 设置 TabLayout
        tabLayout.addTab(tabLayout.newTab().setText("进行中"));
        tabLayout.addTab(tabLayout.newTab().setText("已完成"));
        tabLayout.addTab(tabLayout.newTab().setText("成就点数"));

        // 设置 RecyclerView
        List<Task> taskList = generateTaskList();
        adapter = new TaskAdapter(taskList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // 切换 Tab 时更新 RecyclerView
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // 根据选择的 Tab 更新 RecyclerView 的内容
                int position = tab.getPosition();
                updateRecyclerView(position, taskList);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private List<Task> generateTaskList() {
        // 在这里生成任务列表数据
        List<Task> taskList = new ArrayList<>();
        // 添加示例数据
        taskList.add(new Task("任务1", 10, "2023-12-31", false));
        taskList.add(new Task("任务2", 15, "2023-12-15", true));
        taskList.add(new Task("任务3", 8, "2023-11-30", false));
        return taskList;
    }

    private void updateRecyclerView(int tabPosition, List<Task> taskList) {
        List<Task> filteredList;
        switch (tabPosition) {
            case 0:
                filteredList = filterTasks(taskList, false);
                break;
            case 1:
                filteredList = filterTasks(taskList, true);
                break;
            case 2:
                // 显示成就点数的逻辑
                showAchievementPoints();
                return;
            default:
                filteredList = taskList;
        }
        adapter.updateList(filteredList);
    }

    private List<Task> filterTasks(List<Task> taskList, boolean completed) {
        List<Task> filteredList = new ArrayList<>();
        for (Task task : taskList) {
            if (task.isCompleted() == completed) {
                filteredList.add(task);
            }
        }
        return filteredList;
    }

    // 创建菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // 处理菜单项点击事件
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                // 添加任务
                addTask();
                return true;
            case 1:
                // 删除任务
                deleteTask();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // 添加任务
    private void addTask() {
        // 在这里处理添加任务的逻辑
        // 例如，弹出对话框让用户输入任务信息，并将任务添加到任务列表中
    }

    // 删除任务
    private void deleteTask() {
        // 在这里处理删除任务的逻辑
        // 例如，弹出对话框确认删除，并从任务列表中移除选定的任务
    }

    // 显示成就点数
    private void showAchievementPoints() {
        // 在这里处理显示成就点数的逻辑
        // 例如，弹出对话框显示用户的成就点数
    }
}

