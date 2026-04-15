package com.example.coffeeshopapp.order;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.database.DatabaseHelper;
import com.example.coffeeshopapp.model.Product;
import com.example.coffeeshopapp.product.ProductAdapter;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    RecyclerView rv;
    DatabaseHelper db;
    ArrayList<Product> list;
    ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        rv = findViewById(R.id.recyclerView);

        db = new DatabaseHelper(this);
        list = db.getAllProducts();

        adapter = new ProductAdapter(this, list, "CUSTOMER");
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        //Lộc thêm
        TextView btnHome = findViewById(R.id.btnHome);
        TextView btnProduct = findViewById(R.id.btnProduct);
        TextView btnCart = findViewById(R.id.btnCart);
        TextView btnAdmin = findViewById(R.id.btnAdmin);

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, com.example.coffeeshopapp.product.MainHomeActivity.class);
            startActivity(intent);
        });

        btnProduct.setOnClickListener(v -> {
            startActivity(new Intent(this, com.example.coffeeshopapp.product.HomeActivity.class));
        });

        btnCart.setOnClickListener(v -> {
            startActivity(new Intent(this, com.example.coffeeshopapp.cart.CartActivity.class));
        });


        btnAdmin.setOnClickListener(v -> {
            startActivity(new Intent(this, com.example.coffeeshopapp.admin.AdminActivity.class));
        });
    }
}