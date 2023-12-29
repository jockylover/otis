package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.data.BillingRecord;

import java.text.DateFormat;
import java.util.List;

public class BillingAdapter extends RecyclerView.Adapter<BillingAdapter.BillingViewHolder> {
    private List<BillingRecord> billingRecords;

    public BillingAdapter(List<BillingRecord> billingRecords) {
        this.billingRecords = billingRecords;
    }

    @NonNull
    @Override
    public BillingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_billing_record, parent, false);
        return new BillingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillingViewHolder holder, int position) {
        BillingRecord record = billingRecords.get(position);
        holder.descriptionTextView.setText(record.getDescription());
        holder.timestampTextView.setText(DateFormat.getDateTimeInstance().format(record.getTimestamp()));

        int pointsChange = record.getPointsChange();
        if (pointsChange >= 0) {
            holder.pointsChangeTextView.setText("+" + pointsChange + " coins");
            holder.pointsChangeTextView.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.holo_green_dark));
        } else {
            holder.pointsChangeTextView.setText(String.valueOf(pointsChange) + " coins");
            holder.pointsChangeTextView.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.holo_red_dark));
        }
    }

    @Override
    public int getItemCount() {
        return billingRecords.size();
    }

    static class BillingViewHolder extends RecyclerView.ViewHolder {
        TextView descriptionTextView;
        TextView pointsChangeTextView;
        TextView timestampTextView;

        public BillingViewHolder(@NonNull View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.description_text_view);
            pointsChangeTextView = itemView.findViewById(R.id.points_change_text_view);
            timestampTextView = itemView.findViewById(R.id.timestamp_text_view);
        }
    }
    public void setRecords(List<BillingRecord> newRecords) {
        this.billingRecords = newRecords;
        notifyDataSetChanged(); // 通知数据已更改
    }
}
