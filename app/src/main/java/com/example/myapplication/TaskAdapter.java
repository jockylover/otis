package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.data.Task;

import java.text.DateFormat;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasksList;
    private Task.TaskCompletionListener taskCompletionListener;
    private Context context;
    public TaskAdapter(List<Task> tasksList) {
        this.tasksList = tasksList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasksList.get(position);
        holder.taskNameTextView.setText(task.getName());
        holder.taskCreationTimeTextView.setText(DateFormat.getDateTimeInstance().format(task.getCreatedAt())); // 使用合适的格式化
        holder.taskCoinValueTextView.setText(task.getCoinValue() + " points");
        // 设置点数文本颜色
        holder.taskCoinValueTextView.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.blue_color));
        // 设置CheckBox点击事件
        holder.taskCheckBox.setOnClickListener(v -> {
            // 弹出对话框前，先取消选中CheckBox
            holder.taskCheckBox.setChecked(false);
            // 弹出对话框
            new AlertDialog.Builder(holder.itemView.getContext())
                    .setTitle("确认完成")
                    .setMessage("您确定已完成任务 " + task.getName() + " 吗？")
                    .setPositiveButton("确定", (dialog, which) -> {
                        // TODO: 增加成就点数逻辑
                        // TODO: 从列表中移除任务
                        tasksList.remove(position);
                        notifyItemRemoved(position);
                        // 这里可以调用一个回调接口通知外部活动进行数据更新和持久化
                    })
                    .setNegativeButton("取消", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView taskNameTextView;
        TextView taskCreationTimeTextView;
        TextView taskCoinValueTextView;
        CheckBox taskCheckBox;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskNameTextView = itemView.findViewById(R.id.task_name_text_view);
            taskCreationTimeTextView = itemView.findViewById(R.id.task_creation_time_text_view);
            taskCoinValueTextView = itemView.findViewById(R.id.task_coin_value_text_view);
            taskCheckBox = itemView.findViewById(R.id.task_checkbox);
        }
    }
}


