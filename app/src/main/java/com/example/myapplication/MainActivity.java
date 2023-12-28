package com.example.myapplication;

import static com.example.myapplication.R.*;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
// MainActivity.java

    // 标签名称更新
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private FragmentAdapter fragmentAdapter;
    private String[] tabTitles = {"任务管理", "奖励管理", "账单"};

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