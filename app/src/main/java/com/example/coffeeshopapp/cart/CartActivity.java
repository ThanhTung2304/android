package com.example.coffeeshopapp.cart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.database.DatabaseHelper;
import com.example.coffeeshopapp.model.Cart;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    RecyclerView rv;
    TextView txtTotal;
    CheckBox chkAll;

    DatabaseHelper db;
    ArrayList<Cart> list;
    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rv = findViewById(R.id.rvCart);
        txtTotal = findViewById(R.id.txtTotalPrice);
        chkAll = findViewById(R.id.chkAll);

        Button btnCheckout = findViewById(R.id.btnCheckout);

        TextView btnHome = findViewById(R.id.btnHome);
        TextView btnProduct = findViewById(R.id.btnProduct);
        TextView btnCart = findViewById(R.id.btnCart);
        TextView btnOrder = findViewById(R.id.btnOrder);
        TextView btnAdmin = findViewById(R.id.btnAdmin);

        btnHome.setOnClickListener(v -> {
            startActivity(new Intent(this, com.example.coffeeshopapp.product.HomeActivity.class));
        });

        btnProduct.setOnClickListener(v -> {
            startActivity(new Intent(this, com.example.coffeeshopapp.product.HomeActivity.class));
        });

// đang ở cart
        btnOrder.setOnClickListener(v -> {
            startActivity(new Intent(this, com.example.coffeeshopapp.order.OrderActivity.class));
        });

        btnAdmin.setOnClickListener(v -> {
            startActivity(new Intent(this, com.example.coffeeshopapp.admin.AdminActivity.class));
        });

        db = new DatabaseHelper(this);
        list = db.getCart();

        db = new DatabaseHelper(this);
        list = db.getCart();

        adapter = new CartAdapter(this, list, this::updateTotal);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        updateTotal();

        // check all
        chkAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            for (Cart c : list) {
                c.setChecked(isChecked);
            }
            adapter.notifyDataSetChanged();
            updateTotal();
        });
    }

    // 👉 tính tổng tiền
    public void updateTotal() {
        int total = 0;

        for (Cart c : list) {
            if (c.isChecked()) {
                total += c.getPrice() * c.getQuantity();
            }
        }

        txtTotal.setText("Tổng: " + total + " đ");
    }
}