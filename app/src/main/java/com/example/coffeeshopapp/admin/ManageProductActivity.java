package com.example.coffeeshopapp.admin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.database.DatabaseHelper;
import com.example.coffeeshopapp.model.Product;

import java.util.List;

public class ManageProductActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private RecyclerView recyclerView;
    private AdminProductAdapter adapter;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_product);

        db = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.rv_products);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadProducts();

        Button btnAdd = findViewById(R.id.btn_add_product);
        btnAdd.setOnClickListener(v -> showProductDialog(null));
    }

    private void loadProducts() {
        productList = db.getAllProducts();

        adapter = new AdminProductAdapter(
                productList,
                this::showProductDialog,
                this::confirmDelete
        );

        recyclerView.setAdapter(adapter);
    }

    // ================= DIALOG ADD / EDIT =================
    private void showProductDialog(Product product) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_product, null);

        builder.setView(dialogView);

        EditText etName = dialogView.findViewById(R.id.edtName);
        EditText etPrice = dialogView.findViewById(R.id.edtPrice);
        EditText etCategory = dialogView.findViewById(R.id.edtCategory);

        boolean isEdit = (product != null);
        builder.setTitle(isEdit ? "Sửa sản phẩm" : "Thêm sản phẩm");

        if (isEdit) {
            etName.setText(product.getName());
            etPrice.setText(String.valueOf(product.getPrice()));
            etCategory.setText(product.getCategory());
        }

        builder.setPositiveButton(isEdit ? "Cập nhật" : "Thêm", (dialog, which) -> {

            String name = etName.getText().toString().trim();
            String priceStr = etPrice.getText().toString().trim();
            String category = etCategory.getText().toString().trim();

            if (name.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(this, "Không được để trống!", Toast.LENGTH_SHORT).show();
                return;
            }

            int price;
            try {
                price = Integer.parseInt(priceStr);
            } catch (Exception e) {
                Toast.makeText(this, "Giá không hợp lệ!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isEdit) {
                // ✅ FIX: gọi đúng hàm DB
                db.updateProduct(
                        product.getId(),
                        name,
                        price,
                        category,
                        product.isHot()
                );

                Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                loadProducts();

            } else {
                // ✅ FIX: gọi đúng hàm DB
                if (db.insertProduct(name, price, category, false)) {
                    Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    loadProducts();
                }
            }
        });

        builder.setNegativeButton("Huỷ", null);
        builder.show();
    }

    // ================= DELETE =================
    private void confirmDelete(Product product) {
        new AlertDialog.Builder(this)
                .setTitle("Xoá sản phẩm")
                .setMessage("Xoá " + product.getName() + "?")
                .setPositiveButton("Xoá", (dialog, which) -> {

                    // ✅ FIX: delete không trả về boolean
                    db.deleteProduct(product.getId());

                    Toast.makeText(this, "Đã xoá!", Toast.LENGTH_SHORT).show();
                    loadProducts();
                })
                .setNegativeButton("Huỷ", null)
                .show();
    }
}