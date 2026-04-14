package com.example.coffeeshopapp.admin;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.model.User;

import java.util.List;

public class AdminUserAdapter extends RecyclerView.Adapter<AdminUserAdapter.ViewHolder> {

    public interface OnRoleClick { void onClick(User user); }
    public interface OnDeleteClick { void onClick(User user); }

    private List<User> list;
    private OnRoleClick onRoleClick;
    private OnDeleteClick onDeleteClick;

    public AdminUserAdapter(List<User> list, OnRoleClick onRoleClick, OnDeleteClick onDeleteClick) {
        this.list = list;
        this.onRoleClick = onRoleClick;
        this.onDeleteClick = onDeleteClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User u = list.get(position);
        holder.tvUsername.setText(u.getUsername());
//        holder.tvEmail.setText(u.getEmail() != null ? u.getEmail() : "");
        holder.tvRole.setText(u.getRole());

        // Màu role
        switch (u.getRole()) {
            case "ADMIN":
                holder.tvRole.setTextColor(Color.parseColor("#E53935")); // đỏ
                break;
            case "STAFF":
                holder.tvRole.setTextColor(Color.parseColor("#1976D2")); // xanh dương
                break;
            default:
                holder.tvRole.setTextColor(Color.parseColor("#388E3C")); // xanh lá
                break;
        }

        holder.btnChangeRole.setOnClickListener(v -> onRoleClick.onClick(u));
        holder.btnDelete.setOnClickListener(v -> onDeleteClick.onClick(u));
    }

    @Override
    public int getItemCount() { return list.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername, tvEmail, tvRole;
        Button btnChangeRole, btnDelete;

        ViewHolder(View v) {
            super(v);
            tvUsername = v.findViewById(R.id.tv_username);
//            tvEmail = v.findViewById(R.id.tv_email);
            tvRole = v.findViewById(R.id.tv_role);
            btnChangeRole = v.findViewById(R.id.btn_change_role);
            btnDelete = v.findViewById(R.id.btn_delete_user);
        }
    }
}
