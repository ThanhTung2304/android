package com.example.coffeeshopapp.order;

import android.content.Intent;
import android.os.Bundle;

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
    }
}