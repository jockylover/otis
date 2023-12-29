package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.data.BillingRecord;

import java.util.ArrayList;
import java.util.List;

public class BillingFragment extends Fragment {
    private RecyclerView billingRecyclerView;
    private BillingAdapter billingAdapter;
    private SharedViewModel sharedViewModel;
    private TextView emptyView;
    private TextView currentPointsView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_billing, container, false);

        // Initialize the RecyclerView and its adapter
        billingRecyclerView = view.findViewById(R.id.billing_recycler_view);
        emptyView = view.findViewById(R.id.empty_view);
        billingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        billingAdapter = new BillingAdapter(new ArrayList<>());
        billingRecyclerView.setAdapter(billingAdapter);
        currentPointsView = view.findViewById(R.id.current_points_view);
        // Initialize the SharedViewModel
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getPoints().observe(getViewLifecycleOwner(), points ->
                currentPointsView.setText("当前成就点数: " + points));
        // Observe the billing records from the ViewModel
        sharedViewModel.getBillingRecords().observe(getViewLifecycleOwner(), newRecords -> {
            updateBillingRecords(newRecords);
            if (newRecords.isEmpty()) {
                billingRecyclerView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            } else {
                billingRecyclerView.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
            }
        });

        return view;
    }

    // Method to update the billing records in the adapter
    private void updateBillingRecords(List<BillingRecord> newRecords) {
        billingAdapter.setRecords(newRecords);
        billingAdapter.notifyDataSetChanged();
    }
}
