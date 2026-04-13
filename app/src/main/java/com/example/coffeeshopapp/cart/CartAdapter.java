package com.example.coffeeshopapp.cart;

import android.content.Context;
import android.view.*;
import android.widget.*;

import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.database.DatabaseHelper;
import com.example.coffeeshopapp.model.Cart;

import java.util.List;

import android.graphics.Paint;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    Context context;
    List<Cart> list;
    DatabaseHelper db;
    Runnable updateTotal;

    public CartAdapter(Context context, List<Cart> list, Runnable updateTotal) {
        this.context = context;
        this.list = list;
        this.updateTotal = updateTotal;
        db = new DatabaseHelper(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, oldPrice, qty;
        Button plus, minus;
        ImageButton delete;
        CheckBox chk;

        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.txtName);
            price = v.findViewById(R.id.txtPrice);
            oldPrice = v.findViewById(R.id.txtOldPrice);
            qty = v.findViewById(R.id.txtQty);
            plus = v.findViewById(R.id.btnPlus);
            minus = v.findViewById(R.id.btnMinus);
            delete = v.findViewById(R.id.btnDelete);
            chk = v.findViewById(R.id.chkItem);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup p, int viewType) {
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_cart, p, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder h, int i) {
        Cart c = list.get(i);

        h.name.setText(c.getName());
        h.price.setText(c.getPrice() + " đ");
        h.oldPrice.setText((c.getPrice() + 20000) + " đ");// giả giá cũ
        h.oldPrice.setPaintFlags(
                h.oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        h.qty.setText(String.valueOf(c.getQuantity()));
        h.chk.setChecked(c.isChecked());

        // checkbox
        h.chk.setOnCheckedChangeListener((b, isChecked) -> {
            c.setChecked(isChecked);
            updateTotal.run();
        });

        // +
        h.plus.setOnClickListener(v -> {
            int newQty = c.getQuantity() + 1;
            c.setQuantity(newQty);
            db.updateCartQuantity(c.getId(), newQty);
            notifyDataSetChanged();
            updateTotal.run();
        });

        // -
        h.minus.setOnClickListener(v -> {
            int newQty = c.getQuantity() - 1;
            if (newQty < 1) return;

            c.setQuantity(newQty);
            db.updateCartQuantity(c.getId(), newQty);
            notifyDataSetChanged();
            updateTotal.run();
        });

        // delete
        h.delete.setOnClickListener(v -> {
            db.deleteCart(c.getId());
            list.remove(i);
            notifyDataSetChanged();
            updateTotal.run();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}