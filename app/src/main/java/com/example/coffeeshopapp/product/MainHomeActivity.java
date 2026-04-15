package com.example.coffeeshopapp.product;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.auth.LoginActivity;
import com.example.coffeeshopapp.database.DatabaseHelper;
import com.example.coffeeshopapp.model.Product;


import java.util.ArrayList;

public class MainHomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper db;
    ArrayList<Product> list;
    ProductAdapter adapter;
    String role;

    TextView btnHome, btnProduct, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        role = getIntent().getStringExtra("role");

        recyclerView = findViewById(R.id.recyclerProducts);
        btnHome = findViewById(R.id.btnHome);
        btnProduct = findViewById(R.id.btnProduct);
        //Lộc thêm
        btnLogout = findViewById(R.id.btnLogout);

        db = new DatabaseHelper(this);
        list = db.getAllProducts();

        adapter = new ProductAdapter(this, list, "CUSTOMER");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // 👉 chuyển sang Product
        btnProduct.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("role", role);
            startActivity(intent);
        });
        btnLogout.setOnClickListener(v -> {
            Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainHomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        TextView btnCart = findViewById(R.id.btnCart);
        TextView btnOrder = findViewById(R.id.btnOrder);

        btnCart.setOnClickListener(v -> {
            startActivity(new Intent(this, com.example.coffeeshopapp.cart.CartActivity.class));
        });

        btnOrder.setOnClickListener(v -> {
            startActivity(new Intent(this, com.example.coffeeshopapp.order.OrderActivity.class));
        });;
    }
}