package com.example.coffeeshopapp.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.model.Order;

import java.util.List;

public class AdminOrderAdapter extends RecyclerView.Adapter<AdminOrderAdapter.ViewHolder> {

    private List<Order> list;

    public AdminOrderAdapter(List<Order> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order o = list.get(position);

        holder.tvOrderId.setText("Đơn #" + o.getId());
        holder.tvCustomer.setText("KH: " + o.getName());
        holder.tvTotal.setText(String.format("%,d đ", o.getTotal()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvCustomer, tvTotal;

        ViewHolder(View v) {
            super(v);
            tvOrderId = v.findViewById(R.id.tv_order_id);
            tvCustomer = v.findViewById(R.id.tv_customer);
            tvTotal = v.findViewById(R.id.tv_total);
        }
    }
}