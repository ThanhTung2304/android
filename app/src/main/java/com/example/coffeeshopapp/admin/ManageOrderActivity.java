package com.example.coffeeshopapp.admin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.database.DatabaseHelper;
import com.example.coffeeshopapp.model.Order;

import java.util.List;

public class ManageOrderActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private RecyclerView recyclerView;
    private AdminOrderAdapter adapter;
    private List<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_order);

        db = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.rv_orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadOrders();
    }

    private void loadOrders() {
        orderList = db.getAllOrders();

        adapter = new AdminOrderAdapter(orderList); // ✅ đúng

        recyclerView.setAdapter(adapter);
    }
}