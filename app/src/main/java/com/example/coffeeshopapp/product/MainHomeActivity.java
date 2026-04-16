package com.example.coffeeshopapp.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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

    // bottom nav
    TextView btnHome, btnProduct, btnCart, btnOrder;

    // drawer
    DrawerLayout drawerLayout;
    ImageView btnProfile;

    // sidebar buttons
    TextView btnProfileInfo, btnOrderHistory, btnSetting, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        role = getIntent().getStringExtra("role");

        // ===== ánh xạ =====
        recyclerView = findViewById(R.id.recyclerProducts);

        btnHome = findViewById(R.id.btnHome);
        btnProduct = findViewById(R.id.btnProduct);
        btnCart = findViewById(R.id.btnCart);
        btnOrder = findViewById(R.id.btnOrder);

        drawerLayout = findViewById(R.id.drawerLayout);
        btnProfile = findViewById(R.id.btnProfile);

        // sidebar
        btnProfileInfo = findViewById(R.id.btnProfileInfo);
        btnOrderHistory = findViewById(R.id.btnOrderHistory);
        btnSetting = findViewById(R.id.btnSetting);
        btnLogout = findViewById(R.id.btnLogout);

        // ===== dữ liệu =====
        db = new DatabaseHelper(this);
        list = db.getAllProducts();

        adapter = new ProductAdapter(this, list, "CUSTOMER");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // ===== mở sidebar =====
        btnProfile.setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.END);   // ✅ đúng
        });

        // ===== bottom nav =====
        btnProduct.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("role", role);
            startActivity(intent);
        });

        btnCart.setOnClickListener(v -> {
            startActivity(new Intent(this, com.example.coffeeshopapp.cart.CartActivity.class));
        });

        btnOrder.setOnClickListener(v -> {
            startActivity(new Intent(this, com.example.coffeeshopapp.order.OrderActivity.class));
        });

        // ===== sidebar actions =====

        // hồ sơ
        btnProfileInfo.setOnClickListener(v -> {
            Toast.makeText(this, "Mở hồ sơ", Toast.LENGTH_SHORT).show();
            drawerLayout.closeDrawer(GravityCompat.END);
        });

        // đơn hàng
        btnOrderHistory.setOnClickListener(v -> {
            startActivity(new Intent(this, com.example.coffeeshopapp.order.OrderActivity.class));
            drawerLayout.closeDrawer(GravityCompat.END);
        });

        // cài đặt
        btnSetting.setOnClickListener(v -> {
            startActivity(new Intent(this, com.example.coffeeshopapp.admin.ManageUserActivity.class));
            drawerLayout.closeDrawer(GravityCompat.END);
        });

        // đăng xuất
        btnLogout.setOnClickListener(v -> {
            Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainHomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}