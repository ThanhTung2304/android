package com.example.coffeeshopapp.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.database.DatabaseHelper;
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


        // --- Xử lý sự kiện nút XÓA ---
        holder.btnDelete.setOnClickListener(v -> {
            // Lấy vị trí thực tế của item
            int currentPos = holder.getAdapterPosition();

            new AlertDialog.Builder(v.getContext())
                    .setTitle("Xác nhận")
                    .setMessage("Bạn có chắc muốn xóa đơn hàng này không?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        DatabaseHelper db = new DatabaseHelper(v.getContext());
                        // Gọi hàm xóa trong DB (Linh nhớ thêm hàm deleteOrder vào DatabaseHelper nhé)
                        if (db.deleteOrder(o.getId())) {
                            list.remove(currentPos);
                            notifyItemRemoved(currentPos);
                            notifyItemRangeChanged(currentPos, list.size());
                            Toast.makeText(v.getContext(), "Đã xóa đơn hàng!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvCustomer, tvTotal;
        Button btnDelete;

        ViewHolder(View v) {
            super(v);
            tvOrderId = v.findViewById(R.id.tv_order_id);
            tvCustomer = v.findViewById(R.id.tv_customer);
            tvTotal = v.findViewById(R.id.tv_total);
            btnDelete = v.findViewById(R.id.btn_delete_order);
        }
    }
}