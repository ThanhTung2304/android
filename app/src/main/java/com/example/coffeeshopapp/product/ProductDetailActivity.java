package com.example.coffeeshopapp.product;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.database.DatabaseHelper;

public class ProductDetailActivity extends AppCompatActivity {

    TextView txtName, txtPrice;
    Button btnAddCart, btnBuy;
    DatabaseHelper db;

    String name;
    int price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        txtName = findViewById(R.id.txtName);
        txtPrice = findViewById(R.id.txtPrice);
        btnAddCart = findViewById(R.id.btnAddCart);
        btnBuy = findViewById(R.id.btnConfirm);

        TextView btnHome = findViewById(R.id.btnHome);
        TextView btnProduct = findViewById(R.id.btnProduct);
        TextView btnCart = findViewById(R.id.btnCart);
        TextView btnOrder = findViewById(R.id.btnOrder);

        btnHome.setOnClickListener(v ->
                startActivity(new Intent(this, com.example.coffeeshopapp.product.HomeActivity.class)));

        btnProduct.setOnClickListener(v ->
                startActivity(new Intent(this, com.example.coffeeshopapp.product.HomeActivity.class)));

        btnCart.setOnClickListener(v ->
                startActivity(new Intent(this, com.example.coffeeshopapp.cart.CartActivity.class)));

        btnOrder.setOnClickListener(v ->
                startActivity(new Intent(this, com.example.coffeeshopapp.order.OrderActivity.class)));
        db = new DatabaseHelper(this);

        name = getIntent().getStringExtra("name");
        price = getIntent().getIntExtra("price", 0);

        if (name != null) {
            txtName.setText(name);
        } else {
            txtName.setText("Không có sản phẩm");
        }

        txtPrice.setText(price + " đ");

        btnAddCart.setOnClickListener(v -> {
            if (name == null) {
                Toast.makeText(this, "Lỗi sản phẩm", Toast.LENGTH_SHORT).show();
                return;
            }

            db.addToCart(name, price);
            Toast.makeText(this, "Đã thêm vào giỏ", Toast.LENGTH_SHORT).show();
        });

        btnBuy.setOnClickListener(v -> {
            db.addToCart(name, price);
            Toast.makeText(this, "Đã thêm và chuyển tới giỏ hàng", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, com.example.coffeeshopapp.cart.CartActivity.class));
        });
    }
}