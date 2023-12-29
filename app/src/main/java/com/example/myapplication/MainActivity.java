package com.example.myapplication;

import static com.example.myapplication.R.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.data.Reward;
import com.google.android.material.navigation.NavigationView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
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
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private FragmentAdapter fragmentAdapter;
    private String[] tabTitles = {"任务管理", "奖励管理", "账单"};
    private ActivityResultLauncher<Intent> addTaskLauncher;
    private ActivityResultLauncher<Intent> addRewardLauncher;
    private List<Task> tasksList = new ArrayList<>();
    private TaskAdapter taskAdapter;
    private RecyclerView tasksRecyclerView;
    private AchievementPointsManager pointsManager;
    private SharedViewModel sharedViewModel;
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);


        // Setup Drawer Toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        // Navigation Drawer Item Selection Listener
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                handleNavigationItemSelected(menuItem.getItemId());
                return true;
            }
        });

        // Tab and ViewPager Setup
        viewPager = findViewById(id.view_pager);
        tabLayout = findViewById(id.tab_layout);
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(fragmentAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabTitles[position])
        ).attach();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        // Initialize ActivityResultLauncher for adding tasks in the drawer menu
        addTaskLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Task newTask = (Task) data.getSerializableExtra("NEW_TASK");
                            // Notify the appropriate fragment to handle the new task
                            Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("f" + viewPager.getCurrentItem());
                            if (currentFragment instanceof TaskManagementFragment) {
                                ((TaskManagementFragment) currentFragment).addNewTask(newTask);
                            }
                        }
                    }
                }
        );
        addRewardLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Reward newReward = (Reward) data.getSerializableExtra("NEW_REWARD");
                                Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("f" + viewPager.getCurrentItem());
                                if (currentFragment instanceof RewardManagementFragment) {
                                    ((RewardManagementFragment) currentFragment).addNewReward(newReward);
                                }
                        }
                    }
                }
        );
    }
    private void handleNavigationItemSelected(int itemId) {
        if (itemId == R.id.nav_tasks) {
            startAddTaskActivity();
        } else if (itemId == R.id.nav_rewards) {
            startAddRewardActivity();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void startAddTaskActivity() {
//        Intent addTaskIntent = new Intent(MainActivity.this, AddTaskActivity.class);
//        // You can add any additional data or flags to the intent if needed
//        startActivity(addTaskIntent);
        Intent intent = new Intent(this, AddTaskActivity.class);
        addTaskLauncher.launch(intent);

    }

    private void startAddRewardActivity() {
        Intent intent = new Intent(this, AddRewardActivity.class);
        addRewardLauncher.launch(intent);
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
                    TaskManagementFragment taskFragment = new TaskManagementFragment();
                    taskFragment.setSharedViewModel(sharedViewModel);
                    return taskFragment;
                case 1:
                    RewardManagementFragment rewardFragment = new RewardManagementFragment();
                    rewardFragment.setSharedViewModel(sharedViewModel);
                    return rewardFragment;
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