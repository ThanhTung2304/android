//package com.example.coffeeshopapp.cart;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.*;
//
//import com.example.coffeeshopapp.R;
//import com.example.coffeeshopapp.database.DatabaseHelper;
//import com.example.coffeeshopapp.model.Cart;
//
//import java.util.ArrayList;
//
//public class CartActivity extends AppCompatActivity {
//
//    RecyclerView rv;
//    TextView txtTotal;
//    CheckBox chkAll;
//
//    DatabaseHelper db;
//    ArrayList<Cart> list;
//    CartAdapter adapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_cart);
//
//        rv = findViewById(R.id.rvCart);
//        txtTotal = findViewById(R.id.txtTotalPrice);
//        chkAll = findViewById(R.id.chkAll);
//
//        Button btnCheckout = findViewById(R.id.btnCheckout);
//
//        TextView btnHome = findViewById(R.id.btnHome);
//        TextView btnProduct = findViewById(R.id.btnProduct);
//        TextView btnCart = findViewById(R.id.btnCart);
//        TextView btnOrder = findViewById(R.id.btnOrder);
//        TextView btnAdmin = findViewById(R.id.btnAdmin);
//
//        btnHome.setOnClickListener(v -> {
//            startActivity(new Intent(this, com.example.coffeeshopapp.product.HomeActivity.class));
//        });
//
//        btnProduct.setOnClickListener(v -> {
//            startActivity(new Intent(this, com.example.coffeeshopapp.product.HomeActivity.class));
//        });
//
//// đang ở cart
//        btnOrder.setOnClickListener(v -> {
//            startActivity(new Intent(this, com.example.coffeeshopapp.order.OrderActivity.class));
//        });
//
//        btnAdmin.setOnClickListener(v -> {
//            startActivity(new Intent(this, com.example.coffeeshopapp.admin.AdminActivity.class));
//        });
//
//        db = new DatabaseHelper(this);
//        list = db.getCart();
//
//        db = new DatabaseHelper(this);
//        list = db.getCart();
//
//        adapter = new CartAdapter(this, list, this::updateTotal);
//        rv.setLayoutManager(new LinearLayoutManager(this));
//        rv.setAdapter(adapter);
//
//        updateTotal();
//
//        // check all
//        chkAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            for (Cart c : list) {
//                c.setChecked(isChecked);
//            }
//            adapter.notifyDataSetChanged();
//            updateTotal();
//        });
//
//        //Lộc
//        Button btnCheckout = findViewById(R.id.btnCheckout);
//        btnCheckout.setOnClickListener(v -> {
//            if (list.isEmpty()) {
//                Toast.makeText(this, "Chưa có món nào trong giỏ!", Toast.LENGTH_SHORT).show();
//            } else {
//                showCheckoutDialog();
//            }
//        });
//    }
//
//    // 👉 tính tổng tiền
//    public void updateTotal() {
//        int total = 0;
//
//        for (Cart c : list) {
//            if (c.isChecked()) {
//                total += c.getPrice() * c.getQuantity();
//            }
//        }
//
//        txtTotal.setText("Tổng: " + total + " đ");
//    }
//    //Lộc
//    private void showCheckoutDialog() {
//        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
//        android.view.View view = getLayoutInflater().inflate(R.layout.dialog_confirm_payment, null);
//        builder.setView(view);
//
//        android.app.AlertDialog dialog = builder.create();
//        dialog.setCancelable(false);
//
//        TextView tvDialogTotal = view.findViewById(R.id.tvDialogTotal);
//        EditText edtName = view.findViewById(R.id.edtName);
//        EditText edtPhone = view.findViewById(R.id.edtPhone);
//        EditText edtAddress = view.findViewById(R.id.edtAddress);
//        Button btnConfirm = view.findViewById(R.id.btnConfirmCheckout);
//        Button btnCancel = view.findViewById(R.id.btnCancelCheckout);
//
//        // Lấy số tiền từ màn hình chính giỏ hàng
//        tvDialogTotal.setText("Thanh toán: " + txtTotal.getText().toString().replace("Tổng: ", ""));
//
//        btnCancel.setOnClickListener(v -> dialog.dismiss());
//
//        btnConfirm.setOnClickListener(v -> {
//            String name = edtName.getText().toString().trim();
//            String phone = edtPhone.getText().toString().trim();
//            String address = edtAddress.getText().toString().trim();
//
//            if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
//                Toast.makeText(this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            db.createOrder(name, phone, address);
//            Toast.makeText(this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
//            dialog.dismiss();
//
//            // Refresh lại giỏ hàng
//            list.clear();
//            list.addAll(db.getCart());
//            adapter.notifyDataSetChanged();
//            updateTotal();
//
//            // Chuyển về màn hình đơn hàng
//            startActivity(new Intent(this, com.example.coffeeshopapp.order.OrderActivity.class));
//        });
//
//        dialog.show();
//    }
//}
package com.example.coffeeshopapp.cart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

        // --- ÁNH XẠ VIEW (Chỉ khai báo biến ở đây một lần) ---
        rv = findViewById(R.id.rvCart);
        txtTotal = findViewById(R.id.txtTotalPrice);
        chkAll = findViewById(R.id.chkAll);
        Button btnCheckout = findViewById(R.id.btnCheckout); // Đây là nút quan trọng nhất

        TextView btnHome = findViewById(R.id.btnHome);
        TextView btnProduct = findViewById(R.id.btnProduct);
        TextView btnCart = findViewById(R.id.btnCart);
        TextView btnOrder = findViewById(R.id.btnOrder);
        TextView btnAdmin = findViewById(R.id.btnAdmin);

        // --- XỬ LÝ MENU ---
        btnHome.setOnClickListener(v -> startActivity(new Intent(this, com.example.coffeeshopapp.product.HomeActivity.class)));
        btnProduct.setOnClickListener(v -> startActivity(new Intent(this, com.example.coffeeshopapp.product.HomeActivity.class)));
        btnOrder.setOnClickListener(v -> startActivity(new Intent(this, com.example.coffeeshopapp.order.OrderActivity.class)));
        btnAdmin.setOnClickListener(v -> startActivity(new Intent(this, com.example.coffeeshopapp.admin.AdminActivity.class)));

        // --- DATABASE & RECYCLERVIEW ---
        db = new DatabaseHelper(this);
        list = db.getCart();

        adapter = new CartAdapter(this, list, this::updateTotal);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        updateTotal();

        // --- XỬ LÝ CHECK ALL ---
        chkAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            for (Cart c : list) {
                c.setChecked(isChecked);
            }
            adapter.notifyDataSetChanged();
            updateTotal();
        });

        // --- XỬ LÝ NÚT THANH TOÁN (LỘC) ---
        // SỬA LỖI: Không ghi 'Button' ở đây nữa vì đã khai báo ở dòng 34
        btnCheckout.setOnClickListener(v -> {
            if (list.isEmpty()) {
                Toast.makeText(this, "Chưa có món nào trong giỏ!", Toast.LENGTH_SHORT).show();
            } else {
                showCheckoutDialog();
            }
        });
    }

    // 👉 Tính tổng tiền (Giữ nguyên logic của Linh)
    public void updateTotal() {
        int total = 0;
        for (Cart c : list) {
            if (c.isChecked()) {
                total += c.getPrice() * c.getQuantity();
            }
        }
        txtTotal.setText("Tổng: " + total + " đ");
    }

    // --- DIALOG THANH TOÁN (LỘC) ---
    private void showCheckoutDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        android.view.View view = getLayoutInflater().inflate(R.layout.dialog_confirm_payment, null);
        builder.setView(view);

        android.app.AlertDialog dialog = builder.create();
        dialog.setCancelable(false);

        TextView tvDialogTotal = view.findViewById(R.id.tvDialogTotal);
        EditText edtName = view.findViewById(R.id.edtName);
        EditText edtPhone = view.findViewById(R.id.edtPhone);
        EditText edtAddress = view.findViewById(R.id.edtAddress);
        Button btnConfirm = view.findViewById(R.id.btnConfirmCheckout);
        Button btnCancel = view.findViewById(R.id.btnCancelCheckout);

        // Hiển thị tiền từ màn hình chính giỏ hàng
        tvDialogTotal.setText("Thanh toán: " + txtTotal.getText().toString().replace("Tổng: ", ""));

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnConfirm.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            String address = edtAddress.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            db.createOrder(name, phone, address);
            Toast.makeText(this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();

            // Refresh lại giao diện giỏ hàng
            list.clear();
            list.addAll(db.getCart());
            adapter.notifyDataSetChanged();
            updateTotal();

            // Nhảy sang xem đơn hàng
            startActivity(new Intent(this, com.example.coffeeshopapp.order.OrderActivity.class));
        });

        dialog.show();
    }
}