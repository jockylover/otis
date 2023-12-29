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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.data.Reward;

import java.util.ArrayList;
import java.util.List;

public class RewardManagementFragment extends Fragment {
    private RecyclerView rewardsRecyclerView;
    private RewardAdapter rewardAdapter;
    private List<Reward> rewardsList;
    private ActivityResultLauncher<Intent> addRewardLauncher;
    private AchievementPointsManager pointsManager;
    private TextView pointsTextView;
    private SharedViewModel sharedViewModel;
    private RewardManagementFragment.RewardCompletionListener rewardCompletionListener;

    public void setSharedViewModel(SharedViewModel sharedViewModel) {
        this.sharedViewModel = sharedViewModel;
    }
    public interface RewardCompletionListener {
        void onRewardCompleted(Reward reward);
    }
    @Nullable
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化静态的奖励列表
        rewardsList = new ArrayList<>();
        rewardsList.add(new Reward("刷B站十分钟", 5));
        rewardsList.add(new Reward("看一部电影", 10));
        rewardsList.add(new Reward("外出散步", 4));
        rewardsList.add(new Reward("玩一小时游戏", 20));
        addRewardLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Reward newReward = (Reward) data.getSerializableExtra("NEW_REWARD");
                            if (newReward != null) {
                                // Handle the new reward, e.g., add it to the rewards list
                                rewardsList.add(newReward);
                                rewardAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
        );
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reward_management, container, false);

        rewardsRecyclerView = view.findViewById(R.id.rewards_recycler_view);
        rewardsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Button addRewardButton = view.findViewById(R.id.add_reward_button);
        addRewardButton.setOnClickListener(v -> launchAddRewardActivity());
        // 初始化成就点数TextView
        pointsTextView = view.findViewById(R.id.points_text_view);
        updatePointsTextView(); // 更新TextView显示的点数
        sharedViewModel.getPoints().observe(getViewLifecycleOwner(), points -> {
            pointsTextView.setText("成就点数: " + points);
        });
        rewardAdapter = new RewardAdapter(rewardsList, sharedViewModel,rewardCompletionListener);
        rewardsRecyclerView.setAdapter(rewardAdapter);
        return view;
    }
    public void launchAddRewardActivity() {
        Intent intent = new Intent(getActivity(), AddRewardActivity.class);
        addRewardLauncher.launch(intent);
    }
    public void addNewReward(Reward newReward) {
        rewardsList.add(newReward);
        rewardAdapter.notifyDataSetChanged();
    }
    public void onRewardCompleted(Reward reward) {
        // 奖励完成时的处理，比如显示提示信息
        Toast.makeText(getContext(), "奖励 " + reward.getName() + " 完成！", Toast.LENGTH_SHORT).show();
    }
    private void updatePointsTextView() {
        // 更新成就点数的显示
        if (pointsManager != null) {
            int currentPoints = pointsManager.getPoints();
            pointsTextView.setText("成就点数: " + currentPoints);
        }
    }
    public void setPointsManager(AchievementPointsManager pointsManager) {
        this.pointsManager = pointsManager;
    }
}


