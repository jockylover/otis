package com.example.myapplication;

import static com.example.myapplication.R.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.data.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
// MainActivity.java

    // 标签名称更新
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private FragmentAdapter fragmentAdapter;
    private String[] tabTitles = {"任务管理", "奖励管理", "账单"};
    private ActivityResultLauncher<Intent> addTaskLauncher;
    private List<Task> tasksList = new ArrayList<>();
    private TaskAdapter taskAdapter;
    private RecyclerView tasksRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(fragmentAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabTitles[position])
        ).attach();
    }


    // FragmentAdapter类的更新
    private class FragmentAdapter extends FragmentStateAdapter {
        private static final int NUM_TABS = 3; // 更新标签数量为3

        public FragmentAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new TaskManagementFragment(); // 任务管理页面
                case 1:
                    return new RewardManagementFragment(); // 奖励管理页面
                case 2:
                    return new BillingFragment(); // 账单页面
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return NUM_TABS;
        }
    }

}