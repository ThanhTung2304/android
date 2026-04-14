package com.example.coffeeshopapp.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.model.Product;

import java.util.List;

public class AdminProductAdapter extends RecyclerView.Adapter<AdminProductAdapter.ViewHolder> {

    public interface OnEditClick {
        void onClick(Product product);
    }

    public interface OnDeleteClick {
        void onClick(Product product);
    }

    private List<Product> list;
    private OnEditClick onEdit;
    private OnDeleteClick onDelete;

    public AdminProductAdapter(List<Product> list,
                               OnEditClick onEdit,
                               OnDeleteClick onDelete) {
        this.list = list;
        this.onEdit = onEdit;
        this.onDelete = onDelete;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product p = list.get(position);

        holder.txtName.setText(p.getName());

        holder.txtPrice.setText(p.getPrice() + " đ");

        holder.txtCategory.setText("Loại: " + p.getCategory());

        holder.txtHot.setText(p.isHot() ? "🔥 Hot" : "Normal");

        holder.btnEdit.setOnClickListener(v -> onEdit.onClick(p));

        holder.btnDelete.setOnClickListener(v -> onDelete.onClick(p));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtPrice, txtCategory, txtHot;
        ImageButton btnEdit, btnDelete;

        ViewHolder(View v) {
            super(v);

            txtName = v.findViewById(R.id.txtName);
            txtPrice = v.findViewById(R.id.txtPrice);
            txtCategory = v.findViewById(R.id.txtCategory);
            txtHot = v.findViewById(R.id.txtHot);

            btnEdit = v.findViewById(R.id.btnEdit);
            btnDelete = v.findViewById(R.id.btnDelete);
        }
    }
}