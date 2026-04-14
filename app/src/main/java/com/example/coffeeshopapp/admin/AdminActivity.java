package com.example.coffeeshopapp.admin;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coffeeshopapp.R;
//import com.example.n5.auth.LoginActivity;
import com.example.coffeeshopapp.auth.LoginActivity;
import com.example.coffeeshopapp.database.DatabaseHelper;

public class AdminActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
//      ánh  xạ Toolbar từ file activity_admin.xml
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar_admin);
        // Thiết lập Toolbar này làm Action Bar cho Activity
        setSupportActionBar(toolbar);

        // ===== CHẶN QUYỀN =====
//        role = getIntent().getStringExtra("role");
//        if (role == null || !role.equals("ADMIN")) {
//            Toast.makeText(this, "Không có quyền truy cập!", Toast.LENGTH_SHORT).show();
//            finish();
//            return;
//        }


//        role = getIntent().getStringExtra("role");
//        if (role == null) {
//            role = "ADMIN";
//        }
//
//        if (!role.equals("ADMIN")) {
//            Toast.makeText(this, "Không có quyền truy cập!", Toast.LENGTH_SHORT).show();
//            finish();
//            return;
//        }

        SharedPreferences prefs = getSharedPreferences("USER", MODE_PRIVATE);
        role = prefs.getString("role", "").trim().toUpperCase();

        if (!"ADMIN".equals(role)) {
            Toast.makeText(this, "Không có quyền truy cập!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        db = new DatabaseHelper(this);

        // Hiển thị thống kê nhanh
        TextView tvProductCount = findViewById(R.id.tv_product_count);
        TextView tvOrderCount = findViewById(R.id.tv_order_count);
        TextView tvUserCount = findViewById(R.id.tv_user_count);

        tvProductCount.setText(String.valueOf(db.getAllProducts().size()));
        tvOrderCount.setText(String.valueOf(db.getAllOrders().size()));
        tvUserCount.setText(String.valueOf(db.getAllUsers().size()));

        // Nút điều hướng
        Button btnManageProduct = findViewById(R.id.btn_manage_product);
        Button btnManageOrder = findViewById(R.id.btn_manage_order);
        Button btnManageUser = findViewById(R.id.btn_manage_user);
        Button btnLogout = findViewById(R.id.btn_logout);

        btnManageProduct.setOnClickListener(v -> {
            Intent intent = new Intent(this, ManageProductActivity.class);
            intent.putExtra("role", role);
            startActivity(intent);
        });

        btnManageOrder.setOnClickListener(v -> {
            Intent intent = new Intent(this, ManageOrderActivity.class);
            intent.putExtra("role", role);
            startActivity(intent);
        });

        btnManageUser.setOnClickListener(v -> {
            Intent intent = new Intent(this, ManageUserActivity.class);
            intent.putExtra("role", role);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("role", role);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật lại thống kê khi quay về
        if (db != null) {
            TextView tvProductCount = findViewById(R.id.tv_product_count);
            TextView tvOrderCount = findViewById(R.id.tv_order_count);
            TextView tvUserCount = findViewById(R.id.tv_user_count);
            tvProductCount.setText(String.valueOf(db.getAllProducts().size()));
            tvOrderCount.setText(String.valueOf(db.getAllOrders().size()));
            tvUserCount.setText(String.valueOf(db.getAllUsers().size()));
        }
    }
    // Lệnh này để "thổi" cái file XML vào thanh tiêu đề
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // Lệnh này để xử lý khi bạn bấm vào các mục trong Menu
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_product) {
            startActivity(new Intent(this, ManageProductActivity.class).putExtra("role", role));
            return true;
        }
        if (id == R.id.menu_order) {
            startActivity(new Intent(this, ManageOrderActivity.class).putExtra("role", role));
            return true;
        }
        if (id == R.id.menu_user) {
            startActivity(new Intent(this, ManageUserActivity.class).putExtra("role", role));
            return true;
        }

        if (id == R.id.menu_logout) {
            Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
