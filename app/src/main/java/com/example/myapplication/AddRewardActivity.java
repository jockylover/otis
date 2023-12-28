package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.data.Reward;

public class AddRewardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reward);

        EditText rewardNameEditText = findViewById(R.id.reward_name_edit_text);
        EditText rewardCostEditText = findViewById(R.id.reward_cost_edit_text);
        Button confirmButton = findViewById(R.id.confirm_button);

        confirmButton.setOnClickListener(v -> {
            String rewardName = rewardNameEditText.getText().toString().trim();
            String rewardCostString = rewardCostEditText.getText().toString().trim();

            // 检查奖励名称是否为空
            if (rewardName.isEmpty()) {
                rewardNameEditText.setError("奖励名称不能为空");
                rewardNameEditText.requestFocus();
                return;
            }

            // 检查成就点数是否为有效的数字
            int rewardCost;
            try {
                rewardCost = Integer.parseInt(rewardCostString);
            } catch (NumberFormatException e) {
                rewardCostEditText.setError("请输入有效的数字");
                rewardCostEditText.requestFocus();
                return;
            }

            // 创建奖励对象并设置结果
            Reward newReward = new Reward(rewardName, rewardCost);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("NEW_REWARD", newReward); // 'newReward' 需要实现 Serializable
            setResult(RESULT_OK, resultIntent);

            // 注意处理输入验证和错误处理

            finish(); // 关闭Activity
        });
    }
}

