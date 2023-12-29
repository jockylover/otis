package com.example.myapplication;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.data.Reward;

import java.text.DateFormat;
import java.util.List;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.RewardViewHolder> {
    private List<Reward> rewardsList;
    private SharedViewModel sharedViewModel;

    public RewardAdapter(List<Reward> rewardsList, SharedViewModel sharedViewModel) {
        this.rewardsList = rewardsList;
        this.sharedViewModel = sharedViewModel;
    }

    @NonNull
    @Override
    public RewardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reward, parent, false);
        return new RewardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RewardViewHolder holder, int position) {
        Reward reward = rewardsList.get(position);
        holder.rewardNameTextView.setText(reward.getName());
        holder.rewardCreationTimeTextView.setText(DateFormat.getDateTimeInstance().format(reward.getCreatedAt()));
        holder.rewardCoinCostTextView.setText(reward.getCoinCost() + " coins");
        // 设置CheckBox点击事件
        holder.rewardCheckBox.setOnClickListener(v -> {
            // 弹出对话框前，先取消选中CheckBox
            holder.rewardCheckBox.setChecked(false);
            // 弹出对话框
            new AlertDialog.Builder(holder.itemView.getContext())
                    .setTitle("确认兑换")
                    .setMessage("您确定要兑换奖励 " + reward.getName() + " 吗？")
                    .setPositiveButton("确定", (dialog, which) -> {
                        if (sharedViewModel.getPoints().getValue() >= reward.getCoinCost()) {
                            sharedViewModel.subtractPoints(reward.getCoinCost());
                            if (position < rewardsList.size()) {
                                rewardsList.remove(position);
                                notifyItemRemoved(position);
                            }
                        }else {
                            Toast.makeText(holder.itemView.getContext(), "点数不足，无法兑换此奖励", Toast.LENGTH_SHORT).show();
                        }
//                        rewardsList.remove(position);
//                        notifyItemRemoved(position);
//                        // 这里可以调用一个回调接口通知外部活动进行数据更新和持久化
                    })
                    .setNegativeButton("取消", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return rewardsList.size();
    }

    static class RewardViewHolder extends RecyclerView.ViewHolder {
        TextView rewardNameTextView;
        TextView rewardCreationTimeTextView;
        TextView rewardCoinCostTextView;
        CheckBox rewardCheckBox;

        public RewardViewHolder(@NonNull View itemView) {
            super(itemView);
            rewardNameTextView = itemView.findViewById(R.id.reward_name_text_view);
            rewardCreationTimeTextView = itemView.findViewById(R.id.reward_creation_time_text_view);
            rewardCoinCostTextView = itemView.findViewById(R.id.reward_coin_cost_text_view);
            rewardCheckBox = itemView.findViewById(R.id.reward_checkbox);
        }
    }
}

