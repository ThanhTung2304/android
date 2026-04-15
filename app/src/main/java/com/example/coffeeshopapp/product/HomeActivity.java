package com.example.coffeeshopapp.product;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.database.DatabaseHelper;
import com.example.coffeeshopapp.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fab;
    DatabaseHelper db;
    ArrayList<Product> list;
    ProductAdapter adapter;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView btnHome = findViewById(R.id.btnHome);
        TextView btnProduct = findViewById(R.id.btnProduct);
        TextView btnCart = findViewById(R.id.btnCart);
        TextView btnOrder = findViewById(R.id.btnOrder);
        TextView btnAdmin = findViewById(R.id.btnAdmin);

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, com.example.coffeeshopapp.product.MainHomeActivity.class);
            intent.putExtra("role", role);
            startActivity(intent);
        });
//Loc them
        btnProduct.setOnClickListener(v -> {
            startActivity(new Intent(this, com.example.coffeeshopapp.product.HomeActivity.class));
        });

        btnCart.setOnClickListener(v -> {
            startActivity(new Intent(this, com.example.coffeeshopapp.cart.CartActivity.class));
        });

        btnOrder.setOnClickListener(v -> {
            startActivity(new Intent(this, com.example.coffeeshopapp.order.OrderActivity.class));
        });

        btnAdmin.setOnClickListener(v -> {
            startActivity(new Intent(this, com.example.coffeeshopapp.admin.AdminActivity.class));
        });
        // Nhận role từ Login
        role = getIntent().getStringExtra("role");
//         ✅ FIX 1: LẤY ROLE TỪ SHARED (KHÔNG DÙNG INTENT NỮA)
        SharedPreferences prefs = getSharedPreferences("USER", MODE_PRIVATE);
        role = prefs.getString("role", "");

        // ✅ FIX 2: NẾU CHƯA LOGIN → ĐÁ VỀ LOGIN (TRÁNH MÀN ĐEN)
        if (role.isEmpty()) {
            startActivity(new Intent(this, com.example.coffeeshopapp.auth.LoginActivity.class));
            finish();
            return;
        }

        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fabAdd);

        db = new DatabaseHelper(this);

        // Lấy dữ liệu
        list = db.getAllProducts();
        if (list == null) list = new ArrayList<>();

        adapter = new ProductAdapter(this, list, role);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Ẩn nút nếu không phải ADMIN
//        if (role == null || !role.equals("ADMIN")) {
//            fab.setVisibility(View.GONE);
//        }
        // ✅ FIX 3: CHẶN QUYỀN ĐÚNG
        if (!"ADMIN".equalsIgnoreCase(role)) {
            fab.setVisibility(View.GONE);
        }

        fab.setOnClickListener(v -> showAddDialog());
    }

    private void showAddDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_product, null);

        EditText name = view.findViewById(R.id.edtName);
        EditText price = view.findViewById(R.id.edtPrice);
        EditText cate = view.findViewById(R.id.edtCategory);
        CheckBox hot = view.findViewById(R.id.chkHot);

        new AlertDialog.Builder(this)
                .setTitle("Thêm sản phẩm")
                .setView(view)
                .setPositiveButton("Add", (d, w) -> {
                    db.insertProduct(
                            name.getText().toString(),
                            Integer.parseInt(price.getText().toString()),
                            cate.getText().toString(),
                            hot.isChecked()
                    );

                    list.clear();
                    list.addAll(db.getAllProducts());
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
